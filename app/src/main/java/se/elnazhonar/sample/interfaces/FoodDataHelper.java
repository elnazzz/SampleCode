package se.elnazhonar.sample.interfaces;

import java.util.List;

import se.elnazhonar.sample.data.orm.generated.Food;



public interface FoodDataHelper {

    List<Food> getFoods();
    void addFood(Food food);
    Food getFoodWith(long foodId);
    void deleteFood(Food food);
    boolean isFavourite(long foodId);

}
