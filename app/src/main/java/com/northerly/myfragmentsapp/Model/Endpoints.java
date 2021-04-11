package com.northerly.myfragmentsapp.Model;

import com.northerly.myfragmentsapp.Model.PojoClass.Data;
import com.northerly.myfragmentsapp.Model.PojoClass.MyDataSet;
import com.northerly.myfragmentsapp.Model.PojoClass.Root;
import com.northerly.myfragmentsapp.Model.PojoClass.Root2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Endpoints {
    @GET("api/users")
    Call<Root> getUsers(
            @Query("page") String page
    );

    @GET("api/users/{id}")
    Call<Root2> getSingleUser(
            @Path("id") String id
    );

    @POST("api/users")
    Call<MyDataSet> postData(
            @Body MyDataSet myDataSet
    );
}
