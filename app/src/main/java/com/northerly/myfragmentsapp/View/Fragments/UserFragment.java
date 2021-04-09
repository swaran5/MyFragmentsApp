package com.northerly.myfragmentsapp.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.northerly.myfragmentsapp.R;
import com.squareup.picasso.Picasso;

public class UserFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        TextView firName = v.findViewById(R.id.firstname);
        TextView lasName = v.findViewById(R.id.lastname);
        TextView email = v.findViewById(R.id.email);
        ImageView avatar = v.findViewById(R.id.avatar);
        if (this.getArguments() != null) {
            Bundle bundle = this.getArguments();
            String firstName = bundle.getString("key1");
            String lastName = bundle.getString("key2");
            String emails = bundle.getString("key3");
            String url = bundle.getString("key4");

            firName.setText("First Name : "+firstName);
            lasName.setText("Last Name : "+lastName);
            email.setText("Email : "+emails);
            Picasso.get().load(url).into(avatar);

        }
        return v;
    }
}
