package interfaces;

import models.MusicItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface StoreManager {

    void addItem(MusicItem item) throws ExecutionException, InterruptedException;

    void deleteItem(MusicItem item) throws ExecutionException, InterruptedException;

    void printList();

    void buyItem(MusicItem item, int quantity) throws ExecutionException, InterruptedException;

    void sort();

    void generateReport();

    MusicItem searchItem(String itemID);

    List<MusicItem> searchItems(String title);

}
