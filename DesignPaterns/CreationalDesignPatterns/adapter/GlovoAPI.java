package adapter;

import java.util.Map;

public class GlovoAPI {

    public boolean syncMenuItem(Map<String, Object> itemData, String apiKey) {
        System.out.println("   [Glovo API] Syncing with API key: " + apiKey);
        System.out.println("   Item data: " + itemData);
        return true;
    }

    public boolean initiateDelivery(Map<String, Object> orderDetails) {
        System.out.println("   [Glovo API] Initiating delivery:");
        System.out.println("   Details: " + orderDetails);
        return true;
    }
}
