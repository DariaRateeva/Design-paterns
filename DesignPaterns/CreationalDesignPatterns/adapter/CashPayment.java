package adapter;

// Concrete implementation - already implements target interface
public class CashPayment implements PaymentProcessor {

    @Override
    public boolean processPayment(String customerName, double amount) {
        System.out.println("\n[Cash Payment] Processing cash payment");
        System.out.println("  Customer: " + customerName);
        System.out.println("  Amount: $" + String.format("%.2f", amount));
        System.out.println("  Cash payment received");
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "Cash";
    }

    @Override
    public boolean refund(String transactionId, double amount) {
        System.out.println("[Cash Payment] Refund of $" + String.format("%.2f", amount) + " prepared");
        return true;
    }
}
