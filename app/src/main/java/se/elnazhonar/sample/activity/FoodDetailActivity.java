package se.elnazhonar.sample.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

import se.elnazhonar.sample.R;
import se.elnazhonar.sample.data.model.Food;
import se.elnazhonar.sample.helper.FoodDataHelperImpl;


public class FoodDetailActivity extends BaseActivity {

    private final static String TAG = FoodDetailActivity.class.getSimpleName();
    private Interpolator mInterpolator;
    private FoodDataHelperImpl mDataHelper;
    private Food mFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        mDataHelper = new FoodDataHelperImpl(getApplicationContext());
        mInterpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.decelerate_cubic);
        mFood = getIntent().getParcelableExtra(Food.TAG);
        if (mFood == null) {
            Log.w(TAG, "Didn't find a food, finishing...!");
            this.finish();
        } else {
            populate(mFood);
        }
    }

    /**
     * Provides a launcher intent for this activity with the required data
     *
     * @param context context
     * @param food    the food object that will be shown in this detail page
     */
    public static Intent getStartIntent(Context context, Food food) {
        Intent starter = new Intent(context, FoodDetailActivity.class);
        starter.putExtra(Food.TAG, food);
        return starter;
    }

    /**
     * Populates the views with Food data and setups the toolbar
     *
     * @param food the food object
     */
    private void populate(Food food) {
        initToolbar(R.id.toolbar_widget, getResources().getString(R.string.nutrition_facts));
        initLayout(food);
    }

    private void initLayout(Food food) {
        ImageView mImage = (ImageView) findViewById(R.id.food_image);
        mImage.setScaleX(0);
        mImage.setScaleY(0);
        mImage.animate().scaleX(1).scaleY(1).setInterpolator(mInterpolator).setStartDelay(300);
        NumberFormat numberFormat = NumberFormat.getInstance(); // Format digits according to user's locale
        NumberFormat decimalFormat = NumberFormat.getInstance();
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);
        TextView title = (TextView) findViewById(R.id.food_title);
        title.setText(food.getTitle());
        TextView calories = (TextView) findViewById(R.id.food_calories_value);
        calories.setText(numberFormat.format(food.getCalories()));
        TextView protein = (TextView) findViewById(R.id.food_protein_value);
        protein.setText(decimalFormat.format(food.getProtein()));
        TextView carbohydrate = (TextView) findViewById(R.id.food_carbohydrate_value);
        carbohydrate.setText(decimalFormat.format(food.getCarbohydrates()));
        TextView fiber = (TextView) findViewById(R.id.food_fiber_value);
        fiber.setText(decimalFormat.format(food.getFiber()));
        TextView fat = (TextView) findViewById(R.id.food_fat_value);
        fat.setText(decimalFormat.format(food.getFat()));
    }

    @Override
    protected void initToolbar(int id, String toolbarTitle) {
        super.initToolbar(id, toolbarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_food_options, menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_favorite);
        if (mDataHelper.isFavourite(mFood.getFoodId())) {
            menuItem.setIcon(R.drawable.ic_favorite_filled_white);
            menuItem.setTitle(getString(R.string.is_favorite)); // title is set for helping uiautomator to recognize favorite foods
        } else {
            menuItem.setIcon(R.drawable.ic_favorite_border_white);
            menuItem.setTitle(getString(R.string.is_not_favorite));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_favorite:
                se.elnazhonar.sample.data.orm.generated.Food food = new se.elnazhonar.sample.data.orm.generated.Food(mFood.getFoodId(), mFood);
                if (mDataHelper.isFavourite(mFood.getFoodId())) {
                    mDataHelper.deleteFood(food);
                    menuItem.setIcon(R.drawable.ic_favorite_border_white);
                    menuItem.setTitle(getString(R.string.is_not_favorite));
                } else {
                    mDataHelper.addFood(food);
                    menuItem.setIcon(R.drawable.ic_favorite_filled_white);
                    menuItem.setTitle(getString(R.string.is_favorite));
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        final ImageView foodImage = (ImageView) findViewById(R.id.food_image);
        if (foodImage == null) {  // Skip the animation
            super.onBackPressed();
        } else {
            scaleImage(foodImage); // Scale the image to 0
        }
    }

    private void scaleImage(View foodImage) {
        foodImage.animate().scaleX(0).scaleY(0).setStartDelay(0)
                .setInterpolator(mInterpolator).setListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        FoodDetailActivity.super.onBackPressed();
                        super.onAnimationEnd(animation);
                    }
                });
    }
}