package com.northerly.myfragmentsapp.view.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.northerly.myfragmentsapp.BuildConfig;
import com.northerly.myfragmentsapp.Model.PojoClass.Data;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.view.Dialog.BottomSheetHome;
import com.northerly.myfragmentsapp.view.MyAdapter;
import com.northerly.myfragmentsapp.ViewModel.HomeViewModel;
import com.northerly.myfragmentsapp.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private MyAdapter myAdapter;
    public LinearLayoutManager manager;
    Context context;
    Boolean permiss = false;
    private RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    HomeViewModel homeViewModel;
    public FragmentHomeBinding binding;
    BottomNavigationView bottomNavigationView;
    int page = 1;
    List<Data> fullusers = new ArrayList<Data>();
    int currentItems, totalItems, scrollOutItems;
    boolean isScrolling = false;
    public HomeFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);
        View v = binding.getRoot();

        binding.setIsLoading(true);

        homeViewModel = ViewModelProviders.of((FragmentActivity) context).get(HomeViewModel.class);
        homeViewModel.getUsers(String.valueOf(page));
        recyclerView = v.findViewById(R.id.recyclerView);
        floatingActionButton = v.findViewById(R.id.floatingActionButtonhome);
        manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if(isScrolling && (totalItems == currentItems + scrollOutItems)){
                    isScrolling = false;
                    page = page + 1;
                    if(homeViewModel.totalpage >= page) {
                        binding.setIsBottomLoading(true);
                        homeViewModel.getUsers(String.valueOf(page));
                    }
                }
            }
        });
        myAdapter = new MyAdapter(getActivity(), fullusers);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

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

                    fullusers.addAll(data);

                    binding.setIsLoading(false);
                    binding.setIsBottomLoading(false);

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
