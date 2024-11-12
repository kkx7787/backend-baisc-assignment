package problem1;

class ConanBookStore implements BookStore {
    private final String serialNumber;
    private final String title;
    private final int price;
    private final String author;
    private final String description;
    private final String genre;
    private final String publishDate;
    private int quantity;

    ConanBookStore(String serialNumber, String title, int price, String author, String description, String genre, String publishDate) {
        this.serialNumber = serialNumber;
        this.title = title;
        this.price = price;
        this.author = author;
        this.description = description;
        this.genre = genre;
        this.publishDate = publishDate;
        this.quantity = 1;
    }

    public void increaseQuantity() {
        this.quantity += 1;
    }

    public int getTotalPrice() {
        return price * quantity;
    }

    @Override
    public void displayDetails() {
        System.out.printf("%s | %s | %d원 | %s | %s | %s | %s%n",
                serialNumber, title, price, author, description, genre, publishDate);
    }

    public void displayShoppingCart() {
        System.out.printf("%s\t|\t%d\t|\t%d\n", serialNumber, quantity, price * quantity);
    }

    public String getSerialNumber() {
        return serialNumber;
    }
}