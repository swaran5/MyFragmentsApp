package com.northerly.myfragmentsapp.Model;

import com.northerly.myfragmentsapp.Model.PojoClass.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Endpoints {
    @GET("api/users")
    Call<Root> getUsers(
            @Query("page") String page
    );
}
