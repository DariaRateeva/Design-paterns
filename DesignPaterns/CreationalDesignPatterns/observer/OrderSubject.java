package observer;

import java.util.ArrayList;
import java.util.List;

public class OrderSubject {
    private List<OrderObserver> observers;
    private String orderId;
    private OrderStatus currentStatus;
    private String customerName;
    private double totalAmount;

    public OrderSubject(String orderId, String customerName, double totalAmount) {
        this.observers = new ArrayList<>();
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.currentStatus = OrderStatus.PENDING;
    }

    public void attach(OrderObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer attached: " + observer.getClass().getSimpleName());
        }
    }

    public void detach(OrderObserver observer) {
        observers.remove(observer);
        System.out.println("Observer detached: " + observer.getClass().getSimpleName());
    }

    private void notifyObservers() {
        System.out.println("\nNotifying " + observers.size() + " observer(s) about order " + orderId + "...");
        for (OrderObserver observer : observers) {
            observer.update(orderId, currentStatus, customerName, totalAmount);
        }
    }

    public void setStatus(OrderStatus newStatus) {
        System.out.println("\nOrder Status Change: " + currentStatus + " → " + newStatus);
        this.currentStatus = newStatus;
        notifyObservers();
    }

    public OrderStatus getCurrentStatus() {
        return currentStatus;
    }

    public String getOrderId() {
        return orderId;
    }
}
