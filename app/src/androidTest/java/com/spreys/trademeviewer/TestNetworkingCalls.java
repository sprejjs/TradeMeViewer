package com.spreys.trademeviewer;

import android.test.AndroidTestCase;

import com.spreys.trademeviewer.Model.Category;
import com.spreys.trademeviewer.Model.Listing;
import com.spreys.trademeviewer.NetworkCommunication.TradeMeApiWrapper;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class TestNetworkingCalls extends AndroidTestCase {

    public void testGetAllCategories() {
        TradeMeApiWrapper apiWrapper = new TradeMeApiWrapper();

        List<Category> categories = apiWrapper.getCategories();

        assertEquals(true, categories.size() > 5);
        assertEquals(true, categories.get(0).getSubcategories().size() > 3);
    }

    public void testGetTradeMeMotors() {
        TradeMeApiWrapper apiWrapper = new TradeMeApiWrapper();

        List<Category> categories = apiWrapper.getCategories("0001-");

        assertEquals(13, categories.size());
        assertEquals("Aircraft", categories.get(0).getName());
    }

    public void testSearchAllMotors() {
        TradeMeApiWrapper apiWrapper = new TradeMeApiWrapper();

        List<Listing> listings = apiWrapper.searchListings("0001-");

        assertTrue(listings.size() > 20);
    }
}