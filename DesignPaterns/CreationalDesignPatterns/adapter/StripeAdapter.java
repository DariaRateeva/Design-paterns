package adapter;

// Adapter - adapts Stripe's interface to PaymentProcessor interface
public class StripeAdapter implements PaymentProcessor {
    private StripePayment stripePayment;

    public StripeAdapter() {
        this.stripePayment = new StripePayment();
    }

    @Override
    public boolean processPayment(String customerName, double amount) {
        // Adapt our interface to Stripe's interface
        String customerId = "cus_" + customerName.hashCode();
        int amountInCents = (int)(amount * 100); // Convert dollars to cents
        return stripePayment.charge(customerId, amountInCents);
    }

    @Override
    public String getPaymentMethod() {
        return "Stripe";
    }

    @Override
    public boolean refund(String transactionId, double amount) {
        int amountInCents = (int)(amount * 100);
        return stripePayment.createRefund(transactionId, amountInCents);
    }
}
