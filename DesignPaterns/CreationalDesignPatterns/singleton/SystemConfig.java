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

    public static SystemConfig getInstance() {
        if (instance == null) {
            synchronized (SystemConfig.class) {
                if (instance == null) {
                    instance = new SystemConfig();
                }
            }
        }
        return instance;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }


    public String getRestaurantStatus() {
        return restaurantStatus;
    }


    public String getRestaurantName() {
        return restaurantName;
    }


}
