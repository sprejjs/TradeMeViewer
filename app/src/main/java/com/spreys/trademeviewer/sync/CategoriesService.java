package com.spreys.trademeviewer.sync;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;

import com.spreys.trademeviewer.Model.Category;
import com.spreys.trademeviewer.NetworkCommunication.TradeMeApiWrapper;
import com.spreys.trademeviewer.data.TradeMeContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created with Android Studio
 *
 * @author vspreys
 *         Date: 25/08/15
 *         Project: TradeMe Viewer
 *         Contact by: vlad@spreys.com
 */
public class CategoriesService extends IntentService {

    public CategoriesService() {
        super("CategoriesService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public CategoriesService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        TradeMeApiWrapper mApiWrapper = new TradeMeApiWrapper();
        List<Category> categories = mApiWrapper.getCategories();

        Vector<ContentValues> cVVector = new Vector<ContentValues>(categories.size());

        cVVector = extractSubcategories(cVVector, categories);

        this.getContentResolver().bulkInsert(
                TradeMeContract.CategoryEntry.CONTENT_URI,
                cVVector.toArray(new ContentValues[cVVector.size()])
        );
    }

    private Vector<ContentValues> extractSubcategories(Vector<ContentValues> cVVector, List<Category> categories) {
        for(Category category : categories){
            ContentValues values = new ContentValues();

            values.put(TradeMeContract.CategoryEntry.COLUMN_PARENT_ID, category.getParentId());
            values.put(TradeMeContract.CategoryEntry.COLUMN_NAME, category.getName());
            values.put(TradeMeContract.CategoryEntry.COLUMN_NUMBER, category.getNumber());
            values.put(TradeMeContract.CategoryEntry.COLUMN_PATH, category.getPath());

            cVVector.add(values);

            if(category.getSubcategories() != null && category.getSubcategories().size() > 0) {
                cVVector = extractSubcategories(cVVector, category.getSubcategories());
            }
        }

        return cVVector;
    }
}
