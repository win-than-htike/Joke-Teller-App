package com.winthan.joketeller.data.network;

import android.util.Log;

import com.winthan.joketeller.BuildConfig;
import com.winthan.joketeller.data.models.JokeModel;
import com.winthan.joketeller.data.responses.JokeResponse;
import com.winthan.joketeller.data.vos.JokeVO;
import com.winthan.joketeller.events.DataEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by winthanhtike on 3/26/17.
 */

public class ApiRetrofit implements DataAgent{

    private static ApiRetrofit objInstance;

    private ApiService mService;

    private ApiRetrofit(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.157:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(ApiService.class);

    }

    public static ApiRetrofit getInstance(){
        if (objInstance == null){
            objInstance = new ApiRetrofit();
        }
        return objInstance;
    }

    @Override
    public void loadJokes() {

        Log.i("call", "network");
        Call<JokeResponse> call = mService.loadJokes();
        call.enqueue(new Callback<JokeResponse>() {
            @Override
            public void onResponse(Call<JokeResponse> call, Response<JokeResponse> response) {
                if (response.isSuccessful()){

                    List<JokeVO> jokeVOList = response.body().getJokeVOList();
                    Log.i("jokecount", jokeVOList.size()+"");
                    if (jokeVOList.size() > 0){
                        JokeModel.getInstance().notifyDataLoaded(jokeVOList, "success");
                    }
                }
            }

            @Override
            public void onFailure(Call<JokeResponse> call, Throwable t) {
                JokeModel.getInstance().notifyError(t.getMessage());
            }
        });

    }
}
