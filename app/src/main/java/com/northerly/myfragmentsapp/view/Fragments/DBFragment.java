package com.northerly.myfragmentsapp.view.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.northerly.myfragmentsapp.Model.RoomDB.User;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.view.Dialog.LoadingDialog;
import com.northerly.myfragmentsapp.view.Helper.SwipeHelper;
import com.northerly.myfragmentsapp.view.MyDBAdapter;
import com.northerly.myfragmentsapp.view.PDFViewerActivity;
import com.northerly.myfragmentsapp.ViewModel.DBViewModel;

import java.util.List;

public class DBFragment extends Fragment {

    Context context;
    MyDBAdapter myDBAdapter;
    MutableLiveData<List<User>> listuser;
    Boolean chk = false;
    LoadingDialog loadingDialog;
    BottomNavigationView bottomNavigationView;
    RecyclerView dbrecyclerview;
    public DBFragment(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bottomNavigationView = getActivity().findViewById(R.id.bottm_navigator);
        bottomNavigationView.setSelectedItemId(R.id.user_DB);
        View v = inflater.inflate(R.layout.fragmet_user_db, container, false);

        DBViewModel dbViewModel = ViewModelProviders.of(getActivity()).get(DBViewModel.class);

        dbViewModel.getuser();
        listuser = dbViewModel.getUser();
        dbrecyclerview = v.findViewById(R.id.db_recyclerView);


        listuser.observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                dbrecyclerview.setLayoutManager(new LinearLayoutManager(context));
                myDBAdapter = new MyDBAdapter(context, users);
                dbrecyclerview.setAdapter(myDBAdapter);

                myDBAdapter.setOnItemClickListener(new MyDBAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String userPhone) {
                        loadingDialog = new LoadingDialog(getActivity());
                        loadingDialog.startLoadingDialog();
                        chk = true;
                        Intent intent = new Intent(getActivity(), PDFViewerActivity.class);
                        intent.putExtra("pnumber", userPhone );
                        startActivity(intent);
                    }
                });
            }
        });

        SwipeHelper swipeHelper = new SwipeHelper(getActivity(), dbrecyclerview, false ) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {

                underlayButtons.add(new UnderlayButton(
                        "Delete",
                        AppCompatResources.getDrawable(getActivity(),
                                R.drawable.ic_baseline_delete_24),
                        Color.RED,
                        Color.WHITE,
                        new UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(getActivity(), "Delete button clicked..."+ pos, Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                ));
            }
        };
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (chk) {
            loadingDialog.dismissDialog();
        }
    }

    //    public class GettAsync extends AsyncTask<Void, Void, List<User>>{
//
//        @Override
//        protected List<User> doInBackground(Void... voids) {
//            return userDao.getAllUsers();
//        }
//
//        @Override
//        protected void onPostExecute(List<User> users) {
//            listusers = users;
//            myDBAdapter = new MyDBAdapter(context, listusers);
//            dbrecyclerview.setAdapter(myDBAdapter);
//            myDBAdapter.notifyDataSetChanged();
//        }
//    }

}
