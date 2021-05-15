package com.northerly.myfragmentsapp.ViewModel;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.northerly.myfragmentsapp.Model.Endpoints;
import com.northerly.myfragmentsapp.Model.PojoClass.Data;
import com.northerly.myfragmentsapp.Model.PojoClass.Root;
import com.northerly.myfragmentsapp.Model.ServiceBuilder;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.View.Fragments.UserFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    public MutableLiveData<List<Data>> Users = new MutableLiveData<>();
    Endpoints request = ServiceBuilder.createService(Endpoints.class);


    public MutableLiveData<List<Data>> getUsers(String page){

    Call<Root> call = request.getUsers(page);
    call.enqueue(new retrofit2.Callback<Root>() {
        @Override
        public void onResponse(Call<Root> call, Response<Root> response) {

                    Users.postValue(response.body().getData());

        }

        @Override
        public void onFailure(Call<Root> call, Throwable t) {

        }
    });
return Users;}
}
