package models;

import java.util.Objects;

public class Time {

    private int seconds, minutes, hours;

    public Time() {
    }

    public Time(int seconds, int minutes, int hours) {
        setSeconds(seconds);
        setMinutes(minutes);
        setHours(hours);
    }

    public int getSeconds() {
        return seconds;
    }

    private void setSeconds(int seconds) {
        if(seconds < 60){
            this.seconds = seconds;
        } else {
            throw new IllegalArgumentException("Invalid number of seconds");
        }
    }

    public int getMinutes() {
        return minutes;
    }

    private void setMinutes(int minutes) {
        if(minutes < 60){
            this.minutes = minutes;
        } else {
            throw new IllegalArgumentException("Invalid number of minutes");
        }
    }

    public int getHours() {
        return hours;
    }

    private void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return seconds == time.seconds &&
                minutes == time.minutes &&
                hours == time.hours;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seconds, minutes, hours);
    }

    @Override
    public String toString() {
        return hours + ":" + minutes + ":" + seconds;
    }

}
