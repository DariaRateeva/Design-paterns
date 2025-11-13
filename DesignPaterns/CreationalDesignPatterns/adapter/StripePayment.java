package adapter;

// Adaptee - third-party Stripe API with incompatible interface
public class StripePayment {

    public boolean charge(String customerId, int amountInCents) {
        System.out.println("\n[Stripe API] Processing charge via Stripe");
        System.out.println("  Customer ID: " + customerId);
        System.out.println("  Amount: " + amountInCents + " cents");

        if (amountInCents > 0) {
            System.out.println("   Stripe charge successful");
            return true;
        }
        return false;
    }

    public boolean createRefund(String chargeId, int amountInCents) {
        System.out.println("[Stripe API] Refund of " + amountInCents + " cents for charge " + chargeId);
        return true;
    }

    public String retrieveCharge(String chargeId) {
        return "paid";
    }
}
