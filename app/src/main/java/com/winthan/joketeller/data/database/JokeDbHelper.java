package com.winthan.joketeller.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.version;

/**
 * Created by winthanhtike on 3/27/17.
 */

public class JokeDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "joketeller";

    private static final int DATABASE_VERSION = 1;

    public JokeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createJokeTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + JokeContract.JokeEntity.TABLE_NAME);
    }

    public String createJokeTable() {

        String jokeTable = "CREATE TABLE " + JokeContract.JokeEntity.TABLE_NAME + "( "
                + JokeContract.JokeEntity._ID + " INTEGER PRIMARY KEY, "
                + JokeContract.JokeEntity.COLUMN_JOKE_NAME + " TEXT, "
                + JokeContract.JokeEntity.COLUMN_JOKE_PREVIEW + " TEXT, "
                + JokeContract.JokeEntity.COLUMN_JOKE_DESCRIPTION + " TEXT, "
                + JokeContract.JokeEntity.COLUMN_JOKE_IMAGEURL + " TEXT);";

        return jokeTable;

    }

}
