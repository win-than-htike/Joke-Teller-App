package com.winthan.joketeller.data.models;

import com.winthan.joketeller.data.network.ApiRetrofit;
import com.winthan.joketeller.data.vos.JokeVO;
import com.winthan.joketeller.events.DataEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winthanhtike on 3/26/17.
 */

public class JokeModel {

    private static JokeModel objInstance;

    private List<JokeVO> jokeVOList;

    private JokeModel() {
        jokeVOList = new ArrayList<>();
        ApiRetrofit.getInstance().loadJokes();
    }

    public static JokeModel getInstance() {
        if (objInstance == null) {
            objInstance = new JokeModel();
        }
        return objInstance;
    }

    public List<JokeVO> getJokeVOList() {
        return jokeVOList;
    }

    public void notifyDataLoaded(List<JokeVO> jokeVOList, String message) {

        this.jokeVOList = jokeVOList;
        JokeVO.saveJoke(jokeVOList);
        EventBus.getDefault().post(new DataEvent.JokeDataLoadedEvent(message,jokeVOList));

    }

    public void setStoredData(List<JokeVO> jokeVOList){
        this.jokeVOList = jokeVOList;
    }

    public void notifyError(String message) {
    }
}
