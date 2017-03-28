package com.winthan.joketeller.clicklistener;

import com.winthan.joketeller.data.vos.JokeVO;

/**
 * Created by winthanhtike on 3/28/17.
 */

public interface ItemClickListener {

    void onTapJoke(JokeVO jokeVO, int position);

}
