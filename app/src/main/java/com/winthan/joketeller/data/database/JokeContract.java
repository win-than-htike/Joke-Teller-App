package com.winthan.joketeller.data.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by winthanhtike on 3/27/17.
 */

public class JokeContract {

    public static final String CONTENT_AUTHORITY = "com.winthan.joketeller";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_JOKE = "jokes";

    public static class JokeEntity implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_JOKE);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_JOKE;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_JOKE;

        public static final String TABLE_NAME = "jokes";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_JOKE_NAME = "name";

        public static final String COLUMN_JOKE_PREVIEW = "preview";

        public static final String COLUMN_JOKE_DESCRIPTION = "description";

        public static final String COLUMN_JOKE_IMAGEURL = "image_url";

    }

}
