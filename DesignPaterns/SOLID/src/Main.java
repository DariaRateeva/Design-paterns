public class Main {
    public static void main(String[] args) {
        // Setup dependencies
        BookRepository bookRepository = new InMemoryBookRepository();
        NotificationService notificationService = new NotificationService();
        LibraryService libraryService = new LibraryService(
                bookRepository,
                notificationService
        );

        // Create books and members
        Book book1 = new Book("1", "Harry Potter", "JK Rowling");
        Book book2 = new Book("2", "Atomic Habits", "James Clear");
        Member john = new Member("001", "John Smith", "john@gmail.com");
        Member mary = new Member("002", "Mary Smith", "mary@gmail.com");

        // Add books
        libraryService.addBook(book1);
        libraryService.addBook(book2);

        // John borrows Atomic Habits
        libraryService.borrowBook("2", john);

        // John returns Atomic Habits with student fee
        LateFeeCalculator studentFee = new StudentFeeCalculator();
        libraryService.returnBook("2", 5, studentFee);

        // Mary borrows Harry Potter
        libraryService.borrowBook("1", mary);

        // Mary returns Harry Potter with premium fee
        LateFeeCalculator premiumFee = new PremiumFeeCalculator();
        libraryService.returnBook("1", 3, premiumFee);
    }

}
