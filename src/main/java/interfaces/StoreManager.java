package interfaces;

import models.MusicItem;

import java.util.List;

public interface StoreManager {

    void addItem(MusicItem item);

    boolean deleteItem(MusicItem item);

    void printList();

    void buyItem(MusicItem item);

    void sort(List<MusicItem> musicItems, SortCategory category);

    void generateReport();

}
