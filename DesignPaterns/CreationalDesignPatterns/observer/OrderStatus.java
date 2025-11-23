package observer;

public enum OrderStatus {
    PENDING("Order Received - Pending Confirmation"),
    CONFIRMED("Order Confirmed - Payment Successful"),
    PREPARING("Being Prepared by Restaurant"),
    OUT_FOR_DELIVERY("Out for Delivery"),
    DELIVERED("Delivered Successfully"),
    CANCELLED("Order Cancelled");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
