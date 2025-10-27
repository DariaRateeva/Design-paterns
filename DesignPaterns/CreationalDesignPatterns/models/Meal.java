package models;

public class Meal {
    private String mainDish;
    private String sideDish;
    private String drink;
    private String dessert;

    public Meal(String mainDish, String sideDish, String drink, String dessert) {
        this.mainDish = mainDish;
        this.sideDish = sideDish;
        this.drink = drink;
        this.dessert = dessert;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Custom Meal ===\n");
        sb.append("Main Dish: ").append(mainDish != null ? mainDish : "None").append("\n");
        sb.append("Side Dish: ").append(sideDish != null ? sideDish : "None").append("\n");
        sb.append("Drink: ").append(drink != null ? drink : "None").append("\n");
        sb.append("Dessert: ").append(dessert != null ? dessert : "None");
        return sb.toString();
    }

    public String getMainDish() { return mainDish; }
    public String getSideDish() { return sideDish; }
    public String getDrink() { return drink; }
    public String getDessert() { return dessert; }
}
