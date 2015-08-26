package com.spreys.trademeviewer.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.spreys.trademeviewer.Model.Category;

/**
 * Created with Android Studio
 *
 * @author vspreys
 *         Date: 25/08/15
 *         Project: TradeMe Viewer
 *         Contact by: vlad@spreys.com
 */
public class TradeMeContentProvider extends ContentProvider {

    private static final int CATEGORY = 100;

    private static final UriMatcher sUriMather = buildUriMather();
    private TradeMeDbHelper mTradeMeHelper;

    private static UriMatcher buildUriMather() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TradeMeContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, TradeMeContract.PATH_CATEGORY, CATEGORY);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mTradeMeHelper = new TradeMeDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (sUriMather.match(uri)) {
            case CATEGORY:
                retCursor = mTradeMeHelper.getReadableDatabase().query(
                    TradeMeContract.CategoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMather.match(uri);
        switch (match) {
            case CATEGORY:
                return TradeMeContract.CategoryEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mTradeMeHelper.getWritableDatabase();
        final int match = sUriMather.match(uri);
        Uri returnUri;

        switch (match) {
            case CATEGORY: {
                long _id = db.insert(TradeMeContract.CategoryEntry.TABLE_NAME, null, values);
                if(_id > 0) {
                    returnUri = TradeMeContract.CategoryEntry.buildCategoryUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mTradeMeHelper.getWritableDatabase();
        final int match = sUriMather.match(uri);
        int numberOfAffectedRows;

        switch (match) {
            case CATEGORY:
                numberOfAffectedRows = db.delete(TradeMeContract.CategoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: + " + uri);
        }

        if(null == selection || 0 != numberOfAffectedRows) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfAffectedRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mTradeMeHelper.getWritableDatabase();
        final int match = sUriMather.match(uri);

        int numberOfAffectedRows;

        switch (match) {
            case CATEGORY:
                numberOfAffectedRows = db.update(TradeMeContract.CategoryEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numberOfAffectedRows;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mTradeMeHelper.getWritableDatabase();
        final int match = sUriMather.match(uri);

        clearCategoryTable();

        switch (match) {
            case CATEGORY:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value: values) {
                        long _id = db.insert(TradeMeContract.CategoryEntry.TABLE_NAME, null, value);
                        if(_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    /**
     * Clears the database.
     */
    private void clearCategoryTable() {
        final SQLiteDatabase db = mTradeMeHelper.getWritableDatabase();
        db.delete(TradeMeContract.CategoryEntry.TABLE_NAME, null, null);
    }
}
