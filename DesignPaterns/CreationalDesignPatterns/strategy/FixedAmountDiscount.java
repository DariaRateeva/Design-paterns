package strategy;

public class FixedAmountDiscount implements DiscountStrategy{
    private double amount;

    public FixedAmountDiscount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
    }

    @Override
    public double calculateDiscount(double orderSubtotal){
        return Math.min(amount, orderSubtotal);
    }

    @Override
    public String getDescription() {
        return String.format("$%.2f OFF", amount);
    }

    public double getAmount() {
        return amount;
    }
}
