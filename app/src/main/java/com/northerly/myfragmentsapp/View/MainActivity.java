package com.northerly.myfragmentsapp.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.View.Fragments.AddFragment;
import com.northerly.myfragmentsapp.View.Fragments.HomeFragment;
import com.northerly.myfragmentsapp.View.Fragments.UserFragment;
import com.northerly.myfragmentsapp.ViewModel.HomeViewModel;

public class MainActivity extends AppCompatActivity {
    HomeViewModel home;
    Context context = this;
   public BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         bottomNav = findViewById(R.id.bottm_navigator);
        bottomNav.setItemOnTouchListener(R.id.home_button, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment(context))
                        .commit();
                return false;
            }
        });

        bottomNav.setItemOnTouchListener(R.id.user_details, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Toast.makeText(getApplication() ,"Please Select a User From Home",Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        bottomNav.setItemOnTouchListener(R.id.add_user, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AddFragment())
                        .commit();

                return false;
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment(context))
                .commit();


    }
}
