package singleton;

public class SystemConfig {
    private static SystemConfig instance;

    private double deliveryFee;
    private String restaurantStatus;
    private String restaurantName;
    private int maxOrdersPerDay;

    private SystemConfig() {
        this.deliveryFee = 3.50;
        this.restaurantStatus = "Open";
        this.restaurantName = "Delicious Bites";
        this.maxOrdersPerDay = 100;
    }

    public static synchronized SystemConfig getInstance() {
        if (instance == null) {
            instance = new SystemConfig();
        }
        return instance;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getRestaurantStatus() {
        return restaurantStatus;
    }

    public void setRestaurantStatus(String restaurantStatus) {
        this.restaurantStatus = restaurantStatus;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public int getMaxOrdersPerDay() {
        return maxOrdersPerDay;
    }

    public void displayConfig() {
        System.out.println("\n===== SYSTEM CONFIGURATION =====");
        System.out.println("Restaurant: " + restaurantName);
        System.out.println("Status: " + restaurantStatus);
        System.out.println("Delivery Fee: $" + deliveryFee);
        System.out.println("Max Orders/Day: " + maxOrdersPerDay);
        System.out.println("=================================\n");
    }
}
