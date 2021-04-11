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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.northerly.myfragmentsapp.Model.PojoClass.Data;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.ViewModel.HomeViewModel;
import com.northerly.myfragmentsapp.ViewModel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserFragment extends Fragment {
    UserViewModel userViewModel;
    String fname;
    String lname;
    String email;
    String url;
    TextView firName;
    TextView lasName;
    TextView emails;
    ImageView avatar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
         firName = v.findViewById(R.id.firstname);

         lasName = v.findViewById(R.id.lastname);
         emails = v.findViewById(R.id.email);
         avatar = v.findViewById(R.id.avatar);

        if (this.getArguments() != null) {
            Bundle bundle = this.getArguments();
            String id = String.valueOf(bundle.getInt("key1"));
            userViewModel.getUser(id);

      }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel.user.observe(getViewLifecycleOwner(), new Observer<Data>() {
            @Override
            public void onChanged(Data data) {
                fname = data.getFirst_name();
                firName.setText("Frist Name : "+fname);
                lname = data.getLast_name();
                lasName.setText("Last Name : "+lname);
                email = data.getEmail();
                emails.setText("Email : "+email);
                url = data.getAvatar();
                Picasso.get().load(url).into(avatar);

            }
        });
    }

}
