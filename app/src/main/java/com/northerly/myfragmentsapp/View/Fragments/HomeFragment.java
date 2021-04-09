package com.northerly.myfragmentsapp.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northerly.myfragmentsapp.Model.PojoClass.Data;
import com.northerly.myfragmentsapp.Model.Endpoints;
import com.northerly.myfragmentsapp.Model.PojoClass.Root;
import com.northerly.myfragmentsapp.Model.ServiceBuilder;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.View.MyAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    public Context context = this.getContext();
    private MyAdapter myAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        Endpoints request = ServiceBuilder.createService(Endpoints.class);
        Call<Root> call = request.getUsers("1");

        call.enqueue(new retrofit2.Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                response.body();
                List<Data> Users = response.body().getData();

                myAdapter = new MyAdapter(Users);
                recyclerView.setAdapter(myAdapter);


                myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String fname, String lname, String email) {

                        Bundle bundle = new Bundle();
                        bundle.putString("key1", fname);
                        bundle.putString("key2", lname);
                        bundle.putString("key3", email);

                        UserFragment userFragment = new UserFragment();
                        userFragment.setArguments(bundle);
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container,userFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });

            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
        return v;
    }
}
