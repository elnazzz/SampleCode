package se.elnazhonar.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import se.elnazhonar.sample.R;
import se.elnazhonar.sample.fragment.FavouriteFoodsGridFragment;

public class StartActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initToolbar(R.id.toolbar_widget, getResources().getString(R.string.my_saved_foods));
        pushFragment(R.id.fragment_container, FavouriteFoodsGridFragment.newInstance(), FavouriteFoodsGridFragment.TAG, true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_search){
                startActivity(new Intent(StartActivity.this, SearchableActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
