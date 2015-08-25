package com.spreys.trademeviewer.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created with Android Studio
 *
 * @author vspreys
 *         Date: 25/08/15
 *         Project: TradeMe Viewer
 *         Contact by: vlad@spreys.com
 */
public class TradeMeContract {

    public static final String CONTENT_AUTHORITY = "com.spreys.trademeviewer";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CATEGORY = "category";

    public static final class CategoryEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CATEGORY).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" +
                PATH_CATEGORY;
        public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/" + CONTENT_AUTHORITY +
                "/" + PATH_CATEGORY;

        public static final String TABLE_NAME = "category";

        public static final String COLUMN_LOC_NAME = "name";
        public static final String COLUMN_LOC_KEY = "number";
        public static final String COLUMN_LOC_PATH = "path";
        public static final String COLUMN_LOC_COUNT = "сount";
        public static final String COLUMN_LOC_IS_RESTRICTED = "isRestrictead";
        public static final String COLUMN_LOC_HAS_LEGAL_NOTICE = "hasLegalNotice";
        public static final String COLUMN_LOC_HAS_CLASSFIELD = "hasClassfieds";

        public static Uri buildCategoryUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
