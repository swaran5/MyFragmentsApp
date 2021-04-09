package com.northerly.myfragmentsapp.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.northerly.myfragmentsapp.R;

public class UserFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        TextView userdetails = v.findViewById(R.id.user_detail);
        if (this.getArguments() != null) {
            Bundle bundle = this.getArguments();
            String firstName = bundle.getString("key1");
            String lastName = bundle.getString("key2");
            String email = bundle.getString("key3");

            userdetails.setText(firstName+"\n"+ lastName + "\n" + email);

        }
        return v;
    }
}
