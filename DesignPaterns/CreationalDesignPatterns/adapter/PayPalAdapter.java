package adapter;

// Adapter - adapts PayPal's interface to PaymentProcessor interface
public class PayPalAdapter implements PaymentProcessor {
    private PayPalPayment payPalPayment;

    public PayPalAdapter() {
        this.payPalPayment = new PayPalPayment();
    }

    @Override
    public boolean processPayment(String customerName, double amount) {
        // Adapt our interface to PayPal's interface
        String email = customerName.toLowerCase().replace(" ", ".") + "@email.com";
        return payPalPayment.sendPayment(email, amount);
    }

    @Override
    public String getPaymentMethod() {
        return "PayPal";
    }

    @Override
    public boolean refund(String transactionId, double amount) {
        String email = transactionId; // Simplified for demo
        payPalPayment.initiateRefund(email, amount);
        return true;
    }
}
