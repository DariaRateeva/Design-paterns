package facade;

import models.Food;

// Subsystem Class - handles customer notifications
public class NotificationService {

    public void sendOrderConfirmation(String customerName, int orderId, Food food) {
        System.out.println("\n[Notification Service] Sending confirmation...");
        System.out.println("  Email sent to customer: " + customerName);
        System.out.println("  Order #" + orderId + " confirmed");
        System.out.println("  Item: " + food.getName());
    }

    public void sendDeliveryUpdate(String customerName, int orderId, String status) {
        System.out.println("\n[Notification Service] Delivery update sent");
        System.out.println("  SMS to customer: " + customerName);
        System.out.println("  Order #" + orderId + " - Status: " + status);
    }

    public void sendPromotionalOffer(String customerName, String offer) {
        System.out.println("\n[Notification Service] Promotional offer sent");
        System.out.println(customerName + ": " + offer);
    }
}
