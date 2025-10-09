import java.util.HashMap;
import java.util.Map;

public class InMemoryBookRepository implements BookRepository {
    private Map<String, Book> books = new HashMap<>();

    @Override
    public void save(Book book) {
        books.put(book.getID(), book);
        System.out.println("Book saved: " + book.getTitle());
    }

    @Override
    public Book findByID(String id) {
        return books.get(id);
    }
}
