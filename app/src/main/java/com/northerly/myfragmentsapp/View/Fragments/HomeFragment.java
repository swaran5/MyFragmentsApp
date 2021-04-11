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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    public Context context = this.getContext();
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    HomeViewModel homeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
            homeViewModel.getUsers();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel.Users.observe(getViewLifecycleOwner(), new Observer<List<Data>>() {
                @Override
                public void onChanged(List<Data> data) {

                    myAdapter = new MyAdapter(data);
                    recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();

                    myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int id) {

                            Bundle bundle = new Bundle();
                            bundle.putInt("key1", id);

                            UserFragment userFragment = new UserFragment();
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
