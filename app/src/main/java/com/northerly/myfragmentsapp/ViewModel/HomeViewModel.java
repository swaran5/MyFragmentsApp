package com.northerly.myfragmentsapp.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.northerly.myfragmentsapp.Model.Endpoints;
import com.northerly.myfragmentsapp.Model.PojoClass.Data;
import com.northerly.myfragmentsapp.Model.PojoClass.Root;
import com.northerly.myfragmentsapp.Model.ServiceBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    public MutableLiveData<List<Data>> Users = new MutableLiveData<>();
    public List<Data> User = new ArrayList<>();
    public int totalpage = 1;
    Endpoints request = ServiceBuilder.createService(Endpoints.class);


    public MutableLiveData<List<Data>> getUsers(String page){
        if(Integer.valueOf(page) <=  totalpage) {

            Call<Root> call = request.getUsers(page);
            call.enqueue(new retrofit2.Callback<Root>() {
                @Override
                public void onResponse(Call<Root> call, Response<Root> response) {

//            User.addAll(response.body().getData());
                    totalpage = response.body().getTotal_pages();
                    Users.postValue(response.body().getData());
//            Users.postValue(User);

                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {

                }
            });
        }
return Users;}
}
