package com.task.webchallengetask.ui.modules.main;

import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.task.webchallengetask.App;
import com.task.webchallengetask.R;
import com.task.webchallengetask.data.data_managers.SharedPrefManager;
import com.task.webchallengetask.global.utils.image.CircleTransform;
import com.task.webchallengetask.ui.base.BaseActivity;
import com.task.webchallengetask.ui.dialogs.ProfileDialog;

public class MainActivity extends BaseActivity<MainActivityPresenter>
        implements MainActivityPresenter.MainView, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private ImageView ivNavAvatar;
    private TextView tvNavTitle;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public MainActivityPresenter initPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    protected int getFragmentContainer() {
        return R.id.fragment_container_CM;
    }

    @Override
    public void findUI(View _rootView) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        ivNavAvatar = (ImageView) (mNavigationView != null ? mNavigationView.getHeaderView(0).findViewById(R.id.ivAvatar_ND) : null);
        tvNavTitle = (TextView) (mNavigationView != null ? mNavigationView.getHeaderView(0).findViewById(R.id.tvTitle_ND) : null);
    }

    @Override
    public void setupUI() {
        getDelegate().setSupportActionBar(mToolbar);
        getDelegate().getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.setToolbarNavigationClickListener(v1 -> onBackPressed());

        mNavigationView.setNavigationItemSelectedListener(this);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            mDrawerToggle.setDrawerIndicatorEnabled(getSupportFragmentManager().getBackStackEntryCount() == 0);
            getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount() > 0);
            mDrawerToggle.syncState();
        });

        if (TextUtils.isEmpty(SharedPrefManager.getInstance().retrieveGender())) {
            showProfileDialog();
        }

    }

    private void showProfileDialog() {
        ProfileDialog dialog = new ProfileDialog();
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_activities:
                getPresenter().onActivityListClicked();
                break;
            case R.id.nav_analytics:
                getPresenter().onAnalyticsClicked();
                break;
            case R.id.nav_programs:
                getPresenter().onProgramsClicked();
                break;
            case R.id.nav_settings:
                getPresenter().onSettingsClicked();
                break;
            case R.id.nav_logout:
                getPresenter().onLogoutClicked();
                break;
        }
        return true;
    }


    @Override
    public boolean isDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void closeDrawer(DrawerCallBack _callback) {
        if (_callback != null)
            mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {

                }

                @Override
                public void onDrawerOpened(View drawerView) {

                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    _callback.onClosed();
                    mDrawerLayout.removeDrawerListener(this);
                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });
        new Handler().post(mDrawerLayout::closeDrawers);
    }

    @Override
    public void setHeaderTitle(String _title) {
        tvNavTitle.setText(_title);
    }

    @Override
    public void startSenderIntent(IntentSender _intentSender, int _const)
            throws IntentSender.SendIntentException {
        this.startIntentSenderForResult(_intentSender,
                _const, null, 0, 0, 0);
    }

    @Override
    public void setAvatar(String _path) {
        if (!TextUtils.isEmpty(_path)) {
            Picasso.with(App.getAppContext())
                    .load(SharedPrefManager.getInstance().retrieveUrlPhoto())
                    .transform(new CircleTransform())
                    .into(ivNavAvatar);
        }
    }

    public interface DrawerCallBack {
        void onClosed();
    }
}
