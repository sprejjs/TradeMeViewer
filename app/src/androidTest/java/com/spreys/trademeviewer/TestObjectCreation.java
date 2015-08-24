package com.spreys.trademeviewer;

import android.test.AndroidTestCase;

import com.spreys.trademeviewer.Model.Category;

import junit.framework.Assert;

/**
 * Created with Android Studio
 *
 * @author vspreys
 *         Date: 24/08/15
 *         Project: TradeMe Viewer
 *         Contact by: vlad@spreys.com
 */
public class TestObjectCreation extends AndroidTestCase{

    public void testCreateSingleCategory_Success() {
        String jsonString = "{\"Name\":\"Aircraft\",\"Number\":\"0001-1484-8698-\",\"Path\":\"\\/Trade-Me-Motors\\/Aircraft\\/Aircraft\",\"HasClassifieds\":true}";

        Category category = new Category(jsonString);
        assertEquals("Aircraft", category.getName());
        assertEquals("0001-1484-8698-", category.getNumber());
        assertEquals("\\/Trade-Me-Motors\\/Aircraft\\/Aircraft\\", category.getPath());
        assertEquals(true, category.hasClassifields());

        //Shouldn't have any subcategories
        assertEquals(null, category.getSubcategories());
    }

    public void testCreateSingleCategory_InvalidJson() {
        String jsonString = "Test string";

        try {
            Category category = new Category(jsonString);
        } catch (IllegalArgumentException exception) {
            //This is the expected outcome
        } catch (Exception exception) {
            Assert.fail("Incorrect type of exception received");
        }
    }

    public void testCreateSingleCategory_Null() {
        String jsonString = null;

        try {
            Category category = new Category(jsonString);
        } catch (IllegalArgumentException exception) {
            //This is the expected outcome
        } catch (Exception exception) {
            Assert.fail("Incorrect type of exception received");
        }
    }

    public void testCreateCategoryWithSubcategories1Level_Success() {
        String jsonString = "{ \"HasClassifieds\": true, \"Name\": \"Dinghies & rowboats\", \"Number\": \"0001-0348-1261-\", \"Path\": \"/Trade-Me-Motors/Boats-marine/Dinghies-rowboats\", \"Subcategories\": [ { \"HasClassifieds\": true, \"Name\": \"Aluminium\", \"Number\": \"0001-0348-1261-1287-\", \"Path\": \"/Trade-Me-Motors/Boats-marine/Dinghies-rowboats/Aluminium\"}, { \"HasClassifieds\": true, \"Name\": \"Fibreglass\", \"Number\": \"0001-0348-1261-2971-\", \"Path\": \"/Trade-Me-Motors/Boats-marine/Dinghies-rowboats/Fibreglass\"}, { \"HasClassifieds\": true, \"Name\": \"Inflatable\", \"Number\": \"0001-0348-1261-1288-\", \"Path\": \"/Trade-Me-Motors/Boats-marine/Dinghies-rowboats/Inflatable\"}, { \"HasClassifieds\": true, \"Name\": \"Wood\", \"Number\": \"0001-0348-1261-1289-\", \"Path\": \"/Trade-Me-Motors/Boats-marine/Dinghies-rowboats/Wood\"} ]}";

        Category category = new Category(jsonString);
        assertEquals(true, category.hasClassifields());
        assertEquals("Dinghies & rowboats", category.getName());
        assertEquals("0001-0348-1261-", category.getNumber());
        assertEquals("/Trade-Me-Motors/Boats-marine/Dinghies-rowboats", category.getPath());
        //Should have 4 subcategories
        assertEquals(4, category.getSubcategories().size());

        //Make sure that the first subcategory is parsed correctly
        Category subcategory = category.getSubcategories().get(0);
        assertEquals(true, subcategory.hasClassifields());
        assertEquals("Aluminium", subcategory.getName());
        assertEquals("0001-0348-1261-1287-", subcategory.getNumber());
        assertEquals("/Trade-Me-Motors/Boats-marine/Dinghies-rowboats/Aluminium", subcategory.getPath());
    }

    public void testCreateCategoryWithSubcategories2Levels_Success() {
        String jsonString = "{\"HasClassifieds\": true, \"Name\": \"Trade Me Motors\", \"Number\": \"0001-\", \"Path\": \"/Trade-Me-Motors\", \"Subcategories\": [{\"HasClassifieds\": true, \"Name\": \"Aircraft\", \"Number\": \"0001-1484-\", \"Path\": \"/Trade-Me-Motors/Aircraft\", \"Subcategories\": [{\"HasClassifieds\": true, \"Name\": \"Aircraft\", \"Number\": \"0001-1484-8698-\", \"Path\": \"/Trade-Me-Motors/Aircraft/Aircraft\"},{\"HasClassifieds\": true, \"Name\": \"Parts\", \"Number\": \"0001-1484-8699-\", \"Path\": \"/Trade-Me-Motors/Aircraft/Parts\"},{\"HasClassifieds\": true, \"Name\": \"Other\", \"Number\": \"0001-1484-8700-\", \"Path\": \"/Trade-Me-Motors/Aircraft/Other\"}]},]}";

        Category category = new Category(jsonString);
        assertEquals(true, category.hasClassifields());
        assertEquals("Trade Me Motors", category.getName());
        assertEquals("0001-", category.getNumber());
        assertEquals("/Trade-Me-Motors", category.getPath());

        //Should have 1 subcategory
        assertEquals(1, category.getSubcategories().size());

        //Check the subcategory
        Category subcategory = category.getSubcategories().get(0);
        assertEquals(true, subcategory.hasClassifields());
        assertEquals("Aircraft", subcategory.getName());
        assertEquals("0001-1484-", subcategory.getNumber());
        assertEquals("/Trade-Me-Motors/Aircraft", subcategory.getPath());

        //Should have 3 subcategory
        assertEquals(3, category.getSubcategories().size());

        //Check second level subcategory
        subcategory = subcategory.getSubcategories().get(0);
        assertEquals(true, subcategory.hasClassifields());
        assertEquals("Aircraft", subcategory.getName());
        assertEquals("0001-1484-8698-", subcategory.getNumber());
        assertEquals("/Trade-Me-Motors/Aircraft/Aircraft", subcategory.getPath());
    }
}
