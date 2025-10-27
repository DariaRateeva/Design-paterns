package builder;

import models.Meal;

// Abstract Builder interface
public interface IMealBuilder {
    IMealBuilder setMainDish(String mainDish);
    IMealBuilder setSideDish(String sideDish);
    IMealBuilder setDrink(String drink);
    IMealBuilder setDessert(String dessert);
    IMealBuilder reset();
    Meal build();
}
