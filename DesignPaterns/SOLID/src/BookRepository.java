public interface BookRepository {
    void save(Book book);
    Book findByID(String id);
}
