package com.example.nasapictureapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("raw/")
    Call<List<ImagesResponse>> getAllImages();
}
