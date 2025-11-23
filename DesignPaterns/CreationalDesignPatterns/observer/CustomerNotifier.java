package observer;

public class CustomerNotifier implements OrderObserver {
    private String customerContact;

    public CustomerNotifier(String customerContact) {
        this.customerContact = customerContact;
    }

    @Override
    public void update(String orderId, OrderStatus status, String customerName, double totalAmount) {
        System.out.println("CUSTOMER NOTIFICATION                                ");
        System.out.println("To: " + customerContact);
        System.out.println("Dear " + customerName + ",");
        System.out.println("Order #" + orderId + ": " + status.getDescription());

        switch (status) {
            case CONFIRMED:
                System.out.println("Payment of $" + String.format("%.2f", totalAmount) + " received.");
                System.out.println("Your order is being prepared!");
                break;
            case PREPARING:
                System.out.println("Our chefs are working on your order!");
                break;
            case OUT_FOR_DELIVERY:
                System.out.println("Your order is on the way!");
                System.out.println("Expected delivery: 30-45 minutes");
                break;
            case DELIVERED:
                System.out.println("Enjoy your meal! Thank you for ordering.");
                break;
            case CANCELLED:
                System.out.println("Refund of $" + String.format("%.2f", totalAmount) + " initiated.");
                break;
        }
    }
}
