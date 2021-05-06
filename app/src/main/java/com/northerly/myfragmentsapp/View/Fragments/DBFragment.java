package com.northerly.myfragmentsapp.View.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northerly.myfragmentsapp.Model.RoomDB.User;
import com.northerly.myfragmentsapp.Model.RoomDB.UserDao;
import com.northerly.myfragmentsapp.Model.RoomDB.UserDataBase;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.View.Helper.MyButton;
import com.northerly.myfragmentsapp.View.Helper.MySwipeHelper;
import com.northerly.myfragmentsapp.View.Listener.MyButtonClickListener;
import com.northerly.myfragmentsapp.View.MyDBAdapter;
import com.northerly.myfragmentsapp.ViewModel.DBViewModel;

import java.util.List;

public class DBFragment extends Fragment {

    Context context;
    MyDBAdapter myDBAdapter;
    MutableLiveData<List<User>> listuser;


    RecyclerView dbrecyclerview;
    public DBFragment(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmet_user_db, container, false);

        DBViewModel dbViewModel = ViewModelProviders.of(getActivity()).get(DBViewModel.class);

        dbViewModel.getuser();
        listuser = dbViewModel.getUser();

        listuser.observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                dbrecyclerview = v.findViewById(R.id.db_recyclerView);
                dbrecyclerview.setLayoutManager(new LinearLayoutManager(context));
                myDBAdapter = new MyDBAdapter(context, users);
                dbrecyclerview.setAdapter(myDBAdapter);

                MySwipeHelper swipe = new MySwipeHelper(getActivity(), dbrecyclerview, 200) {
                    @Override
                    public void instanciateMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
                        buffer.add(new MyButton(getActivity(),
                                "Delete",
                                30,
                                R.drawable.ic_baseline_delete_24,
                                Color.BLUE,
                                new MyButtonClickListener() {
                                    @Override
                                    public void onClick(int pos) {
                                        Toast.makeText(getActivity(), "Delete User", Toast.LENGTH_SHORT).show();
                                    }
                                }));
                        buffer.add(new MyButton(getActivity(),
                                "Delete",
                                30,
                                R.drawable.ic_baseline_edit_24,
                                Color.GREEN,
                                new MyButtonClickListener() {
                                    @Override
                                    public void onClick(int pos) {
                                        Toast.makeText(getActivity(), "Update User", Toast.LENGTH_SHORT).show();
                                    }
                                }));
                    }
                };
            }
        });

        return v;
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
