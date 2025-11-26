package chain;

import singleton.SystemConfig;
import composite.Order;

public class RestaurantStatusHandler extends OrderValidationHandler {
    @Override
    public boolean validate(Order order, String deliveryAddress) {
        String status = SystemConfig.getInstance().getRestaurantStatus();

        if (!"Open".equalsIgnoreCase(status)) {
            System.out.println("Validation Failed: The restaurant is currently " + status + ".");
            return false;
        }

        System.out.println("✓ Restaurant is Open");
        return super.validate(order, deliveryAddress);
    }
}