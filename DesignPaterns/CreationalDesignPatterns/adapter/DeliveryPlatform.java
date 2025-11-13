package adapter;

import models.Food;

public interface DeliveryPlatform {
    boolean publishMenuItem(Food food);
    boolean acceptOrder(String orderId, double amount);
    String getPlatformName();
}
