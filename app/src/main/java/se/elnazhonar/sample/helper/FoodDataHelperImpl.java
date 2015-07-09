package se.elnazhonar.sample.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import se.elnazhonar.sample.data.orm.generated.DaoMaster;
import se.elnazhonar.sample.data.orm.generated.DaoSession;
import se.elnazhonar.sample.data.orm.generated.Food;
import se.elnazhonar.sample.data.orm.generated.FoodDao;
import se.elnazhonar.sample.interfaces.FoodDataHelper;

/**
 * Helper class providing convenience methods to interact with Food DAO
 *
 */
public class FoodDataHelperImpl implements FoodDataHelper {

    private Context mContext;
    private DaoMaster.DevOpenHelper mOpenHelper;
    private SQLiteDatabase mDatabase;
    private DaoSession mDaoSession;

    public FoodDataHelperImpl(Context context) {
        mContext = context;
    }


    private FoodDao retrieveFoodDoa() {
        mOpenHelper = new DaoMaster.DevOpenHelper(mContext, "FOOD_DB.sqlite", null);
        mDatabase = mOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(mDatabase);
        mDaoSession = daoMaster.newSession();
        return mDaoSession.getFoodDao();
    }

    private void close() {
        mDaoSession.clear();
        mDatabase.close();
        mOpenHelper.close();
    }


    @Override
    public List<Food> getFoods() {
        List<Food> foods = retrieveFoodDoa().loadAll();
        close();
        return foods;
    }


    @Override
    public void addFood(Food food) {
        retrieveFoodDoa().insertOrReplace(food);
        close();
    }

    @Override
    public Food getFoodWith(long foodId) {
        Food food = retrieveFoodDoa().queryBuilder().where(FoodDao.Properties.Foodid.eq(foodId)).unique();
        close();
        return food;
    }

    @Override
    public void deleteFood(Food food) {
        retrieveFoodDoa().delete(food);
        close();
    }

    @Override
    public boolean isFavourite(long foodId) {
        Food food = getFoodWith(foodId);
        if (food == null) {
            return false;
        }
        return true;
    }

}
