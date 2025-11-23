package strategy;

public interface DiscountStrategy {
    double calculateDiscount(double orderSubtotal);
    String getDescription();
}
