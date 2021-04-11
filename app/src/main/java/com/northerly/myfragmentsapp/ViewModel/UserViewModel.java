package com.northerly.myfragmentsapp.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.northerly.myfragmentsapp.Model.Endpoints;
import com.northerly.myfragmentsapp.Model.PojoClass.Data;
import com.northerly.myfragmentsapp.Model.PojoClass.Root;
import com.northerly.myfragmentsapp.Model.PojoClass.Root2;
import com.northerly.myfragmentsapp.Model.ServiceBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    public MutableLiveData<Data> user = new MutableLiveData<>();

    Endpoints request = ServiceBuilder.createService(Endpoints.class);

    public MutableLiveData<Data> getUser(String id){

        Call<Root2> call = request.getSingleUser(id);
        call.enqueue(new retrofit2.Callback<Root2>() {
            @Override
            public void onResponse(Call<Root2> call, Response<Root2> response) {

                user.postValue(response.body().getData());

            }

            @Override
            public void onFailure(Call<Root2> call, Throwable t) {

            }
        });
    return user;}
}
