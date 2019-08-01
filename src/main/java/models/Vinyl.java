package models;

import java.util.Objects;

public class Vinyl extends MusicItem {
    private float speed, diameter;

    public Vinyl(String itemID, String title, String genre, String artist, Date releaseDate, double price, float speed, float diameter) {
        super(itemID, title, genre, artist, releaseDate, price);
        setSpeed(speed);
        setDiameter(diameter);
    }

    public float getSpeed() {
        return speed;
    }

    private void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDiameter() {
        return diameter;
    }

    private void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vinyl vinyl = (Vinyl) o;
        return Float.compare(vinyl.speed, speed) == 0 &&
                Float.compare(vinyl.diameter, diameter) == 0;
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
                '}';
    }
}