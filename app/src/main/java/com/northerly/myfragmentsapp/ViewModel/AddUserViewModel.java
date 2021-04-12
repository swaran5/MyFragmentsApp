package com.northerly.myfragmentsapp.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.northerly.myfragmentsapp.Model.Endpoints;
import com.northerly.myfragmentsapp.Model.PojoClass.MyDataSet;
import com.northerly.myfragmentsapp.Model.ServiceBuilder;

import retrofit2.Call;
import retrofit2.Response;

public class AddUserViewModel extends ViewModel {

    public MutableLiveData<MyDataSet> postedUser = new MutableLiveData<>();

    public MutableLiveData<MyDataSet> postUser (MyDataSet myDataSet){

        Endpoints request = ServiceBuilder.createService(Endpoints.class);
        Call<MyDataSet> call = request.postData(myDataSet);
        call.enqueue(new retrofit2.Callback<MyDataSet>() {
            @Override
            public void onResponse(Call<MyDataSet> call, Response<MyDataSet> response) {

                if(response.body() != null) {
                 postedUser.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<MyDataSet> call, Throwable t) {

            }
        });


    return postedUser;}
}
