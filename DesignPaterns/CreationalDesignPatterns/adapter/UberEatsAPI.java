package adapter;


public class UberEatsAPI {

    public boolean addMenuItem(String jsonData) {
        System.out.println("   [UberEats API] Received JSON menu data:");
        System.out.println("   " + jsonData);
        return true;
    }

    public boolean createOrder(String restaurantId, int priceInCents) {
        System.out.println("   [UberEats API] Creating order:");
        System.out.println("   Restaurant ID: " + restaurantId);
        System.out.println("   Price: " + priceInCents + " cents");
        return true;
    }
}
