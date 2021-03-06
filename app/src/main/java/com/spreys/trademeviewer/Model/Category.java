package com.spreys.trademeviewer.Model;

import android.database.Cursor;

import com.spreys.trademeviewer.DataStorage.TradeMeContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidParameterException;
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
public class Category {
    private String name;
    private String number;
    private String path;
    private int сount;
    private boolean isRestrictead;
    private boolean hasLegalNotice;
    private boolean hasClassfieds;
    private String parentCategory;
    private int subcategoriesCount;
    private List<Category> subcategories;


    public static final String[] CATEGORY_COLUMNS = {
            TradeMeContract.CategoryEntry.TABLE_NAME + "." + TradeMeContract.CategoryEntry._ID,
            TradeMeContract.CategoryEntry.COLUMN_NAME,
            TradeMeContract.CategoryEntry.COLUMN_NUMBER,
            TradeMeContract.CategoryEntry.COLUMN_PARENT_ID,
            TradeMeContract.CategoryEntry.COLUMN_PATH,
            TradeMeContract.CategoryEntry.COLUMN_SUBCATEGORIES_COUNT,
    };

    public static final int COL_ID = 0;
    public static final int COL_NAME = 1;
    public static final int COL_NUMBER = 2;
    public static final int COL_PARENT_ID = 3;
    public static final int COL_PATH = 4;
    public static final int COL_SUBCATEGORIES_COUNT = 5;

    /**
     * Default constructor for the method.
     * @param json the JSON string returned from the TradeMe API. String can contain the
     *             subcategories, which will automatically be parsed as well.
     * @throws InvalidParameterException if json parameter is not in a valid format
     */
    public Category(String json) {
        initialiseCategoryFromJson(json);
    }

    public Category(String json, String parentCategory) {
        this.parentCategory = parentCategory;

        initialiseCategoryFromJson(json);
    }

    public Category(Cursor cursor) {
        this.name = cursor.getString(COL_NAME);
        this.number = cursor.getString(COL_NUMBER);
        this.parentCategory = cursor.getString(COL_PARENT_ID);
        this.path = cursor.getString(COL_PATH);
        this.subcategoriesCount = cursor.getInt(COL_SUBCATEGORIES_COUNT);
    }

    private void initialiseCategoryFromJson(String json) {
        //Convert String to JSONObject
        try {
            JSONObject jsonObject = new JSONObject(json);

            this.name = jsonObject.getString("Name");
            this.number = jsonObject.getString("Number");
            this.path = jsonObject.getString("Path");
            if(jsonObject.has("HasClassifieds")) {
                this.hasClassfieds = jsonObject.getBoolean("HasClassifieds");
            }

            if(jsonObject.has("Subcategories")) {
                JSONArray json_subcategories = jsonObject.getJSONArray("Subcategories");

                ArrayList<Category> subcategories = new ArrayList<>();

                for (int i = 0; i<json_subcategories.length(); i++) {
                    //Recursively create subcategories
                    subcategories.add(new Category(json_subcategories.getJSONObject(i).toString(), number));
                }

                this.subcategories = subcategories;
                this.subcategoriesCount = subcategories.size();
            }


        } catch (JSONException | NullPointerException exception) {
            throw new IllegalArgumentException("Unable to parse JSON");
        }
    }

    /**
     * The name of the category.
     * @return Returns null if object hasn't been initialised or a String with the name otherwise
     */
    public String getName(){
        return this.name;
    }

    /**
     * A unique identifier for the category e.g. “0004-0369-6076-“.
     * We plan to change this to a numeric identifier (e.g. “6076”) so you should ensure you can
     * cope with both formats.
     * @return Returns null if number hasn't been set yet and a String with the identifier otherwise
     */
    public String getNumber(){
        return this.number;
    }

    /**
     * The full URL path of this category e.g. “/Home-living/Beds-bedroom-furniture/Bedside-tables”.
     *
     * @return Returns null if object hasn't been initialised or a String with the full path to the
     * category otherwise.
     */
    public String getPath(){
        return this.path;
    }

    /**
     * The number of items for sale in this category.
     * @return Returns -1 if object hasn't been intialised or an integer with the amount of items
     * in this category.
     */
    public int count() {
        return -1;
    }

    /**
     * Indicates whether the category is restricted to adults only (i.e. the category is R18).
     * @return Returns false if the category is not restricted and true in any other case (i.e. if
     * category is restricted or the object hasn't been initialised).
     */
    public boolean isRestrictead() {
        return false;
    }

    /**
     * Indicates whether the category has legal requirements. You should ask the user to accept the
     * legal notice before listing in this category. There is an API to get the text of the legal
     * notice.
     *
     * @return Returns false if the category does not require legal notice and true in any other
     * case (i.e. if category is restricted or the object hasn't been initialised).
     */
    public boolean hasLegalNotice() {
        return false;
    }

    /**
     * Indicates whether classifieds are allowed in this category.
     * @return Returns false if classifields are not allowed and true in any other
     * case (i.e. if category is restricted or the object hasn't been initialised).
     */
    public boolean hasClassfieds() {
        return this.hasClassfieds;
    }

    /**
     * Returns the list of subcategories.
     * @return null if there are no subcategories and a List otherwise.
     */
    public List<Category> getSubcategories() {
        return this.subcategories;
    }

    /**
     * Method returns the name of the category
     * @return name of the category
     */
    public String toString() {
        return name;
    }

    public String getParentId() {
        return parentCategory;
    }

    public int getSubcategoriesCount() {
        return subcategoriesCount;
    }
}
