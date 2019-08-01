import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import exceptions.ItemNotFoundException;
import exceptions.StoreFullException;
import interfaces.SortCategory;
import interfaces.StoreManager;
import models.CD;
import models.MusicItem;
import models.Vinyl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class WestminsterMusicStoreManager implements StoreManager {

    private static Scanner scanner;
    private static List<MusicItem> storeItems = new ArrayList<MusicItem>();
    private static List<MusicItem> boughtItems = new ArrayList<MusicItem>();
    private static final int maxCount = 1000;

    private static Firestore db;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        storeItems = new ArrayList<>();
        initFirebase();
        displayMenu();
    }

    private static void initFirebase() {
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("pp2cwk2-firebase-adminsdk-jmpsw-8a4bf78092.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://pp2cwk2.firebaseio.com")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();

        //ApiFuture<DocumentReference> addedDocRef = db.collection("cities").add(new Post("Suresh", "Test"));

    }

    private static void displayMenu() {
        int selectedOption = 0;

        do {
            System.out.println("Please choose an option from the given options:\n");
            System.out.println("1)\tAdd new item");
            System.out.println("2)\tDelete an item");
            System.out.println("3)\tPrint the list of items");
            System.out.println("4)\tSort the item");
            System.out.println("5)\tBuy an item");
            System.out.println("6)\tGenerate a report");
            System.out.println("7)\tExit program");

            selectedOption = promtNumberInput(1, 7);

            WestminsterMusicStoreManager storeManager = new WestminsterMusicStoreManager();

            switch (selectedOption) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
            }

            System.out.println("");
        } while (selectedOption != 7);
    }

    private static int promtNumberInput(int range1, int range2) {
        while (true) {
            try {
                int option = Integer.parseInt(scanner.nextLine());
                if (option >= range1 && option <= range2) {
                    return option;
                } else {
                    System.out.println("Your input has to be within range: " + range1 + "-" + range2);
                }
            } catch (NumberFormatException e) {
                System.out.println("Given input is not a number.");
            }
        }
    }

    private static int promtNumberInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Given input is not a number.");
            }
        }
    }

    private static String promtStringInput() {
        String input = scanner.nextLine();
        if (input.length() == 0) {
            System.out.println("Your input cannot be blank.");
            promtStringInput();
        }
        return input;
    }

    @Override
    public void addItem(MusicItem item) {
        if (storeItems.size() < maxCount) {
            storeItems.add(item);
        } else {
            throw new StoreFullException("Music store cannot contain more than " + maxCount + " items");
        }
    }

    @Override
    public boolean deleteItem(MusicItem item) {
        return storeItems.remove(item);

    }

    @Override
    public void printList() {
        for (MusicItem item : storeItems) {
            if (item instanceof Vinyl) {
                System.out.println(item);
            }
        }
        for (MusicItem item : storeItems) {
            if (item instanceof CD) {
                System.out.println(item);
            }
        }
    }

    @Override
    public void buyItem(MusicItem item) {
        if (storeItems.contains(item)) {
            deleteItem(item);
            boughtItems.add(item);
        } else {
            throw new ItemNotFoundException("Given store item is not found");
        }

    }

    @Override
    public void sort(List<MusicItem> musicItems, SortCategory category) {
        if (category == SortCategory.PRICE) {
            Collections.sort(musicItems, new PriceComparator());
        } else if (category == SortCategory.TITLE) {
            Collections.sort(musicItems, new TitleComparator());
        } else if (category == SortCategory.ITEM_ID) {
            Collections.sort(musicItems, new ItemNoComparator());
        } else if(category == SortCategory.ARTIST){
            Collections.sort(musicItems, new ArtistComparator());
        } else if(category == SortCategory.GENRE){
            Collections.sort(musicItems, new GenreComparator());
        } else if(category == SortCategory.RELEASE_DATE){
            Collections.sort(musicItems, new DateComparator());
        }
    }

    @Override
    public void generateReport() {

    }


}

class ItemNoComparator implements Comparator<MusicItem> {
    @Override
    public int compare(MusicItem o1, MusicItem o2) {
        return o1.getItemID().compareTo(o2.getItemID());
    }
}

class TitleComparator implements Comparator<MusicItem> {
    @Override
    public int compare(MusicItem o1, MusicItem o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}

class ArtistComparator implements Comparator<MusicItem> {
    @Override
    public int compare(MusicItem o1, MusicItem o2) {
        return o1.getArtist().compareTo(o2.getArtist());
    }
}

class GenreComparator implements Comparator<MusicItem> {
    @Override
    public int compare(MusicItem o1, MusicItem o2) {
        return o1.getGenre().compareTo(o2.getGenre());
    }
}

class DateComparator implements Comparator<MusicItem> {
    @Override
    public int compare(MusicItem o1, MusicItem o2) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date o1Date = null;
        Date o2Date= null;
        try {
            o1Date = df.parse(o1.getReleaseDate().getDate());
            o2Date = df.parse(o2.getReleaseDate().getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o1Date.compareTo(o2Date) > 0 ? 1 : 0;
    }
}

class PriceComparator implements Comparator<MusicItem> {
    @Override
    public int compare(MusicItem o1, MusicItem o2) {
        return (int)(o1.getPrice() - o2.getPrice())*100;
    }
}