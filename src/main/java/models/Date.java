package models;

import java.util.Objects;

public class Date {

    private int year, month, day;

    public Date(int year, int month, int day) {
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    public int getYear() {
        return year;
    }

    private void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    private void setMonth(int month) {
        if(month <= 12 && month >= 1){
            this.month = month;
        } else {
            throw new IllegalArgumentException("Invalid month");
        }
    }

    public int getDay() {
        return day;
    }

    private void setDay(int day) {
        if(getMonth() == 2){
            if(day > 29){
                throw new IllegalArgumentException("Invalid day");
            }
        }
        if(day >= 1 && day <= 31){
            this.day = day;
        } else {
            throw new IllegalArgumentException("Invalid day");
        }
    }

    public String getDate(){
        return getDay()+"-"+getMonth()+"-"+getYear();
    }

    @Override
    public String toString() {
        return day+"/"+month+"/"+year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return year == date.year &&
                month == date.month &&
                day == date.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }


}
