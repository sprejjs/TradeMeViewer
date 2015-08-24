package com.spreys.trademeviewer.NetworkCommunication;

import com.spreys.trademeviewer.Model.Category;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created with Android Studio
 *
 * @author vspreys
 *         Date: 24/08/15
 *         Project: TradeMe Viewer
 *         Contact by: vlad@spreys.com
 */
public class TradeMeApiWrapper {
    private final static String BASE_API_URL = "https://api.trademe.co.nz/v1/";
    private final static String API_URL_CATEGORIES = "categories";
    private final static String API_FORMAT_JSON = ".json";
    private final static String API_FORMAT_XML = ".xml";

    private boolean useJson = true;

    /**
     * Returns the currently selected API format. Can be modified by setting setApiFormat() method.
     * Defaults to JSON.
     * @return Extension to the API format as string. Either ".json" or ".xml"
     */
    private String apiFormat() {
        if(useJson) {
            return API_FORMAT_JSON;
        } else {
            return API_FORMAT_XML;
        }
    }

    /**
     * Method allows to set the messages format returned from the API to JSON
     */
    public void useJson(){
        this.useJson = true;
    }

    /**
     * Method allows to set the messages formt returned from the API to XML
     */
    public void useXml() {
        this.useJson = false;
    }

    /**
     * Returns all of the categories from the TradeMe API.
     * @return the list of categories.
     */
    public List<Category> getCategories() {
        String url = BASE_API_URL + API_URL_CATEGORIES + apiFormat();
        return new Category(getStringFromUrl(url)).getSubcategories();
    }

    /**
     * Returns the list of categories for the supplied category id
     * @param categoryId Has to be a valid TradeMe category id
     * @return list of subcategories for the supplied category id
     */
    public List<Category> getCategories(String categoryId){
        String url = BASE_API_URL + API_URL_CATEGORIES + "/" + categoryId + apiFormat();
        return new Category(getStringFromUrl(url)).getSubcategories();
    }

    private JSONObject getJsonObjectFromUrl(String url) {
        try {
            return new JSONObject(getStringFromUrl(url));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getStringFromUrl(String url) {
        URL myURL;
        try {
            myURL = new URL(url);
            URLConnection conn = myURL.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);

            InputStream is = conn.getInputStream();
            return convertStreamToString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
