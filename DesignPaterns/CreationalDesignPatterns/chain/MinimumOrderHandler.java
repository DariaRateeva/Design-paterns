package chain;

import composite.Order;

public class MinimumOrderHandler extends OrderValidationHandler {
    private static final double MIN_ORDER_AMOUNT = 10.00;

    @Override
    public boolean validate(Order order, String deliveryAddress) {
        if (order.getPrice() < MIN_ORDER_AMOUNT) {
            System.out.println("Validation Failed: Minimum order amount is $" + String.format("%.2f", MIN_ORDER_AMOUNT));
            System.out.println("Current total: $" + String.format("%.2f", order.getPrice()));
            return false;
        }

        System.out.println("Minimum order value met");
        return super.validate(order, deliveryAddress);
    }
}