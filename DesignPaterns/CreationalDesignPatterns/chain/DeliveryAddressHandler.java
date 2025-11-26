package chain;

import composite.Order;

public class DeliveryAddressHandler extends OrderValidationHandler {
    @Override
    public boolean validate(Order order, String deliveryAddress) {
        if (deliveryAddress == null || deliveryAddress.trim().isEmpty()) {
            System.out.println("Validation Failed: Delivery address cannot be empty.");
            return false;
        }

        if (deliveryAddress.length() < 5) {
            System.out.println("Validation Failed: Please provide a valid, complete address.");
            return false;
        }

        System.out.println("Delivery address is valid");
        return super.validate(order, deliveryAddress);
    }
}