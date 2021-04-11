package com.northerly.myfragmentsapp.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.northerly.myfragmentsapp.Model.Endpoints;
import com.northerly.myfragmentsapp.Model.PojoClass.MyDataSet;
import com.northerly.myfragmentsapp.Model.PojoClass.Root;
import com.northerly.myfragmentsapp.Model.ServiceBuilder;
import com.northerly.myfragmentsapp.R;

import retrofit2.Call;
import retrofit2.Response;

public class AddFragment extends Fragment {

    String name;
    String job;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_user,container,false);

        EditText editTextName = v.findViewById(R.id.editTextName);
        EditText editTextJob = v.findViewById(R.id.editTextJob);
        Button addButton = v.findViewById(R.id.add_button);
        TextView textName = v.findViewById(R.id.addTextViewName);
        TextView textJob = v.findViewById(R.id.addTextViewJob);
        TextView textId = v.findViewById(R.id.addTextViewId);
        TextView textCreatedOn = v.findViewById(R.id.addTextViewCreatedOn);


        addButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             name = String.valueOf(editTextName.getText());
             job = String.valueOf(editTextJob.getText());

             MyDataSet myDataSet = new MyDataSet(name,job);

             Endpoints request = ServiceBuilder.createService(Endpoints.class);

             Call<MyDataSet> call = request.postData(myDataSet);
             call.enqueue(new retrofit2.Callback<MyDataSet>() {
                 @Override
                 public void onResponse(Call<MyDataSet> call, Response<MyDataSet> response) {

                     if(response.body() != null) {
                         textName.setText("Name : "+response.body().getName());
                         textJob.setText("Job : "+response.body().getJob());
                         textId.setText("Id : "+response.body().getId());
                         textCreatedOn.setText("Created On : "+response.body().getCreatedAt());
                     }

                 }

                 @Override
                 public void onFailure(Call<MyDataSet> call, Throwable t) {
                        textName.setText("Connection Error");
                 }
             });

             Toast.makeText(getActivity() ,"Data Posted User Successfully",Toast.LENGTH_LONG).show();

         }
     });


   return v; }
}
