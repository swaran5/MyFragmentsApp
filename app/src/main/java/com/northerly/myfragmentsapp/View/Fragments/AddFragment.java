package com.northerly.myfragmentsapp.View.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.northerly.myfragmentsapp.Model.Endpoints;
import com.northerly.myfragmentsapp.Model.PojoClass.MyDataSet;
import com.northerly.myfragmentsapp.Model.PojoClass.Root;
import com.northerly.myfragmentsapp.Model.ServiceBuilder;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.View.Dialog.BottomSheet;
import com.northerly.myfragmentsapp.View.MainActivity;
import com.northerly.myfragmentsapp.ViewModel.AddUserViewModel;

import retrofit2.Call;
import retrofit2.Response;

public class AddFragment extends Fragment {
    MyDataSet myDataSet;
    RelativeLayout relativeLayoutAddUser;
    String name;
    String job;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_user,container,false);

        EditText editTextName = v.findViewById(R.id.editTextName);
        EditText editTextJob = v.findViewById(R.id.editTextJob);
        Button addButton = v.findViewById(R.id.add_button);
        FloatingActionButton floatingActionButton = v.findViewById(R.id.floatingActionButton);
        TextView textName = v.findViewById(R.id.addTextViewName);
        TextView textJob = v.findViewById(R.id.addTextViewJob);
        TextView textId = v.findViewById(R.id.addTextViewId);
        TextView textCreatedOn = v.findViewById(R.id.addTextViewCreatedOn);
        relativeLayoutAddUser = getActivity().findViewById(R.id.relativeLayout);

        AddUserViewModel addUserViewModel = ViewModelProviders.of(getActivity()).get(AddUserViewModel.class);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheet bottomSheet = new BottomSheet();
                bottomSheet.show(getActivity().getSupportFragmentManager(), "Bottom Sheet");
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
         @RequiresApi(api = Build.VERSION_CODES.M)
         @Override
         public void onClick(View v) {
             if(isConnected()) {
                 name = String.valueOf(editTextName.getText());
                 job = String.valueOf(editTextJob.getText());

                 myDataSet = new MyDataSet(name, job);
                 addUserViewModel.postUser(myDataSet);
                 new MainActivity().snackBarOnine(relativeLayoutAddUser);

             }
             else {
                 new MainActivity().snackBarOffline(relativeLayoutAddUser);
             }
         }
     });

        addUserViewModel.postedUser.observe(getActivity(), new Observer<MyDataSet>() {
            @Override
            public void onChanged(MyDataSet myDataSet) {
                textName.setText("Name : "+myDataSet.getName());
                textJob.setText("Job : "+myDataSet.getJob());
                textId.setText("Id : "+myDataSet.getId());
                textCreatedOn.setText("Created On : "+myDataSet.getCreatedAt());

            }
        });

   return v; }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
