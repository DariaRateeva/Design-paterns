package observer;

public class RestaurantNotifier implements OrderObserver {
    private String restaurantName;

    public RestaurantNotifier(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public void update(String orderId, OrderStatus status, String customerName, double totalAmount) {
        System.out.println("RESTAURANT KITCHEN NOTIFICATION");
        System.out.println("Restaurant: " + restaurantName);
        System.out.println("Order #" + orderId + " - " + status.getDescription());

        switch (status) {
            case CONFIRMED:
                System.out.println("NEW ORDER RECEIVED!");
                System.out.println("Customer: " + customerName);
                System.out.println("Order Value: $" + String.format("%.2f", totalAmount));
                System.out.println("Start preparing immediately");
                break;
            case PREPARING:
                System.out.println("Kitchen acknowledged order");
                break;
            case OUT_FOR_DELIVERY:
                System.out.println("Order picked up by delivery driver");
                break;
            case DELIVERED:
                System.out.println("Order successfully delivered");
                break;
            case CANCELLED:
                System.out.println("Order cancelled - stop preparation");
                break;
        }
    }
}
