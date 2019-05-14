package com.example.hotel.ui.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hotel.R;

public class MainActivity extends AppCompatActivity {

    final Fragment homeFragment = new HomeFragment();
    final Fragment favoriteFragment = new FavoriteFragment();
    final Fragment mapFragment = new MapFragment();
    final Fragment userFragment = new UserFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm.beginTransaction().add(R.id.fragment_container, homeFragment, "1").commit();
        fm.beginTransaction().add(R.id.fragment_container, favoriteFragment, "2").hide(favoriteFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,mapFragment, "3").hide(mapFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,userFragment, "3").hide(userFragment).commit();

        BottomNavigationView botNav = findViewById(R.id.navigation);
        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        fm.beginTransaction().hide(active).show(homeFragment).commit();
                        active = homeFragment;
                        return true;

                    case R.id.navigation_favorite:
                        fm.beginTransaction().hide(active).show(favoriteFragment).commit();
                        active = favoriteFragment;
                        return true;

                    case R.id.navigation_search:
                        fm.beginTransaction().hide(active).show(mapFragment).commit();
                        active = mapFragment;
                        return true;
                    case R.id.navigation_account:
                        fm.beginTransaction().hide(active).show(userFragment).commit();
                        active = userFragment;
                        return true;
                }
                return false;
            }
        });
    }
}
