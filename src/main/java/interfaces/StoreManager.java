package interfaces;

import models.MusicItem;

import java.util.List;

public interface StoreManager {

    void addItem(MusicItem item);

    boolean deleteItem(MusicItem item);

    void printList();

    void buyItem(MusicItem item, int quantity);

    void sort(List<MusicItem> musicItems);

    void generateReport();

    MusicItem searchItem(String itemID);

    List<MusicItem> searchItems(String title);

}
