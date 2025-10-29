package singleton;

import models.Meal;
import models.Food;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderManager {
    private static OrderManager instance;

    private List<Order> orders;
    private int nextOrderId;

    private OrderManager() {
        this.orders = new ArrayList<>();
        this.nextOrderId = 1001;  // Start from order 1001
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            synchronized (OrderManager.class) {
                if (instance == null) {
                    instance = new OrderManager();
                }
            }
        }
        return instance;
    }

    // Place a new order
    public Order placeOrder(String customerName, Food food, Meal meal) {
        Order order = new Order(nextOrderId++, customerName, food, meal);
        orders.add(order);
        System.out.println("Order #" + order.getId() + " placed successfully!");
        return order;
    }

    // Get total number of orders
    public int getTotalOrders() {
        return orders.size();
    }

    // Get total revenue
    public double getTotalRevenue() {
        double total = 0;
        for (Order order : orders) {
            total += order.getTotalAmount();
        }
        return total;
    }

    // Display all orders
    public void displayAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("\n No orders yet.");
            return;
        }


        for (Order order : orders) {
            System.out.println(order);
            System.out.println("─────────────────────────────────────────");
        }

        System.out.println("\nSTATISTICS:");
        System.out.println("Total Orders: " + getTotalOrders());
        System.out.println("Total Revenue: $" + String.format("%.2f", getTotalRevenue()));
    }

    // Inner class to represent an order
    public class Order {
        private int id;
        private String customerName;
        private Food food;
        private Meal meal;
        private LocalDateTime orderTime;
        private double totalAmount;
        private double deliveryFeeSnapshot;

        public Order(int id, String customerName, Food food, Meal meal) {
            this.id = id;
            this.customerName = customerName;
            this.food = food;
            this.meal = meal;
            this.orderTime = LocalDateTime.now();
            this.deliveryFeeSnapshot = SystemConfig.getInstance().getDeliveryFee();
            this.totalAmount = calculateTotal();
        }

        private double calculateTotal() {
            double total = 0;
            if (food != null) {
                total += food.getPrice();
            }
            total += deliveryFeeSnapshot;
            return total;
        }

        public int getId() {
            return id;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            StringBuilder sb = new StringBuilder();
            sb.append("Order #").append(id).append("\n");
            sb.append("Customer: ").append(customerName).append("\n");
            sb.append("Time: ").append(orderTime.format(formatter)).append("\n");

            if (food != null) {
                sb.append("Food: ").append(food.getName()).append(" - $").append(String.format("%.2f", food.getPrice())).append("\n");
            }

            if (meal != null) {
                sb.append("Meal:\n");
                String mealStr = meal.toString();
                String[] lines = mealStr.split("\n");
                for (String line : lines) {
                    sb.append("   ").append(line).append("\n");
                }
            }

            sb.append("Delivery: $").append(String.format("%.2f", deliveryFeeSnapshot)).append("\n");
            sb.append("Total: $").append(String.format("%.2f", totalAmount));

            return sb.toString();
        }
    }
}
