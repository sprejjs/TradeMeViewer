package com.spreys.trademeviewer.sync;

import android.app.IntentService;
import android.content.Intent;

import com.spreys.trademeviewer.Model.Category;
import com.spreys.trademeviewer.NetworkCommunication.TradeMeApiWrapper;

import java.util.List;

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
    }
}
