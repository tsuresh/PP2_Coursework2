package models;

import java.util.Objects;

public class CD extends MusicItem {

    private Time duration;

    public CD(String itemID, String title, String genre, String artist, Date releaseDate, double price, Time duration) {
        super(itemID, title, genre, artist, releaseDate, price);
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
                '}';
    }
}