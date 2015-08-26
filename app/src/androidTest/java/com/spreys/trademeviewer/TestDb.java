package com.spreys.trademeviewer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.spreys.trademeviewer.Model.Category;
import com.spreys.trademeviewer.data.TradeMeContract.*;
import com.spreys.trademeviewer.data.TradeMeDbHelper;

import java.util.Map;
import java.util.Set;

/**
 * Created with Android Studio
 *
 * @author vspreys
 *         Date: 25/08/15
 *         Project: TradeMe Viewer
 *         Contact by: vlad@spreys.com
 */
public class TestDb extends AndroidTestCase{

    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(TradeMeDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new TradeMeDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertReadDb() {
        TradeMeDbHelper dbHelper = new TradeMeDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testObject = createTestCategory();

        long locationRowId;

        //Insert test object
        locationRowId = db.insert(CategoryEntry.TABLE_NAME, null, testObject);
        assertTrue(locationRowId != -1);

        //Read it back
        Cursor cursor = db.query(CategoryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        //Test it
        validateCursor(cursor, testObject);
        cursor.close();
    }

    static ContentValues createTestCategory() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(CategoryEntry.COLUMN_NAME, "Caravans & motorhomes");
        testValues.put(CategoryEntry.COLUMN_NUMBER, "0001-0028-");
        testValues.put(CategoryEntry.COLUMN_PATH, "/Trade-Me-Motors/Caravans-motorhomes");

        return testValues;
    }

    static void validateCursor(Cursor valueCursor, ContentValues expectedValues) {

        assertTrue(valueCursor.moveToFirst());

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue, valueCursor.getString(idx));
        }
    }
}
