package adapter;


public class DoorDashAPI {

    public boolean publishItem(String xmlData) {
        System.out.println("   [DoorDash API] Received XML data:");
        System.out.println("   " + xmlData);
        return true;
    }

    public boolean processDeliveryOrder(String storeCode, String itemName, double totalAmount) {
        System.out.println("   [DoorDash API] Processing order:");
        System.out.println("   Store Code: " + storeCode);
        System.out.println("   Item: " + itemName);
        System.out.println("   Total: $" + String.format("%.2f", totalAmount));
        return true;
    }
}
