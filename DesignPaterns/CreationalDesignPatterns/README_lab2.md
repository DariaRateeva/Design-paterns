# Structural Design Patterns

## Author: Daria Ratteeva

----

## Objectives:

* Study and understand the Structural Design Patterns;
* As a continuation of the previous laboratory work, think about the functionalities that the system will need to provide to the user;
* Implement at least 3 Structural Design Patterns for the specific domain;

## Used Design Patterns:

* Adapter Pattern
* Decorator Pattern
* Facade Pattern

## Implementation

### Adapter Pattern

The Adapter pattern allows incompatible interfaces to work together by converting one interface into another that clients expect. In this project, adapters enable integration with external payment gateways (Stripe, PayPal, Cash) and delivery platforms (UberEats, DoorDash, Glovo) without modifying the core application logic.

**Location:** `adapter/` package

**Structure:**
- **Target Interfaces:** `PaymentProcessor`, `DeliveryPlatform`
- **Adapters:** `StripeAdapter`, `PayPalAdapter`, `UberEatsAdapter`, `DoorDashAdapter`, `GlovoAdapter`
- **Adaptees:** External APIs with incompatible interfaces

**Key Implementation - StripeAdapter.java:**

```
public class StripeAdapter implements PaymentProcessor {
@Override
public boolean processPayment(String customerName, double amount) {
String customerId = "cus_" + customerName.hashCode();
int amountInCents = (int)(amount * 100); // Adapt dollars to cents
return stripePayment.charge(customerId, amountInCents);
}
}
```

**Key Implementation - UberEatsAdapter.java:**
```
public class UberEatsAdapter implements DeliveryPlatform {
private UberEatsAPI uberEatsAPI;
@Override
public boolean publishMenuItem(Food food) {
// Adapt Food object to JSON format expected by UberEats
String jsonData = String.format("{\"dish_name\": \"%s\", \"price_usd\": %.2f}",
food.getName(), food.getPrice());
return uberEatsAPI.addMenuItem(jsonData);
}
}
```
**Motivation:** The Adapter pattern eliminates tight coupling with external services. Each payment gateway and delivery platform has its own API format (JSON, XML, different parameter structures). Without adapters, the system would need separate code paths for each service. Adapters provide a unified interface, making it trivial to add new payment methods or delivery platforms without changing existing code.

---

### Decorator Pattern

The Decorator pattern dynamically adds responsibilities to objects without modifying their structure. The system uses decorators to enhance food orders with optional features like express delivery, discount coupons, loyalty points, and special occasion messages. Multiple decorators can be stacked on any food item.

**Location:** `decorator/` package

**Structure:**
- **Component Interface:** `Food`
- **Concrete Components:** `Pizza`, `Burger`, `Salad`
- **Abstract Decorator:** `FoodDecorator`
- **Concrete Decorators:** `ExpressDeliveryDecorator`, `DiscountCouponDecorator`, `LoyaltyPointsDecorator`, `SpecialOccasionDecorator`

**Key Implementation - FoodDecorator.java:**
```
public abstract class FoodDecorator implements Food {
protected Food decoratedFood;
public FoodDecorator(Food food) {
this.decoratedFood = food;
}

@Override
public void prepare() {
decoratedFood.prepare();
}
}

```
**Key Implementation - DiscountCouponDecorator.java:**
```
public class DiscountCouponDecorator extends FoodDecorator {
private double discountPercentage;
@Override
public String getName() {
return decoratedFood.getName() + " [" + (int)discountPercentage + "% OFF]";
}

@Override
public double getPrice() {
double originalPrice = decoratedFood.getPrice();
return originalPrice - (originalPrice * discountPercentage / 100.0);
}
}
```

**Usage Example:**
```
// Stack multiple decorators
Food burger = new Burger();
Food enhanced = new DiscountCouponDecorator(burger, 20.0);
enhanced = new ExpressDeliveryDecorator(enhanced);
enhanced = new LoyaltyPointsDecorator(enhanced);

```
**Motivation:** Without decorators, adding optional features would require creating subclasses for every combination (`ExpressPizza`, `DiscountedPizza`, `ExpressDiscountedPizza`, etc.), leading to a class explosion. Decorators allow dynamic composition at runtime—customers can add any combination of enhancements to any food item, and the system automatically calculates the correct price and behavior.

---

### Facade Pattern

The Facade pattern provides a simplified interface to a complex subsystem. The **OrderFacade** coordinates five subsystems (inventory, payment, order management, delivery, notifications) through a single unified method, hiding the complexity of order processing from the client.

**Location:** `facade/` package

**Structure:**
- **Facade:** `OrderFacade`
- **Subsystems:** `InventoryService`, `PaymentService`, `DeliveryService`, `NotificationService`, `OrderManager`

**Key Implementation - OrderFacade.java:**
```
public class OrderFacade {
private PaymentService paymentService;
private InventoryService inventoryService;
private NotificationService notificationService;
private DeliveryService deliveryService;
private OrderManager orderManager;
public boolean placeCompleteOrder(String customerName, String address,
Food food, String paymentMethod,
boolean isExpress) {
// Step 1: Check and reserve inventory
if (!inventoryService.checkAvailability(food)) {
return false;
}
inventoryService.reserveItem(food);

    // Step 2: Process payment
    if (!paymentService.processPayment(customerName, food.getPrice(), paymentMethod)) {
        inventoryService.releaseItem(food);
        return false;
    }
    
    // Step 3: Create order
    var order = orderManager.placeOrder(customerName, food, null);
    food.prepare();
    
    // Step 4: Schedule delivery
    deliveryService.scheduleDelivery(customerName, address, isExpress);
    
    // Step 5: Send notifications
    notificationService.sendOrderConfirmation(customerName, order.getId(), food);
    
    return true;
}
}
```
**Client Usage:**
```
// Single method call instead of coordinating 5 subsystems
OrderFacade facade = new OrderFacade();
boolean success = facade.placeCompleteOrder(customerName, address, food,
paymentMethod, isExpress);

```
**Motivation:** Order processing requires coordinating multiple subsystems in a specific sequence with proper error handling (e.g., releasing inventory if payment fails). Without a facade, clients would need to understand and manage these complex interactions, leading to code duplication and tight coupling. The facade encapsulates this workflow into a single method, ensuring consistent order processing and simplifying client code dramatically.

---

## Conclusions

This laboratory work successfully implemented three structural design patterns in the food ordering system, building upon the creational patterns from Lab #1. The **Adapter pattern** enables seamless integration with multiple external services by converting incompatible interfaces, demonstrating system extensibility. The **Decorator pattern** provides runtime flexibility by dynamically adding optional features without modifying core classes, exemplifying the Open/Closed Principle. The **Facade pattern** simplifies complex multi-step order processing by coordinating five subsystems through a single interface, reducing client code complexity.

The project structure maintains clear separation of concerns with packages: `adapter/`, `decorator/`, `facade/`, alongside `factory/`, `builder/`, `singleton/`, `models/`, and `client/`. This modular architecture demonstrates how creational and structural patterns work harmoniously—factories create objects, builders assemble them, decorators enhance them dynamically, adapters integrate external systems, and facades coordinate workflows.

The implementation proves that structural patterns are essential for managing system complexity. The Adapter pattern demonstrates composition over inheritance. The Decorator pattern shows how behavior can be composed at runtime. The Facade pattern illustrates simplified interfaces hiding subsystem complexity. Future extensions could add new payment providers through additional adapters, new enhancements through decorators, or new workflows through facade methods—all without modifying existing code.

## Screenshots

![Platform Selection](./images/lab2_image1.png)

![Decorator Pattern - Adding Enhancements](./images/lab2_image2.png)

![Cart Review with Decorators](./images/lab2_image3.png)

![Facade Pattern - Complete Order Processing](./images/lab2_image4.png)

![Adapter Pattern - Delivery Platform Integration](./images/lab2_image5.png)

