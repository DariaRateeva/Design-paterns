package adapter;

import models.Food;
import java.util.HashMap;
import java.util.Map;


public class GlovoAdapter implements DeliveryPlatform {
    private GlovoAPI glovoAPI;
    private static final String API_KEY = "glovo_api_key_xyz123";

    public GlovoAdapter() {
        this.glovoAPI = new GlovoAPI();
    }

    @Override
    public boolean publishMenuItem(Food food) {
        // Adapt: Convert Food object to Glovo Map format
        Map<String, Object> itemData = new HashMap<>();
        itemData.put("item_name", food.getName());
        itemData.put("cost", food.getPrice());
        itemData.put("currency", "USD");
        itemData.put("vendor", "Delicious Bites");

        return glovoAPI.syncMenuItem(itemData, API_KEY);
    }

    @Override
    public boolean acceptOrder(String orderId, double amount) {
        // Adapt: Create Map for order details
        Map<String, Object> orderDetails = new HashMap<>();
        orderDetails.put("order_id", orderId);
        orderDetails.put("total_amount", amount);
        orderDetails.put("api_key", API_KEY);

        return glovoAPI.initiateDelivery(orderDetails);
    }

    @Override
    public String getPlatformName() {
        return "Glovo";
    }
}
