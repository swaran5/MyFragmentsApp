package com.northerly.myfragmentsapp.View.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.style.LeadingMarginSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.northerly.myfragmentsapp.BuildConfig;
import com.northerly.myfragmentsapp.Model.PojoClass.Data;
import com.northerly.myfragmentsapp.Model.Endpoints;
import com.northerly.myfragmentsapp.Model.PojoClass.Root;
import com.northerly.myfragmentsapp.Model.ServiceBuilder;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.View.Dialog.BottomSheet;
import com.northerly.myfragmentsapp.View.Dialog.BottomSheetHome;
import com.northerly.myfragmentsapp.View.MainActivity;
import com.northerly.myfragmentsapp.View.MyAdapter;
import com.northerly.myfragmentsapp.ViewModel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private MyAdapter myAdapter;
    Context context;
    Boolean permiss = false;
    private RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    HomeViewModel homeViewModel;
    BottomNavigationView bottomNavigationView;
    public HomeFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel = ViewModelProviders.of((FragmentActivity) context).get(HomeViewModel.class);
        homeViewModel.getUsers("1");
        recyclerView = v.findViewById(R.id.recyclerView);
        floatingActionButton = v.findViewById(R.id.floatingActionButtonhome);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        bottomNavigationView = getActivity().findViewById(R.id.bottm_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home_button);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)+
                        ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)+
                        ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)+
                        ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CALL_PHONE)+
                        ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.SEND_SMS)+
                        ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CONTACTS)+
                        ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED)
                            {
                                Toast.makeText(getActivity(),"permission granted already", Toast.LENGTH_SHORT).show();
                                BottomSheetHome bottomSheethome = new BottomSheetHome();
                                bottomSheethome.show(getActivity().getSupportFragmentManager(), "Bottom Sheet");
                    }
                    else {
                        if(
                           ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE) ||
                           ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CAMERA) ||
                           ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) ||
                           ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CALL_PHONE) ||
                           ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.SEND_SMS) ||
                           ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_CONTACTS) ||
                           ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        ){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Needs permission")
                                    .setMessage("Please grant permission for this option..")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(
                                                    new String[]{
                                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                                            Manifest.permission.CAMERA,
                                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                                            Manifest.permission.CALL_PHONE,
                                                            Manifest.permission.SEND_SMS,
                                                            Manifest.permission.READ_CONTACTS,
                                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                    },
                                                    1
                                            );
                                        }
                                    })
                                    .setNegativeButton("Cancel", null);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                        else {
                            if( !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)&&
                                    permiss ||
                                    !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CAMERA) &&
                                            permiss ||
                                    !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)&&
                                            permiss ||
                                    !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CALL_PHONE)&&
                                            permiss ||
                                    !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.SEND_SMS) &&
                                            permiss ||
                                    !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_CONTACTS) &&
                                    permiss ||
                                    !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                                    permiss
                            ){
                                startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                            }
                            else {
                                requestPermissions(
                                        new String[]{
                                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.CAMERA,
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.CALL_PHONE,
                                                Manifest.permission.SEND_SMS,
                                                Manifest.permission.READ_CONTACTS,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        },
                                        1
                                );
                            }
                        }
                        }
                }
                else {
                    Toast.makeText(getActivity(),"permission granted default", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults[0]+
               grantResults[1]+
               grantResults[2]+
               grantResults[3]+
               grantResults[4]+
               grantResults[5]+
               grantResults[6]
                    == PackageManager.PERMISSION_GRANTED
                ) {
                BottomSheetHome bottomSheethome = new BottomSheetHome();
                bottomSheethome.show(getActivity().getSupportFragmentManager(), "Bottom Sheet");
            }
            else {
                if(!permiss) {
                    Toast.makeText(getActivity(), "Needs Permission For This Operation", Toast.LENGTH_SHORT).show();
                }
                permiss = true;
            }
            }
        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel.Users.observe(getActivity(), new Observer<List<Data>>() {
                @Override
                public void onChanged(List<Data> data) {

                    myAdapter = new MyAdapter(context,data);
                    recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();

                    myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int id) {

                            Bundle bundle = new Bundle();
                            bundle.putInt("key1", id);

                            UserFragment userFragment = new UserFragment(context);
                            userFragment.setArguments(bundle);
                            getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, userFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });
                }
            });

    }
}
