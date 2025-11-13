package facade;

import models.Food;
import singleton.OrderManager;

// Facade - provides simplified interface to complex subsystem
public class OrderFacade {
    private PaymentService paymentService;
    private InventoryService inventoryService;
    private NotificationService notificationService;
    private DeliveryService deliveryService;
    private OrderManager orderManager;

    public OrderFacade() {
        this.paymentService = new PaymentService();
        this.inventoryService = new InventoryService();
        this.notificationService = new NotificationService();
        this.deliveryService = new DeliveryService();
        this.orderManager = OrderManager.getInstance();
    }

    // Simplified method that orchestrates all subsystems
    public boolean placeCompleteOrder(String customerName, String address,
                                      Food food, String paymentMethod,
                                      boolean isExpress) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ORDER FACADE - Processing Complete Order");
        System.out.println("=".repeat(60));

        // Step 1: Check inventory
        if (!inventoryService.checkAvailability(food)) {
            System.out.println("\n Order failed: Item not available");
            return false;
        }

        // Step 2: Reserve item
        inventoryService.reserveItem(food);

        // Step 3: Process payment
        double totalAmount = food.getPrice();
        if (!paymentService.processPayment(customerName, totalAmount, paymentMethod)) {
            inventoryService.releaseItem(food);
            System.out.println("\n Order failed: Payment declined");
            return false;
        }

        // Step 4: Create order in system
        var order = orderManager.placeOrder(customerName, food, null);
        food.prepare();

        // Step 5: Schedule delivery
        deliveryService.scheduleDelivery(customerName, address, isExpress);

        // Step 6: Send notifications
        notificationService.sendOrderConfirmation(customerName, order.getId(), food);
        notificationService.sendDeliveryUpdate(customerName, order.getId(), "Preparing");

        System.out.println("\n" + "=".repeat(60));
        System.out.println(" ORDER COMPLETED SUCCESSFULLY");
        System.out.println("=".repeat(60));

        return true;
    }

    // Simplified cancellation
    public void cancelOrder(String customerName, int orderId, Food food, double amount) {
        System.out.println("\n[Order Facade] Cancelling order #" + orderId);
        inventoryService.releaseItem(food);
        paymentService.refundPayment(customerName, amount);
        notificationService.sendDeliveryUpdate(customerName, orderId, "Cancelled");
        System.out.println(" Order cancelled successfully");
    }
}
