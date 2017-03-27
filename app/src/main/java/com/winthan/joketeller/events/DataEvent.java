package com.winthan.joketeller.events;

import com.winthan.joketeller.data.vos.JokeVO;

import java.util.List;

/**
 * Created by winthanhtike on 3/26/17.
 */

public class DataEvent {

    public static class JokeDataLoadedEvent {

        private String message;

        private List<JokeVO> jokeVOList;

        public JokeDataLoadedEvent(String message, List<JokeVO> jokeVOList) {
            this.jokeVOList = jokeVOList;
            this.message = message;
        }

        public List<JokeVO> getJokeVOList() {
            return jokeVOList;
        }
    }

}
