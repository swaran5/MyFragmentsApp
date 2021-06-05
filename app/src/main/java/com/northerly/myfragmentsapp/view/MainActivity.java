package com.northerly.myfragmentsapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.view.Dialog.GoBackDialog;
import com.northerly.myfragmentsapp.view.Fragments.AddFragment;
import com.northerly.myfragmentsapp.view.Fragments.DBFragment;
import com.northerly.myfragmentsapp.view.Fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    Context context = this;
    GoBackDialog goBackDialog = new GoBackDialog();

    public BottomNavigationView bottomNav;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         bottomNav = findViewById(R.id.bottm_navigator);
         relativeLayout = findViewById(R.id.relativeLayout);
        bottomNav.setItemOnTouchListener(R.id.home_button, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                    if (isConnected()) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new HomeFragment(context))
                                .commit();
                        snackBarOnine(relativeLayout);
                        return false;
                    } else {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            snackBarOffline(relativeLayout);
                            return true;
                        }
                    }
                return true;}
        });

        bottomNav.setItemOnTouchListener(R.id.user_details, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!goBackDialog.isAdded()) {
                    goBackDialog.show(getSupportFragmentManager(), "Go Back");
                }
                return true;
            }
        });

        bottomNav.setItemOnTouchListener(R.id.add_user, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isConnected()) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new AddFragment(context))
                            .commit();
                    return false;
                }
                else{
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        snackBarOffline(relativeLayout);
                        return true;
                    }
                }
          return true;  }
        });

        bottomNav.setItemOnTouchListener(R.id.user_DB, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new DBFragment(context))
                            .commit();

                return false;
            }
        });

        if(isConnected()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment(context))
                    .commit();
            snackBarOnine(relativeLayout);
        }
        else{
                    snackBarOffline(relativeLayout);
        }

    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void snackBarOffline(RelativeLayout relativeLayout){
        Snackbar snackbar = Snackbar.make(relativeLayout,"Your Are Offline...",Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
    public void snackBarOnine(RelativeLayout relativeLayout){
        Snackbar snackbar = Snackbar.make(relativeLayout,"Api Call Successful..",Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
