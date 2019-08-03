import gui.StoreManagerGUI;
import javafx.stage.Stage;
import models.*;
import util.InputHandeler;
import util.WestminsterMusicStoreManager;

import java.util.Scanner;

public class Main {

    private static Scanner scanner;
    private static WestminsterMusicStoreManager westminsterMusicStoreManager;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        westminsterMusicStoreManager = new WestminsterMusicStoreManager();
        displayMenu();
    }


    private static void displayMenu() {
        int selectedOption = 0;
        do {
            westminsterMusicStoreManager.displayMenu();
            selectedOption = InputHandeler.promtNumberInput(scanner, 1, 8);
            WestminsterMusicStoreManager storeManager = new WestminsterMusicStoreManager();

            switch (selectedOption) {
                case 1:
                    addItems();
                    break;
                case 2:
                    deleteItem();
                    break;
                case 3:
                    printList();
                    break;
                case 4:
                    sortList();
                    break;
                case 5:
                    buyItem();
                    break;
                case 6:
                    printReport();
                    break;
                case 7:
                    launchGUI();
            }
            System.out.println("");
        } while (selectedOption != 8);
    }

    private static void addItems() {
        System.out.println("Enter the item ID: ");
        String itemID = InputHandeler.promtStringInput(scanner);

        System.out.println("Enter the item title: ");
        String itemTitle = InputHandeler.promtStringInput(scanner);

        System.out.println("Enter the item genre: ");
        String itemGenre = InputHandeler.promtStringInput(scanner);

        System.out.println("Enter the item artist: ");
        String itemArtist = InputHandeler.promtStringInput(scanner);

        System.out.println("Enter the item release date in following format (dd-mm-yyyy): ");
        Date itemReleaseDate = InputHandeler.promtDateInput(scanner);

        System.out.println("Enter the price of the item: ");
        double itemPrice = InputHandeler.promtNumberInput(scanner);

        System.out.println("Enter the item type: (VINYL/CD)");
        ItemTypes itemType = ItemTypes.valueOf(InputHandeler.promtStringInput(scanner));

        if (itemType == ItemTypes.CD) {

            System.out.println("Enter the duration in following format (hh:mm:ss): ");
            Time duration = InputHandeler.promtTimeInput(scanner);

            MusicItem newItem = new CD(itemID, itemTitle, itemGenre, itemArtist, itemReleaseDate, itemPrice, itemType, duration);
            westminsterMusicStoreManager.addItem(newItem);

        } else if (itemType == ItemTypes.VINYL) {

            System.out.println("Enter the speed: ");
            double speed = InputHandeler.promtNumberInput(scanner);

            System.out.println("Enter the diametre: ");
            double diametre = InputHandeler.promtNumberInput(scanner);

            MusicItem newItem = new Vinyl(itemID, itemTitle, itemGenre, itemArtist, itemReleaseDate, itemPrice, itemType, speed, diametre);
            westminsterMusicStoreManager.addItem(newItem);

        } else {
            System.out.println("Invalid item type");
        }
    }

    private static void deleteItem() {
        System.out.println("Enter the item ID to delete: ");
        String itemID = InputHandeler.promtStringInput(scanner);
        if (westminsterMusicStoreManager.searchItem(itemID) != null) {
            westminsterMusicStoreManager.deleteItem(westminsterMusicStoreManager.searchItem(itemID));
        } else {
            System.out.println("Given item not found!");
        }
    }

    private static void printList() {
        System.out.printf("%-10s %5s %18s %n", "ItemID", "Type", "Title");
        System.out.println("--------------------------------------");
        for (MusicItem item : westminsterMusicStoreManager.getStoreItems()) {
            System.out.printf("%-10s %s %30s %n", item.getItemID(), item.getItemType(), item.getTitle());
        }
    }

    private static void sortList() {
        westminsterMusicStoreManager.sort(westminsterMusicStoreManager.getStoreItems());
    }

    private static void buyItem() {
        System.out.println("Enter the item ID to purchase: ");
        String itemID = InputHandeler.promtStringInput(scanner);

        System.out.println("Enter the item quantity: ");
        int quantity = InputHandeler.promtNumberInput(scanner);

        if (westminsterMusicStoreManager.searchItem(itemID) != null) {
            westminsterMusicStoreManager.buyItem(westminsterMusicStoreManager.searchItem(itemID), quantity);
        } else {
            System.out.println("Given item not found!");
        }
    }

    private static void printReport() {
        System.out.printf("%-25s %s %10s %10s %9s %n", "Title", "ItemID", "Price", "Date", "Time");
        System.out.println("--------------------------------------------------------------------");
        for (Sale item : westminsterMusicStoreManager.getBoughtItems()) {
            System.out.printf("%-25s %s %15s %14s %8s %n", item.getTitle(), item.getItemID(), item.getPrice(), item.getPurchaseDate(), item.getPurchaseTime());
        }
    }

    private static void launchGUI() {
        StoreManagerGUI gui = new StoreManagerGUI();
        try {
            gui.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
