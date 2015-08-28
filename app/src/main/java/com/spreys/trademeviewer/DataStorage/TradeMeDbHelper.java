package com.spreys.trademeviewer.DataStorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.spreys.trademeviewer.DataStorage.TradeMeContract.*;

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
                CategoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                CategoryEntry.COLUMN_PARENT_ID + " TEXT, " +
                CategoryEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                CategoryEntry.COLUMN_NUMBER + " TEXT NOT NULL, " +
                CategoryEntry.COLUMN_PATH + " TEXT NOT NULL, " +
                CategoryEntry.COLUMN_COUNT + " INTEGER, " +
                CategoryEntry.COLUMN_IS_RESTRICTED + " INTEGER, " +
                CategoryEntry.COLUMN_HAS_LEGAL_NOTICE + " INTEGER, " +
                CategoryEntry.COLUMN_SUBCATEGORIES_COUNT + " INTEGER, " +
                CategoryEntry.COLUMN_HAS_CLASSFIELD + ");";

        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + CategoryEntry.TABLE_NAME);
    }
}
