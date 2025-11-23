package observer;

public class DeliveryDriverNotifier implements OrderObserver {
    private String driverName;

    public DeliveryDriverNotifier(String driverName) {
        this.driverName = driverName;
    }

    @Override
    public void update(String orderId, OrderStatus status, String customerName, double totalAmount) {
        // Drivers only care about orders ready for delivery
        if (status == OrderStatus.PREPARING || status == OrderStatus.OUT_FOR_DELIVERY || status == OrderStatus.DELIVERED) {
            System.out.println("DELIVERY DRIVER NOTIFICATION");
            System.out.println("Driver: " + driverName);
            System.out.println("Order #" + orderId + " - " + status.getDescription());

            switch (status) {
                case PREPARING:
                    System.out.println("Order is being prepared");
                    System.out.println("Stand by for pickup");
                    break;
                case OUT_FOR_DELIVERY:
                    System.out.println("PICKUP CONFIRMED!");
                    System.out.println("Customer: " + customerName);
                    System.out.println("Deliver to customer address");
                    break;
                case DELIVERED:
                    System.out.println("Delivery confirmed by customer");
                    System.out.println("Return to base");
                    break;
            }
        }
    }
}
