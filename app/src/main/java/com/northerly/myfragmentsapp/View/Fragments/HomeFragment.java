package com.northerly.myfragmentsapp.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.northerly.myfragmentsapp.Model.PojoClass.Data;
import com.northerly.myfragmentsapp.Model.Endpoints;
import com.northerly.myfragmentsapp.Model.PojoClass.Root;
import com.northerly.myfragmentsapp.Model.ServiceBuilder;
import com.northerly.myfragmentsapp.R;
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
    private RecyclerView recyclerView;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        bottomNavigationView = getActivity().findViewById(R.id.bottm_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home_button);


        return v;
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
