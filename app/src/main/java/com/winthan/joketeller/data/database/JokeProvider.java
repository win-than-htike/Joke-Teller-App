package com.winthan.joketeller.data.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

import static android.R.attr.value;

/**
 * Created by winthanhtike on 3/27/17.
 */

public class JokeProvider extends ContentProvider {

    private static final int JOKE = 1;

    private static final int JOKE_ITEM = 10;

    private static final UriMatcher sUriMather = new UriMatcher(UriMatcher.NO_MATCH);

    private JokeDbHelper mDbHelper;

    static {

        sUriMather.addURI(JokeContract.CONTENT_AUTHORITY, JokeContract.PATH_JOKE, JOKE);
        sUriMather.addURI(JokeContract.CONTENT_AUTHORITY, JokeContract.PATH_JOKE + "/#", JOKE_ITEM);

    }

    @Override
    public boolean onCreate() {
        mDbHelper = new JokeDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor queryCursor;

        int match = sUriMather.match(uri);

        switch (match) {

            case JOKE:
                queryCursor = db.query(JokeContract.JokeEntity.TABLE_NAME, strings, s, strings1, null, null, s1);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);

        }

        Context context = getContext();
        if (context != null) {
            queryCursor.setNotificationUri(context.getContentResolver(), uri);
        }

        return queryCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        int match = sUriMather.match(uri);

        switch (match) {

            case JOKE:
                return JokeContract.JokeEntity.CONTENT_LIST_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);

        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMather.match(uri);

        Uri insertUri;

        switch (match) {

            case JOKE:
                long jokeInsertedId = db.insert(JokeContract.JokeEntity.TABLE_NAME, null, contentValues);
                if (jokeInsertedId > 0) {
                    insertUri = ContentUris.withAppendedId(uri, jokeInsertedId);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);

        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String tableName = getTableName(uri);

        int insertedCount = 0;

        try {

            db.beginTransaction();

            for (ContentValues cv : values){
                long _id = db.insert(tableName, null, cv);
                if (_id > 0){
                    insertedCount++;
                }
            }

            db.setTransactionSuccessful();

        }finally {
            db.endTransaction();
            db.close();
        }

        return insertedCount;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int rowDeleted;

        String tableName = getTableName(uri);

        rowDeleted = db.delete(tableName, s, strings);

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String tableName = getTableName(uri);

        int updatedRow = db.update(tableName, contentValues, s, strings);

        Context context = getContext();
        if (context != null){
            context.getContentResolver().notifyChange(uri, null);
        }

        return updatedRow;
    }

    private String getTableName(Uri uri) {

        int match = sUriMather.match(uri);

        switch (match) {

            case JOKE:
                return JokeContract.JokeEntity.TABLE_NAME;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);

        }

    }

}
