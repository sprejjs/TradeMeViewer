package com.spreys.trademeviewer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.spreys.trademeviewer.Model.Category;
import com.spreys.trademeviewer.data.TradeMeContract.*;

/**
 * Created with Android Studio
 *
 * @author vspreys
 *         Date: 25/08/15
 *         Project: TradeMe Viewer
 *         Contact by: vlad@spreys.com
 */
public class TradeMeDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "trademe.db";

    public TradeMeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + CategoryEntry.TABLE_NAME + " (" +
                CategoryEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +

                CategoryEntry.COLUMN_LOC_NAME + "TEXT NOT NULL, " +
                CategoryEntry.COLUMN_LOC_KEY + "TEXT NOT NULL, " +
                CategoryEntry.COLUMN_LOC_PATH + "TEXT NOT NULL, " +
                CategoryEntry.COLUMN_LOC_COUNT + "INTEGER NOT NULL, " +
                CategoryEntry.COLUMN_LOC_IS_RESTRICTED + "INTEGER NOT NULL, " +
                CategoryEntry.COLUMN_LOC_HAS_LEGAL_NOTICE + "INTEGER NOT NULL, " +
                CategoryEntry.COLUMN_LOC_HAS_CLASSFIELD + ");";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + CategoryEntry.TABLE_NAME);
    }
}
