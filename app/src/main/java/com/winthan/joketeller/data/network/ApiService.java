package com.winthan.joketeller.data.network;

import com.winthan.joketeller.data.responses.JokeResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by winthanhtike on 3/26/17.
 */

public interface ApiService {

    @GET("/api/joke")
    Call<JokeResponse> loadJokes();

}
