package chain;

import singleton.OrderManager;
import composite.Order;

public abstract class OrderValidationHandler {
    private OrderValidationHandler nextHandler;

    public OrderValidationHandler linkWith(OrderValidationHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    public boolean validate(Order order, String deliveryAddress) {
        if (nextHandler != null) {
            return nextHandler.validate(order, deliveryAddress);
        }
        return true;
    }
}