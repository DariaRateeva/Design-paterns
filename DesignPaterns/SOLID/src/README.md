# SOLID Principles

## Purpose
This report explores the implementation of three SOLID principles—**Single Responsibility**, **Open/Closed**, and **Dependency Inversion**—in a library management system built with Java. The goal is to demonstrate how SOLID helps structure maintainable and extensible software for real-world applications.

## What is SOLID?
**SOLID** is an acronym for five design principles that improve software structure and maintainability:

- **S**ingle Responsibility Principle (SRP): Each class should have one job.
- **O**pen/Closed Principle (OCP): Classes should be open for extension but closed for modification.
- **L**iskov Substitution Principle (LSP): Subtypes should replace the supertype without changing behavior.
- **I**nterface Segregation Principle (ISP): Don't force clients to depend on methods they don't use.
- **D**ependency Inversion Principle (DIP): Depend on abstractions, not concrete classes.

## Idea
This project implements a library management system that handles book borrowing, returns, late fee calculations, and member notifications. Each SOLID principle is represented by specific parts of the design to show the benefits of separation, extensibility, and abstraction in managing library operations.

***

## Principles Implemented

### 1. Single Responsibility Principle (SRP)
**What it means:** Each class should do only one thing.

**Implementation:**
- The `Book` class is responsible *only* for storing book data.
- The `Member` class handles member information exclusively.
- The `NotificationService` class focuses solely on sending notifications.
- The `LibraryService` orchestrates library operations without mixing concerns of persistence or notification logic.

**Code Snippet:**
```public class LibraryService {
private final BookRepository bookRepository;
private final NotificationService notificationService;
public LibraryService(BookRepository bookRepository,
                     NotificationService notificationService) {
    this.bookRepository = bookRepository;
    this.notificationService = notificationService;
}

public void borrowBook(String isbn, Member member) {
    Book book = bookRepository.findByID(isbn);
    if (book != null && book.isAvailable()) {
        book.setAvailable(false);
        bookRepository.save(book);
        notificationService.sendEmail(
            member.getEmail(),
            "You borrowed: " + book.getTitle()
        );
        System.out.println(member.getName() + " borrowed " + book.getTitle());
    }
}

```

***

### 2. Open/Closed Principle (OCP)
**What it means:** Classes can be extended, but their source code shouldn't need to be changed.

**Implementation:**
- The `LateFeeCalculator` abstract class allows new fee calculation strategies to be added without modifying existing code.
- Different member types (students, premium members, standard members) can have different late fee rates by extending the base calculator.

**Code Snippet:**
```
public abstract class LateFeeCalculator {
public abstract double calculateFee(int daysLate);
}

public class StudentFeeCalculator extends LateFeeCalculator {
@Override
public double calculateFee(int daysLate) {
return daysLate * 0.25;
}
}

public class PremiumFeeCalculator extends LateFeeCalculator {
@Override
public double calculateFee(int daysLate) {
return daysLate * 0.5;
}
}

public class StandardFeeCalculator extends LateFeeCalculator {
@Override
public double calculateFee(int daysLate) {
return daysLate * 1.0;
}
}

```

***

### 3. Dependency Inversion Principle (DIP)
**What it means:** Depend on interfaces or abstractions, not concrete implementations.

**Implementation:**
- `LibraryService` depends on the `BookRepository` interface, not on the specific `InMemoryBookRepository` implementation.
- The `returnBook` method accepts `LateFeeCalculator` as a parameter, depending on the abstraction rather than concrete calculator types.
- Dependencies are injected through the constructor, allowing different implementations to be substituted.

**Code Snippet:**
```
public interface BookRepository {
void save(Book book);
Book findByID(String id);
}

public class InMemoryBookRepository implements BookRepository {
private Map<String, Book> books = new HashMap<>();

@Override
public void save(Book book) {
books.put(book.getID(), book);
}

@Override
public Book findByID(String id) {
return books.get(id);
}
}

// Usage in Main.java
BookRepository bookRepository = new InMemoryBookRepository();
NotificationService notificationService = new NotificationService();
LibraryService libraryService = new LibraryService(
bookRepository,
notificationService
);

```

***

## Conclusion
By following SOLID principles in this library management system, the code becomes easier to maintain, extend, and test. New fee calculation strategies can be added, repository implementations can be swapped (from in-memory to database), and notification methods can be changed without breaking existing functionality. Applying even just a few SOLID principles in software design helps keep the codebase *robust*, *flexible*, and *clean*.

