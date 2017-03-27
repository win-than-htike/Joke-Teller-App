package com.winthan.joketeller.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;
import com.winthan.joketeller.JokeTellerApp;
import com.winthan.joketeller.data.database.JokeContract;

import java.util.List;

/**
 * Created by winthanhtike on 3/26/17.
 */

public class JokeVO {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("preview")
    private String preview;

    @SerializedName("description")
    private String description;

    @SerializedName("image_url")
    private String image_url;

    public JokeVO() {
    }

    public JokeVO(String id, String name, String preview, String description, String image_url) {
        this.id = id;
        this.name = name;
        this.preview = preview;
        this.description = description;
        this.image_url = image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public boolean equals(Object obj) {
        JokeVO joke = (JokeVO) obj;
        return this.id.equals(joke.id);
    }

    public static void saveJoke(List<JokeVO> jokeVOList) {

        Context context = JokeTellerApp.getContext();
        ContentValues[] jokeCVs = new ContentValues[jokeVOList.size()];
        for (int index = 0; index < jokeCVs.length; index++) {
            JokeVO jokeVO = jokeVOList.get(index);
            jokeCVs[index] = jokeVO.parseToContenValue();
            int count = context.getContentResolver().update(JokeContract.JokeEntity.CONTENT_URI, jokeCVs[index], JokeContract.JokeEntity._ID + " = ?", new String[]{jokeVO.id});
            if (count == 0) {
                context.getContentResolver().insert(JokeContract.JokeEntity.CONTENT_URI, jokeCVs[index]);
            }
        }

    }

    private ContentValues parseToContenValue() {
        ContentValues values = new ContentValues();
        values.put(JokeContract.JokeEntity._ID, id);
        values.put(JokeContract.JokeEntity.COLUMN_JOKE_NAME, name);
        values.put(JokeContract.JokeEntity.COLUMN_JOKE_PREVIEW, preview);
        values.put(JokeContract.JokeEntity.COLUMN_JOKE_DESCRIPTION, description);
        values.put(JokeContract.JokeEntity.COLUMN_JOKE_IMAGEURL, image_url);
        return values;
    }

    public static JokeVO parseFromCursor(Cursor data) {

        JokeVO jokeVO = new JokeVO();
        jokeVO.id = data.getString(data.getColumnIndex(JokeContract.JokeEntity._ID));
        jokeVO.name = data.getString(data.getColumnIndex(JokeContract.JokeEntity.COLUMN_JOKE_NAME));
        jokeVO.preview = data.getString(data.getColumnIndex(JokeContract.JokeEntity.COLUMN_JOKE_PREVIEW));
        jokeVO.description = data.getString(data.getColumnIndex(JokeContract.JokeEntity.COLUMN_JOKE_DESCRIPTION));
        jokeVO.image_url = data.getString(data.getColumnIndex(JokeContract.JokeEntity.COLUMN_JOKE_IMAGEURL));
        return jokeVO;

    }

}
