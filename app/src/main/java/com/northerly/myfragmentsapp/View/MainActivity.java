package com.northerly.myfragmentsapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.View.Fragments.AddFragment;
import com.northerly.myfragmentsapp.View.Fragments.HomeFragment;
import com.northerly.myfragmentsapp.View.Fragments.UserFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNav = findViewById(R.id.bottm_navigator);
        bottomNav.setOnNavigationItemSelectedListener(selectedListener);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

    }

    BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                  Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.add_user :
                            selectedFragment = new AddFragment();
                            break;
                        case R.id.home_button :
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.user_details :
                            selectedFragment = new UserFragment();
                            break;
                    }

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
               return true;
                }
            };

    }
