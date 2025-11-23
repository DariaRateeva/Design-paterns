package strategy;

public class BulkOrderDiscount implements DiscountStrategy {
    private double threshold;
    private double discountPercentage;

    public BulkOrderDiscount(double threshold, double discountPercentage) {
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold cannot be negative");
        }
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        this.threshold = threshold;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double calculateDiscount(double orderSubtotal) {
        if (orderSubtotal >= threshold) {
            return orderSubtotal * (discountPercentage / 100.0);
        }
        return 0.0;
    }

    @Override
    public String getDescription() {
        return String.format("%.0f%% OFF on orders above $%.2f", discountPercentage, threshold);
    }
}
