package adapter;

// Adaptee - third-party PayPal API with incompatible interface
public class PayPalPayment {

    public boolean sendPayment(String email, double amountUSD) {
        System.out.println("\n[PayPal API] Processing payment via PayPal");
        System.out.println("  Email: " + email);
        System.out.println("  Amount: $" + String.format("%.2f", amountUSD));

        if (amountUSD > 0) {
            System.out.println("  PayPal payment successful");
            return true;
        }
        return false;
    }

    public void initiateRefund(String email, double amountUSD) {
        System.out.println("[PayPal API] Refund of $" + String.format("%.2f", amountUSD) + " sent to " + email);
    }

    public String getTransactionStatus(String email) {
        return "COMPLETED";
    }
}
