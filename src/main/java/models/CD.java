package models;

import java.util.Objects;

public class CD extends MusicItem {

    private Time duration;

    public CD() {
        super();
    }

    public CD(String itemID, String title, String genre, String artist, Date releaseDate, double price, ItemTypes itemType, Time duration) {
        super(itemID, title, genre, artist, releaseDate, price, itemType);
        this.duration = duration;
    }

    public Time getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CD cd = (CD) o;
        return Objects.equals(duration, cd.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration);
    }

    @Override
    public String toString() {
        return "CD{" +
                "duration=" + duration +
                ", itemID='" + itemID + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", artist='" + artist + '\'' +
                ", releaseDate=" + releaseDate +
                ", price=" + price +
                ", itemType=" + itemType +
                '}';
    }
}