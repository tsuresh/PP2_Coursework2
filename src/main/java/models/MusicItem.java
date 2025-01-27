package models;

public abstract class MusicItem {

    protected String itemID;
    protected String title, genre, artist;
    protected Date releaseDate;
    protected double price;
    protected ItemTypes itemType;

    public MusicItem() {
    }

    public MusicItem(String itemID, String title, String genre, String artist, Date releaseDate, double price, ItemTypes itemType) {
        setItemID(itemID);
        setTitle(title);
        setGenre(genre);
        setArtist(artist);
        setReleaseDate(releaseDate);
        setPrice(price);
        setItemType(itemType);
    }

    public String getItemID() {
        return itemID;
    }

    private void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    private void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    private void setArtist(String artist) {
        this.artist = artist;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    private void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPrice() {
        return price;
    }

    private void setPrice(double price) {
        this.price = price;
    }

    public ItemTypes getItemType() {
        return itemType;
    }

    private void setItemType(ItemTypes itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "MusicItem{" +
                "itemID='" + itemID + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", artist='" + artist + '\'' +
                ", releaseDate=" + releaseDate +
                ", price=" + price +
                ", itemType=" + itemType +
                '}';
    }
}