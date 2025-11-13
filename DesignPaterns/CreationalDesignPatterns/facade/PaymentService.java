package facade;

// Subsystem Class - handles payment processing
public class PaymentService {

    public boolean processPayment(String customerName, double amount, String paymentMethod) {
        System.out.println("\n[Payment Service] Processing payment...");
        System.out.println("  Customer: " + customerName);
        System.out.println("  Amount: $" + String.format("%.2f", amount));
        System.out.println("  Method: " + paymentMethod);

        // Simulate payment processing
        if (amount <= 0) {
            System.out.println("Payment failed: Invalid amount");
            return false;
        }

        System.out.println("Payment successful!");
        return true;
    }

    public void refundPayment(String customerName, double amount) {
        System.out.println("\n[Payment Service] Refunding $" + String.format("%.2f", amount) + " to " + customerName);
    }
}
