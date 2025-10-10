public class LibraryService {
    private final BookRepository bookRepository;
    private final NotificationService notificationService;

    // Dependency injection through constructor
    public LibraryService(BookRepository bookRepository,
                          NotificationService notificationService) {
        this.bookRepository = bookRepository;
        this.notificationService = notificationService;
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void borrowBook(String id, Member member) {
        Book book = bookRepository.findByID(id);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            bookRepository.save(book);
            notificationService.sendEmail(
                    member.getEmail(),
                    "You borrowed: " + book.getTitle()
            );
            System.out.println(member.getName() + " borrowed " + book.getTitle());
        } else {
            System.out.println("Book not available");
        }
    }

    public void returnBook(String isbn, int daysLate,
                           LateFeeCalculator feeCalculator) {
        Book book = bookRepository.findByID(isbn);
        if (book != null) {
            book.setAvailable(true);
            bookRepository.save(book);

            if (daysLate > 0) {
                double fee = feeCalculator.calculateFee(daysLate);
                System.out.println("Late fee: $" + fee);
            }
            System.out.println("Book returned: " + book.getTitle());
        }
    }
}
