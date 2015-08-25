package com.spreys.trademeviewer;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.spreys.trademeviewer.data.TradeMeDbHelper;

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
}
