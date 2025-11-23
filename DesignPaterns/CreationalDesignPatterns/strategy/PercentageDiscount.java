package strategy;

public class PercentageDiscount implements DiscountStrategy{
    private double percentage;
    public PercentageDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("percentage must be between 0 and 100");
        }
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(double orderSubtotal) {
        return orderSubtotal * (percentage / 100.0);
    }

    @Override
    public String getDescription() {
        return String.format("%.0f%% OFF", percentage);
    }

    public double getPercentage() {
        return percentage;
    }

}
