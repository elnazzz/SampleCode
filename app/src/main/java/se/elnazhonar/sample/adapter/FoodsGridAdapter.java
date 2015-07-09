package se.elnazhonar.sample.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import se.elnazhonar.sample.R;
import se.elnazhonar.sample.data.model.Food;
import se.elnazhonar.sample.interfaces.OnFavouriteFoodClickListener;

public class FoodsGridAdapter extends BaseAdapter {

    private List<Food> mItems;
    private Context mContext;
    private OnFavouriteFoodClickListener mListener;

    public static final int CALORIE_LOW_MARGIN = 200;
    public static final int CALORIE_HIGH_MARGIN = 550;

    public FoodsGridAdapter(Context context, List<Food> items, OnFavouriteFoodClickListener listener) {
        mContext = context;
        mItems = items;
        mListener = listener;
    }

    /**
     * A subclass of ViewHolder used in this adapter
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private ImageView mImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.food_grid_item_title);
            mImage = (ImageView) itemView.findViewById(R.id.plate_image);
        }
    }

    @Override
    public int getCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public Food getItem(int position) {
        if (mItems != null) {
            return mItems.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mItems != null) {
            return mItems.get(position).getFoodId();
        }
        return -1;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_food_grid, parent, false);
            convertView.setTag(new ItemViewHolder(convertView));
        }
        ItemViewHolder holder = (ItemViewHolder) convertView.getTag();
        if (mItems != null) {
            final Food item = mItems.get(position);
            holder.mTitle.setText(item.getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onFoodItemClick(item);
                }
            });
			holder.itemView.setContentDescription(item.getTitle());// Facilitates uiautomator to detect the clickable item
			holder.mImage.setImageDrawable(tintPlateDrawable(item));
        }
        return convertView;
    }


    /**
     * For Android versions that have API level 21 and upwards:
     * Tint the drawable with a color that corresponds the calorie in the food
     * Tints the plate drawable with green if the food contains less calories than {@link se.elnazhonar.sample.adapter #CALORIE_LOW_MARGIN }
     * Tints the plate drawable with yellow if the food contains higher calories than {@link se.elnazhonar.sample.adapter #CALORIE_LOW_MARGIN }
     * and less calories than {@link se.elnazhonar.sample.adapter #CALORIE_HIGH_MARGIN }
     * Tints the plate drawable with red if the food contains higher calories than {@link se.elnazhonar.sample.adapter #CALORIE_HIGH_MARGIN }
     * <p/>
     * Drawable will be colored to green for older Android versions that API level 21
     *
     * @param food food
     * @return Colored Drawable resource with appropriate color
     */
    private Drawable tintPlateDrawable(Food food) {
        if (food.getCalories() <= CALORIE_LOW_MARGIN) {
            return tintDrawable(R.drawable.ic_plate, R.color.color_branding_eight);
        } else if (food.getCalories() <= CALORIE_HIGH_MARGIN) {
            return tintDrawable(R.drawable.ic_plate, R.color.color_branding_seven);
        } else {
            return tintDrawable(R.drawable.ic_plate, R.color.color_branding_three);
        }

    }


    public void setDataSet(List<Food> items) {
        mItems = items;
        notifyDataSetChanged();
    }


    /**
     * Loads and tints the plate drawable
     *
     * @param resourceId drawable resource id
     * @param colorRes   color resource id
     * @return The tinted drawable
     */
    private Drawable tintDrawable(int resourceId, int colorRes) {
        final Drawable plateImage = ContextCompat.getDrawable(mContext, resourceId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (plateImage != null) {
                plateImage.setTint(mContext.getResources().getColor(colorRes));
            }
        } else {
            int color = mContext.getResources().getColor(R.color.color_branding_eight);
            if (plateImage != null) {
                plateImage.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
        }
        return plateImage;

    }

}

