package adapter;

// Target Interface - what our system expects
public interface PaymentProcessor {
    boolean processPayment(String customerName, double amount);
    String getPaymentMethod();
    boolean refund(String transactionId, double amount);
}
