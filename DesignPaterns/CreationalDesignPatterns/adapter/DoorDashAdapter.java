package adapter;

import models.Food;

public class DoorDashAdapter implements DeliveryPlatform {
    private DoorDashAPI doorDashAPI;

    public DoorDashAdapter() {
        this.doorDashAPI = new DoorDashAPI();
    }

    @Override
    public boolean publishMenuItem(Food food) {
        // Adapt: Convert Food object to DoorDash XML format
        String xmlData = String.format(
                "<menu>\n" +
                        "  <item>\n" +
                        "    <name>%s</name>\n" +
                        "    <price>%.2f</price>\n" +
                        "    <store>Delicious Bites</store>\n" +
                        "  </item>\n" +
                        "</menu>",
                food.getName(),
                food.getPrice()
        );

        return doorDashAPI.publishItem(xmlData);
    }

    @Override
    public boolean acceptOrder(String orderId, double amount) {
        // Adapt: DoorDash needs store code and item name
        return doorDashAPI.processDeliveryOrder("DB_STORE_001", "Order #" + orderId, amount);
    }

    @Override
    public String getPlatformName() {
        return "DoorDash";
    }
}
