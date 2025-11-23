package strategy;

public class NoDiscount implements DiscountStrategy {

    @Override
    public double calculateDiscount(double orderSubtotal) {
        return 0.0;
    }

    @Override
    public String getDescription() {
        return "No Discount";
    }
}
