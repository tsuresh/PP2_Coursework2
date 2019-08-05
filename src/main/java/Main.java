import gui.StoreManagerGUI;
import javafx.application.Application;
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
            System.out.println();
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
            try {
                westminsterMusicStoreManager.addItem(newItem);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else if (itemType == ItemTypes.VINYL) {

            System.out.println("Enter the speed: ");
            double speed = InputHandeler.promtNumberInput(scanner);

            System.out.println("Enter the diametre: ");
            double diametre = InputHandeler.promtNumberInput(scanner);

            MusicItem newItem = new Vinyl(itemID, itemTitle, itemGenre, itemArtist, itemReleaseDate, itemPrice, itemType, speed, diametre);
            try {
                westminsterMusicStoreManager.addItem(newItem);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println("Invalid item type");
        }
    }

    private static void deleteItem() {
        System.out.println("Enter the item ID to delete: ");
        String itemID = InputHandeler.promtStringInput(scanner);

        try {
            westminsterMusicStoreManager.deleteItem(westminsterMusicStoreManager.searchItem(itemID));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printList() {
        westminsterMusicStoreManager.printList();
    }

    private static void sortList() {
        westminsterMusicStoreManager.sort();
    }

    private static void buyItem() {
        System.out.println("Enter the item ID to purchase: ");
        String itemID = InputHandeler.promtStringInput(scanner);

        System.out.println("Enter the item quantity: ");
        int quantity = InputHandeler.promtNumberInput(scanner);

        try {
            westminsterMusicStoreManager.buyItem(westminsterMusicStoreManager.searchItem(itemID), quantity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printReport() {
        westminsterMusicStoreManager.generateReport();
    }

    private static void launchGUI() {
        Application.launch(StoreManagerGUI.class);
    }

}