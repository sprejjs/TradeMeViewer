package com.spreys.trademeviewer.Model;

import java.security.InvalidParameterException;
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
    private boolean hasClassfields;
    private List<Category> subcategories;

    /**
     * Default constructor for the method.
     * @param json the JSON string returned from the TradeMe API. String can contain the
     *             subcategories, which will automatically be parsed as well.
     * @throws InvalidParameterException if json parameter is not in a valid format
     */
    public Category(String json) {
        throw new InvalidParameterException();
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
    public boolean hasClassifields() {
        return false;
    }

    /**
     * Returns the list of subcategories.
     * @return null if there are no subcategories and a List otherwise.
     */
    public List<Category> getSubcategories() {
        return null;
    }
}
