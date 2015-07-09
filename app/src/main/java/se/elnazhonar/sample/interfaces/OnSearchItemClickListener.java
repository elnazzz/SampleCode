package se.elnazhonar.sample.interfaces;


import se.elnazhonar.sample.data.model.Food;


public interface OnSearchItemClickListener {
    void onFoodSetAsFavorite(Food food);
    void onSearchItemClick(Food food);
}
