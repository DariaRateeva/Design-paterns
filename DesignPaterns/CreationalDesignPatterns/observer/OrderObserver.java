package observer;

public interface OrderObserver {
    void update(String orderId, OrderStatus status, String customerName, double totalAmount);
}
