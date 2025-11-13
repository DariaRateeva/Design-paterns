package facade;

// Subsystem Class - manages delivery logistics
public class DeliveryService {

    public void scheduleDelivery(String customerName, String address, boolean isExpress) {
        System.out.println("\n[Delivery Service] Scheduling delivery...");
        System.out.println("  Customer: " + customerName);
        System.out.println("  Address: " + address);
        System.out.println("  Type: " + (isExpress ? "Express (30 min)" : "Standard (60 min)"));
        System.out.println("  Delivery scheduled");
    }

    public String trackDelivery(int orderId) {
        return "In transit - ETA 25 minutes";
    }
}
