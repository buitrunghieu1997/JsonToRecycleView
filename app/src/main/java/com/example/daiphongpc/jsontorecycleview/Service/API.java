package com.example.daiphongpc.jsontorecycleview.Service;

import com.example.daiphongpc.jsontorecycleview.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface API {
    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @Headers("Content-Type: application/json")
    @GET("users")
    Call<List<User>> getListUser();
}
