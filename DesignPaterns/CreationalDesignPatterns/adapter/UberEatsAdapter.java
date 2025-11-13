package adapter;

import models.Food;

public class UberEatsAdapter implements DeliveryPlatform {
    private UberEatsAPI uberEatsAPI;

    public UberEatsAdapter() {
        this.uberEatsAPI = new UberEatsAPI();
    }

    @Override
    public boolean publishMenuItem(Food food) {
        // Adapt: Convert Food object to UberEats JSON format
        String jsonData = String.format(
                "{\n" +
                        "  \"dish_name\": \"%s\",\n" +
                        "  \"price_usd\": %.2f,\n" +
                        "  \"category\": \"main\",\n" +
                        "  \"restaurant_id\": \"delicious_bites_001\"\n" +
                        "}",
                food.getName(),
                food.getPrice()
        );

        return uberEatsAPI.addMenuItem(jsonData);
    }

    @Override
    public boolean acceptOrder(String orderId, double amount) {
        // Adapt: Convert dollars to cents
        int priceInCents = (int)(amount * 100);
        return uberEatsAPI.createOrder("delicious_bites_001", priceInCents);
    }

    @Override
    public String getPlatformName() {
        return "UberEats";
    }
}
