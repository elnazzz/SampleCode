package se.elnazhonar.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import se.elnazhonar.sample.R;
import se.elnazhonar.sample.data.model.Food;
import se.elnazhonar.sample.interfaces.FoodDataHelper;
import se.elnazhonar.sample.interfaces.OnSearchItemClickListener;



public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchItemViewHolder> {

    private List<Food> mSearchResults;
    private Context mContext;
    private OnSearchItemClickListener mListener;
    private FoodDataHelper mDataHelper;

    public SearchAdapter(Context context, FoodDataHelper dataHelper, OnSearchItemClickListener listener) {
        mContext = context;
        mDataHelper = dataHelper;
        mListener = listener;
    }

    public class SearchItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mInfoText;
        private ImageButton mFavouriteButton;

        public SearchItemViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.food_title);
            mInfoText = (TextView) itemView.findViewById(R.id.food_info);
            mFavouriteButton = (ImageButton) itemView.findViewById(R.id.btn_favourite);
        }
    }


    @Override
    public void onBindViewHolder(final SearchItemViewHolder holder, int position) {
        if (mSearchResults != null) {
            final Food item = mSearchResults.get(position);
            holder.mTitle.setText(item.getTitle());
            if (mDataHelper.isFavourite(item.getFoodId())) {
                holder.mFavouriteButton.setImageResource(R.drawable.ic_favorite_filled_white);
                holder.mFavouriteButton.setContentDescription(mContext.getString(R.string.is_favorite));// content description is set for helping uiautomator to recognize favorite foods
            } else {
                holder.mFavouriteButton.setImageResource(R.drawable.ic_favorite_border_white);
                holder.mFavouriteButton.setContentDescription(mContext.getString(R.string.is_not_favorite));
            }
            NumberFormat numberFormat = NumberFormat.getInstance(); // Format digits according to user's locale
            holder.mInfoText.setText(numberFormat.format(item.getCalories()) + " " + mContext.getString(R.string.calories));
            holder.mFavouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDataHelper.isFavourite(item.getFoodId())) {
                        holder.mFavouriteButton.setImageResource(R.drawable.ic_favorite_border_white);
                        holder.mFavouriteButton.setContentDescription(mContext.getString(R.string.is_not_favorite));
                    } else {
                        holder.mFavouriteButton.setImageResource(R.drawable.ic_favorite_filled_white);
                        holder.mFavouriteButton.setContentDescription(mContext.getString(R.string.is_favorite));
                    }
                    mListener.onFoodSetAsFavorite(item);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onSearchItemClick(item);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (mSearchResults != null) {
            return mSearchResults.size();
        }
        return 0;
    }

    @Override
    public SearchItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_search, parent, false);
        return new SearchItemViewHolder(view);
    }


    public void setDataSet(List<Food> items) {
        mSearchResults = items;
        notifyDataSetChanged();
    }
}
