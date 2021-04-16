package com.northerly.myfragmentsapp.View.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northerly.myfragmentsapp.Model.RoomDB.User;
import com.northerly.myfragmentsapp.Model.RoomDB.UserDao;
import com.northerly.myfragmentsapp.Model.RoomDB.UserDataBase;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.View.MyDBAdapter;

import java.util.List;

public class DBFragment extends Fragment {

    Context context;
    MyDBAdapter myDBAdapter;
    List<User> listusers;
    UserDataBase userDB;
    UserDao userDao;

    RecyclerView dbrecyclerview;
    public DBFragment(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmet_user_db, container, false);

        userDB = UserDataBase.getDataBase(getActivity().getApplicationContext());
        userDao = userDB.userDao();
        GettAsync gettAsync = new GettAsync();
        gettAsync.execute();

        dbrecyclerview = v.findViewById(R.id.db_recyclerView);
        dbrecyclerview.setLayoutManager(new LinearLayoutManager(context));

        return v;
    }

    public class GettAsync extends AsyncTask<Void, Void, List<User>>{

        @Override
        protected List<User> doInBackground(Void... voids) {
            return userDao.getAllUsers();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            listusers = users;
            myDBAdapter = new MyDBAdapter(context, listusers);
            dbrecyclerview.setAdapter(myDBAdapter);
            myDBAdapter.notifyDataSetChanged();
        }
    }
}
