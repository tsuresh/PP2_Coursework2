package models;

import java.util.Objects;

public class Sale {

    private String title;
    private String itemID;
    private double price;
    private Date purchaseDate;
    private Time purchaseTime;
    private int quantity;

    public Sale() {
    }

    public Sale(String title, String itemID, double price, Date purchaseDate, Time purchaseTime, int quantity) {
        this.title = title;
        this.itemID = itemID;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getItemID() {
        return itemID;
    }

    public double getPrice() {
        return price;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public Time getPurchaseTime() {
        return purchaseTime;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Double.compare(sale.price, price) == 0 &&
                quantity == sale.quantity &&
                Objects.equals(title, sale.title) &&
                Objects.equals(itemID, sale.itemID) &&
                Objects.equals(purchaseDate, sale.purchaseDate) &&
                Objects.equals(purchaseTime, sale.purchaseTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, itemID, price, purchaseDate, purchaseTime, quantity);
    }

    @Override
    public String toString() {
        return "Sale{" +
                "title='" + title + '\'' +
                ", itemID='" + itemID + '\'' +
                ", price=" + price +
                ", purchaseDate=" + purchaseDate +
                ", purchaseTime=" + purchaseTime +
                ", quantity=" + quantity +
                '}';
    }
}
