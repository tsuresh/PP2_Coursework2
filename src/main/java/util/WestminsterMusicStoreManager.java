package util;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import exceptions.ItemIDTakenException;
import exceptions.ItemNotFoundException;
import exceptions.StoreFullException;
import interfaces.StoreManager;
import models.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WestminsterMusicStoreManager implements StoreManager {

    private final int MAX_COUNT = 1000;
    private static List<MusicItem> storeItems = new ArrayList<MusicItem>();
    private static Firestore db;
    private static List<Sale> boughtItems = new ArrayList<Sale>();

    public WestminsterMusicStoreManager() {
        if (db == null) {
            try {
                initFirebase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (storeItems.size() == 0) {
            System.out.println("Syncing with database...");
            syncFull();
        }
    }

    //Firebase initialization
    private void initFirebase() throws IOException {
        FileInputStream serviceAccount = null;
        serviceAccount = new FileInputStream("pp2cwk2-firebase-adminsdk-jmpsw-8a4bf78092.json");
        FirebaseOptions options = null;
        options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://pp2cwk2.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();
    }

    //Sync both store items and bought items with database
    private void syncFull() {
        syncStoreItems();
        syncBoughtItems();
    }

    //Sync store items
    private void syncStoreItems() {
        db.collection(Constants.MUSIC_ITEMS).listDocuments().forEach(docRef -> {
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = null;
            try {
                document = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (document.exists()) {
                MusicItem musicItem = null;
                if (document.getData().get(Constants.ITEM_TYPE).equals(Constants.VINYL)) {
                    musicItem = document.toObject(Vinyl.class);
                } else if (document.getData().get(Constants.ITEM_TYPE).equals(Constants.CD)) {
                    musicItem = document.toObject(CD.class);
                }
                storeItems.add(musicItem);
            }
        });
    }

    //Sync bought items
    private void syncBoughtItems() {
        db.collection(Constants.BOUGHT_ITEMS).listDocuments().forEach(docRef -> {
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = null;
            try {
                document = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (document.exists()) {
                Sale sale = document.toObject(Sale.class);
                boughtItems.add(sale);
            }
        });
    }

    //Display menu
    public void displayMenu() {
        System.out.println("Please choose an option from the given options:\n");
        System.out.println("1)\tAdd new item");
        System.out.println("2)\tDelete an item");
        System.out.println("3)\tPrint the list of items");
        System.out.println("4)\tSort the item");
        System.out.println("5)\tBuy an item");
        System.out.println("6)\tGenerate a report");
        System.out.println("7)\tDisplay GUI");
        System.out.println("8)\tExit program");
    }

    //Add new items
    @Override
    public void addItem(MusicItem item) throws ExecutionException, InterruptedException {
        for (MusicItem storeItem : getStoreItems()) {
            if (storeItem.getItemID().equals(item.getItemID())) {
                throw new ItemIDTakenException("Item ID is already taken!!");
            }
        }
        if (storeItems.size() < MAX_COUNT) {
            ApiFuture<WriteResult> insertFuture = db.collection(Constants.MUSIC_ITEMS).document(item.getItemID()).set(item);
            storeItems.add(item);
            System.out.println("Successfully added item at : " + insertFuture.get().getUpdateTime());
            System.out.println("There are " + (MAX_COUNT - storeItems.size()) + " slots pending.");
        } else {
            throw new StoreFullException("Music store cannot contain more than " + MAX_COUNT + " items");
        }
    }

    //Delete existing items
    @Override
    public void deleteItem(MusicItem item) throws ExecutionException, InterruptedException {
        if (storeItems.contains(item)) {
            ApiFuture<WriteResult> future = db.collection(Constants.MUSIC_ITEMS).document(item.getItemID()).delete();
            System.out.println("Successfully deleted item at : " + future.get().getUpdateTime());
            System.out.println("Deleted a " + item.getItemType());
            storeItems.remove(item);
        } else {
            throw new ItemNotFoundException("Given item not found in store!!");
        }
    }

    //Print items list
    @Override
    public void printList() {
        System.out.printf("%-10s %-10s %-30s %n", "ItemID", "Type", "Title");
        System.out.println("--------------------------------------");
        for (MusicItem item : getStoreItems()) {
            System.out.printf("%-10s %-10s %-30s %n", item.getItemID(), item.getItemType(), item.getTitle());
        }
    }

    //Make a new purchase
    @Override
    public void buyItem(MusicItem item, int quantity) throws ExecutionException, InterruptedException {
        if (storeItems.contains(item)) {
            LocalDateTime now = LocalDateTime.now();
            Sale sale = new Sale(item.getTitle(), item.getItemID(), item.getPrice(), new Date(now.getYear(), now.getMonthValue(), now.getDayOfMonth()), new Time(now.getSecond(), now.getMinute(), now.getHour()), quantity);
            ApiFuture<WriteResult> future = db.collection(Constants.BOUGHT_ITEMS).document().set(sale);
            System.out.println("Successfully bought item at : " + future.get().getUpdateTime());
            boughtItems.add(sale);
        } else {
            throw new ItemNotFoundException("Given store item is not found");
        }
    }

    //Sort item list
    @Override
    public void sort() {
        Collections.sort(storeItems, new TitleComparator());
        System.out.println("Successfully sorted list!!");
    }

    //Generate a report with purchases
    @Override
    public void generateReport() {
        System.out.printf("%-30s %-15s %-15s %-20s %-15s %n", "Title", "ItemID", "Price", "Date", "Time");
        System.out.println("------------------------------------------------------------------------------------------------");
        for (Sale item : getBoughtItems()) {
            System.out.printf("%-30s %-15s %-15s %-20s %-15s %n", item.getTitle(), item.getItemID(), item.getPrice(), item.getPurchaseDate(), item.getPurchaseTime());
        }
    }

    //Search for an item with itemID
    @Override
    public MusicItem searchItem(String itemID) {
        for (MusicItem item : getStoreItems()) {
            if (item.getItemID().equals(itemID)) {
                return item;
            }
        }
        return null;
    }

    //Search for multiple items with itemID
    @Override
    public List<MusicItem> searchItems(String title) {
        List<MusicItem> items = new ArrayList<>();
        for (MusicItem item : getStoreItems()) {
            if (item.getTitle().contains(title)) {
                items.add(item);
            }
        }
        return items;
    }

    //Returns the list of store items
    public List<MusicItem> getStoreItems() {
        return storeItems;
    }

    //Returns the list of bought items
    public List<Sale> getBoughtItems() {
        return boughtItems;
    }
}

//Title comparator
class TitleComparator implements Comparator<MusicItem> {
    @Override
    public int compare(MusicItem o1, MusicItem o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}