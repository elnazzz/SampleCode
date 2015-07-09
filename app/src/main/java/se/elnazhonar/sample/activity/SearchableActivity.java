package se.elnazhonar.sample.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import se.elnazhonar.sample.R;
import se.elnazhonar.sample.adapter.SearchAdapter;
import se.elnazhonar.sample.data.model.Food;
import se.elnazhonar.sample.data.model.ResponseData;
import se.elnazhonar.sample.data.model.SearchResults;
import se.elnazhonar.sample.helper.FoodDataHelperImpl;
import se.elnazhonar.sample.interfaces.FoodDataHelper;
import se.elnazhonar.sample.interfaces.OnSearchItemClickListener;
import se.elnazhonar.sample.network.NetworkCallback;
import se.elnazhonar.sample.network.NetworkError;
import se.elnazhonar.sample.network.SearchRequest;

/**
 * A Searchable activity that receives the search query from caller and connects to the network to
 * fetch the search results from backend.
 * After it receives the results from backend, it presents the result to user.
 * This Activity has a single top lunch mode to avoid creating new instances over and over again and on top of each others.
 * <p/>
 */
public class SearchableActivity extends BaseActivity implements OnSearchItemClickListener {

    public static final String SAVED_INSTANCE_STATE_SEARCH_QUERY = "SAVED_INSTANCE_STATE_SEARCH_QUERY";
    private static final String TAG = SearchableActivity.class.getSimpleName();
    private SearchAdapter mAdapter;
    private FoodDataHelper mDataHelper;
    private String mSearchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        setUpViews();
        if (savedInstanceState != null) {
            mSearchQuery = savedInstanceState.getString(SAVED_INSTANCE_STATE_SEARCH_QUERY, "");
            searchBackend(mSearchQuery);
        } else {
            handleIntent(getIntent());
        }
    }

    private void setUpViews() {
        mDataHelper = new FoodDataHelperImpl(getApplicationContext());
        initToolbar(R.id.toolbar_widget, "");
        RecyclerView searchRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        searchRecyclerView.setHasFixedSize(true);
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new SearchAdapter(getApplicationContext(), mDataHelper, this);
        searchRecyclerView.setAdapter(mAdapter);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshAdapter();
    }

    private void refreshAdapter() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mSearchQuery = intent.getStringExtra(SearchManager.QUERY);
            searchBackend(mSearchQuery);
        }
    }


    /**
     * Call backend to get search results for the written search query
     *
     * @param query query term
     */
    private void searchBackend(String query) {
        showLoader();
        new SearchRequest(getNetworkService()).search(query, new NetworkCallback<ResponseData>() {
            @Override
            public void onSuccess(ResponseData responseData) {
                hideLoader();
                if (responseData != null) {
                    SearchResults results = responseData.getSearchResponse();
                    mAdapter.setDataSet(results.getSearchResultList());
                }
            }

            @Override
            public void onFailure(NetworkError error) {
                hideLoader();
                Toast.makeText(SearchableActivity.this, getResources().getString(R.string.generic_error), Toast.LENGTH_SHORT).show();
                Log.e(TAG, error.toString());
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_searchable, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }
        });
        menuItem.expandActionView();
        final SearchView searchView = (SearchView) menuItem.getActionView();
        if (!TextUtils.isEmpty(mSearchQuery)) { // if activity was re-created for instance during screen rotation, use the previous query
            searchView.setQuery(mSearchQuery, true);
        }
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }


    @Override
    public void onFoodSetAsFavorite(Food food) {
        se.elnazhonar.sample.data.orm.generated.Food foodCopy = new se.elnazhonar.sample.data.orm.generated.Food(food.getFoodId(), food);
        if (mDataHelper.isFavourite(food.getFoodId())) {
            mDataHelper.deleteFood(foodCopy);
        } else {
            mDataHelper.addFood(foodCopy);
        }
    }


    @Override
    public void onSearchItemClick(Food food) {
        Intent intent = FoodDetailActivity.getStartIntent(SearchableActivity.this, food);
        startActivity(intent);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!TextUtils.isEmpty(mSearchQuery)) {
            outState.putString(SAVED_INSTANCE_STATE_SEARCH_QUERY, mSearchQuery);
        }
    }
}
