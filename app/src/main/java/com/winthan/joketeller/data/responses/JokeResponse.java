package com.winthan.joketeller.data.responses;

import com.google.gson.annotations.SerializedName;
import com.winthan.joketeller.data.vos.JokeVO;

import java.util.List;

/**
 * Created by winthanhtike on 3/26/17.
 */

public class JokeResponse {

    @SerializedName("data")
    private List<JokeVO> jokeVOList;

    public JokeResponse() {
    }

    public JokeResponse(List<JokeVO> jokeVOList) {
        this.jokeVOList = jokeVOList;
    }

    public List<JokeVO> getJokeVOList() {
        return jokeVOList;
    }

    public void setJokeVOList(List<JokeVO> jokeVOList) {
        this.jokeVOList = jokeVOList;
    }

}
