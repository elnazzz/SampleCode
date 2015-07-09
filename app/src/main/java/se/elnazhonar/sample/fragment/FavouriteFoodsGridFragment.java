package se.elnazhonar.sample.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import se.elnazhonar.sample.R;
import se.elnazhonar.sample.activity.FoodDetailActivity;
import se.elnazhonar.sample.adapter.FoodsGridAdapter;
import se.elnazhonar.sample.data.model.Food;
import se.elnazhonar.sample.helper.FoodDataHelperImpl;
import se.elnazhonar.sample.interfaces.FoodDataHelper;
import se.elnazhonar.sample.interfaces.OnFavouriteFoodClickListener;


public class FavouriteFoodsGridFragment extends Fragment implements OnFavouriteFoodClickListener {


    public final static String TAG = FavouriteFoodsGridFragment.class.getSimpleName();
    private FoodDataHelper mDataHelper;
    private FoodsGridAdapter mAdapter;

    public static FavouriteFoodsGridFragment newInstance() {
        return new FavouriteFoodsGridFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_foods_grid, container, false);
        setUpGrid((GridView) view.findViewById(R.id.categories));
        return view;
    }


    private void setUpGrid(GridView foodsGridView) {
        foodsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mDataHelper = new FoodDataHelperImpl(getActivity().getApplicationContext());
        List<Food> uiFoods = new ArrayList<>();
        List<se.elnazhonar.sample.data.orm.generated.Food> foods = mDataHelper.getFoods();
        if (foods != null) {
            for (int i = 0; i < foods.size(); i++) {
                se.elnazhonar.sample.data.model.Food food = new se.elnazhonar.sample.data.model.Food(foods.get(i));
                uiFoods.add(food);
            }
        }
        mAdapter = new FoodsGridAdapter(getActivity(), uiFoods, this);
        foodsGridView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        updateShownSavedFood();
        super.onResume();
    }

    private void updateShownSavedFood() {
        List<se.elnazhonar.sample.data.orm.generated.Food> foods = mDataHelper.getFoods();
        List<se.elnazhonar.sample.data.model.Food> uiFoods = new ArrayList<>();
        for (int i = 0; i < foods.size(); i++) {
            se.elnazhonar.sample.data.model.Food food = new se.elnazhonar.sample.data.model.Food(foods.get(i));
            uiFoods.add(food);
        }
        mAdapter.setDataSet(uiFoods);
    }

    @Override
    public void onFoodItemClick(Food food) {
        Intent intent = FoodDetailActivity.getStartIntent(getActivity(), food);
        startActivity(intent);
    }



}
