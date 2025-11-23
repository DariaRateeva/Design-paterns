package strategy;

public class FirstTimeCustomerDiscount implements DiscountStrategy {
    private double flatAmount;
    private double maxDiscount;

    public FirstTimeCustomerDiscount(double flatAmount, double maxDiscount) {
        this.flatAmount = flatAmount;
        this.maxDiscount = maxDiscount;
    }

    @Override
    public double calculateDiscount(double orderSubtotal) {
        double discount = Math.min(flatAmount, orderSubtotal);
        return Math.min(discount, maxDiscount);
    }

    @Override
    public String getDescription() {
        return String.format("First-Time Customer: $%.2f OFF (max $%.2f)", flatAmount, maxDiscount);
    }
}
