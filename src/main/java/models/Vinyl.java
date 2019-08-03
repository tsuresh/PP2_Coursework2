package models;

import java.util.Objects;

public class Vinyl extends MusicItem {
    private double speed, diameter;

    public Vinyl() {
        super();
    }

    public Vinyl(String itemID, String title, String genre, String artist, Date releaseDate, double price, ItemTypes itemType, double speed, double diameter) {
        super(itemID, title, genre, artist, releaseDate, price, itemType);
        this.speed = speed;
        this.diameter = diameter;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDiameter() {
        return diameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vinyl vinyl = (Vinyl) o;
        return Double.compare(vinyl.speed, speed) == 0 &&
                Double.compare(vinyl.diameter, diameter) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(speed, diameter);
    }

    @Override
    public String toString() {
        return "Vinyl{" +
                "speed=" + speed +
                ", diameter=" + diameter +
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