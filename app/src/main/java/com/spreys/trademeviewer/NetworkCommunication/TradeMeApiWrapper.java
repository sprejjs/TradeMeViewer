package com.spreys.trademeviewer.NetworkCommunication;

import com.spreys.trademeviewer.Model.Category;
import com.spreys.trademeviewer.Model.Listing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
    private final static String BASE_API_URL_PROD = "https://api.trademe.co.nz/v1/";
    private final static String BASE_API_URL_SANDBOX = "https://api.tmsandbox.co.nz/v1/";
    private final static String API_URL_CATEGORIES = "categories";
    private final static String API_URL_SEARCH = "search/general";
    private final static String API_FORMAT_JSON = ".json";
    private final static String API_FORMAT_XML = ".xml";

    private final static String PARAM_OAUTH_KEY_CONSUMER_KEY = "oauth_consumer_key";
    private final static String PARAM_OAUTH_KEY_SIGNATURE_METHOD = "oauth_signature_method";
    private final static String PARAM_OAUTH_KEY_SIGNATURE = "oauth_signature";

    private final static String PARAM_OAUTH_VALUE_CONSUMER_KEY = "BE1FF798DC8AEEFF27E757C528A044D6";
    private final static String PARAM_OAUTH_VALUE_SIGNATURE_METHOD = "PLAINTEXT";
    private final static String PARAM_OAUTH_VALUE_SIGNATURE = "2E3410643AAF5A3F2CDC81F93A389D2C%26";

    private final static String PARAM_KEY_CATEGORY = "category";
    private final static String PARAM_KEY_SEARCH_STRING = "search_string";

    private final static boolean useSandbox = true;

    private boolean useJson = true;

    /**
     * Returns the currently selected API format. Can be modified by setting setApiFormat() method.
     * Defaults to JSON.
     * @return Extension to the API format as string. Either ".json" or ".xml"
     */
    private String apiFormat() {
        return useJson ? API_FORMAT_JSON : API_FORMAT_XML;
    }

    /**
     * Return the base application url based on the useSandbox variable.
     * @return Sandbox url if useSandbox variable is set to true and the product url otherwise
     */
    private String getBaseUrl() {
        return useSandbox ? BASE_API_URL_SANDBOX : BASE_API_URL_PROD;
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
        String url = getBaseUrl() + API_URL_CATEGORIES + apiFormat();
        return new Category(getStringFromUrl(url)).getSubcategories();
    }

    /**
     * Returns the list of categories for the supplied category id
     * @param categoryId Has to be a valid TradeMe category id
     * @return list of subcategories for the supplied category id
     */
    public List<Category> getCategories(String categoryId){
        String url = getBaseUrl() + API_URL_CATEGORIES + "/" + categoryId + apiFormat();
        return new Category(getStringFromUrl(url)).getSubcategories();
    }

    public List<Listing> searchListings(String category, String searchQuery) {
        String categoryParam = "";
        String searchQueryParam = "";

        if(category != null && !category.equals("")) {
            categoryParam = PARAM_KEY_CATEGORY + "=" + category + "&";
        }

        if(searchQuery != null && !searchQuery.equals("")) {
            searchQueryParam = PARAM_KEY_SEARCH_STRING + "=" + searchQuery + "&";
        }

        String url = getBaseUrl() + API_URL_SEARCH + apiFormat() + "?" + categoryParam + searchQueryParam + oauthParameters();

        JSONObject jsonObject = getJsonObjectFromUrl(url);
        JSONArray listArray = null;
        try {
            listArray = jsonObject.getJSONArray("List");
            ArrayList<Listing> listings = new ArrayList<>();

            for(int i = 0 ; i < listArray.length(); i++) {
                listings.add(new Listing(listArray.getString(i)));
            }

            return listings;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Listing> searchListings(String category) {
        return searchListings(category, null);
    }

    private String oauthParameters() {
        return String.format("%s=%s&%s=%s&%s=%s",
                PARAM_OAUTH_KEY_CONSUMER_KEY, PARAM_OAUTH_VALUE_CONSUMER_KEY,
                PARAM_OAUTH_KEY_SIGNATURE_METHOD, PARAM_OAUTH_VALUE_SIGNATURE_METHOD,
                PARAM_OAUTH_KEY_SIGNATURE, PARAM_OAUTH_VALUE_SIGNATURE);
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
