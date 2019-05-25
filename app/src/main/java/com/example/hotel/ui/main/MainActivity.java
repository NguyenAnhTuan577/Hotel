package com.example.hotel.ui.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.MenuItem;

import com.example.hotel.R;
import com.example.hotel.model.Hotel;

public class MainActivity extends AppCompatActivity {

    final HomeFragment mHomeFragment = new HomeFragment();
    final FavoriteFragment mFavoriteFragment = new FavoriteFragment();
    final MapFragment mMapFragment = new MapFragment();
    final UserFragment mUserFragment = new UserFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = mHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fm.beginTransaction().add(R.id.fragment_container, mHomeFragment, "1").commit();
        fm.beginTransaction().add(R.id.fragment_container, mFavoriteFragment, "2").hide(mFavoriteFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, mMapFragment, "3").hide(mMapFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, mUserFragment, "3").hide(mUserFragment).commit();

        BottomNavigationView botNav = findViewById(R.id.navigation);
        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        fm.beginTransaction().hide(active).show(mHomeFragment).commit();
                        active = mHomeFragment;
                        return true;

                    case R.id.navigation_favorite:
                        fm.beginTransaction().hide(active).show(mFavoriteFragment).commit();
                        active = mFavoriteFragment;
                        return true;

                    case R.id.navigation_search:
                        fm.beginTransaction().hide(active).show(mMapFragment).commit();
                        active = mMapFragment;
                        return true;

                    case R.id.navigation_account:
                        fm.beginTransaction().hide(active).show(mUserFragment).commit();
                        active = mUserFragment;
                        return true;
                }
                return false;
            }
        });
    }

    public void changeFavoriteInFavoriteTab(Hotel hotel, int position) {
        if (mFavoriteFragment != null) mFavoriteFragment.changedFavoriteThisHotel(hotel, position);
    }

    public void changeFavoriteInHomeTab(Hotel hotel, int position) {
        if (mHomeFragment != null)
            mHomeFragment.changeFavoriteThisHotel(hotel, position);
    }
}
