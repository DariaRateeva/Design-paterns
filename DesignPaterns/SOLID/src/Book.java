public class Book {
    private String ID;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String ID, String title, String author) {
        this.ID = ID;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getID() { return ID; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}
