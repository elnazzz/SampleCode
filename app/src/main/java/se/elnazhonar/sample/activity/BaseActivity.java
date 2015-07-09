package se.elnazhonar.sample.activity;

import android.app.ProgressDialog;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import se.elnazhonar.sample.R;
import se.elnazhonar.sample.network.NetworkConfiguration;
import se.elnazhonar.sample.network.NetworkService;
import se.elnazhonar.sample.network.RestClientAdapter;
import se.elnazhonar.sample.network.UserInfo;

public class BaseActivity extends AppCompatActivity {


    protected Toolbar mToolbar;
    private ProgressDialog mLoaderDialog;

    /**
     * Convenience method to initialize the app toolbar
     *
     * @param toolbarId toolbar resource id
     */
    protected void initToolbar(@IdRes int toolbarId, String toolbarTitle) {
        mToolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(toolbarTitle);
    }

    /**
     * Create and return a REST adapter, which points to the backend
     */
    protected NetworkService getNetworkService() {
        return RestClientAdapter.createService(NetworkService.class, NetworkConfiguration.BASE_URL, UserInfo.getAuthToken());
    }

    /**
     * Convenience method to be used within all activities to push fragment to the fragment stack
     *
     * @param containerId resource id of the fragment's container layout
     * @param fragment    fragment
     * @param clearStack  boolean to indicate if the fragment stack needs to be clear before pushing this fragment
     */
    protected void pushFragment(int containerId, Fragment fragment, String tag, boolean clearStack) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (clearStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();

    }

    /**
     * Convenience method to be used within all activities to show a loader
     */
    protected void showLoader() {
        if (mLoaderDialog == null) {
            mLoaderDialog = new ProgressDialog(BaseActivity.this);
            mLoaderDialog.setIndeterminate(true);
            mLoaderDialog.setCancelable(true);
        }
        mLoaderDialog.show();

    }

    /**
     * Convenience method to be used within all activities to hide a loader
     */
    protected void hideLoader() {
        if (mLoaderDialog != null) {
            mLoaderDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() <= 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }


}
