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

    /**
     * ====
     * Category
     * ====
     */
    public void testCreateSingleCategory_Success() {
        String jsonString = "{\"Name\":\"Aircraft\",\"Number\":\"0001-1484-8698-\",\"Path\":\"\\/Trade-Me-Motors\\/Aircraft\\/Aircraft\",\"HasClassifieds\":true}";

        Category category = new Category(jsonString);
        assertEquals("Aircraft", category.getName());
        assertEquals("0001-1484-8698-", category.getNumber());
        assertEquals("/Trade-Me-Motors/Aircraft/Aircraft", category.getPath());
        assertEquals(true, category.hasClassfieds());

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
        assertEquals(true, category.hasClassfieds());
        assertEquals("Dinghies & rowboats", category.getName());
        assertEquals("0001-0348-1261-", category.getNumber());
        assertEquals("/Trade-Me-Motors/Boats-marine/Dinghies-rowboats", category.getPath());
        //Should have 4 subcategories
        assertEquals(4, category.getSubcategories().size());

        //Make sure that the first subcategory is parsed correctly
        Category subcategory = category.getSubcategories().get(0);
        assertEquals(true, subcategory.hasClassfieds());
        assertEquals("Aluminium", subcategory.getName());
        assertEquals("0001-0348-1261-1287-", subcategory.getNumber());
        assertEquals("/Trade-Me-Motors/Boats-marine/Dinghies-rowboats/Aluminium", subcategory.getPath());
    }

    public void testCreateCategoryWithSubcategories2Levels_Success() {
        String jsonString = "{\"Name\":\"Caravans & motorhomes\",\"Number\":\"0001-0028-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\",\"Subcategories\":[{\"Name\":\"Caravans\",\"Number\":\"0001-0028-2902-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Caravans\",\"Subcategories\":[{\"Name\":\"Up to 12 ft\",\"Number\":\"0001-0028-2902-6971-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Caravans\\/Up-to-12-ft\",\"HasClassifieds\":true},{\"Name\":\"13 - 16 ft\",\"Number\":\"0001-0028-2902-6972-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Caravans\\/13-16-ft\",\"HasClassifieds\":true},{\"Name\":\"17 - 20 ft\",\"Number\":\"0001-0028-2902-6973-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Caravans\\/17-20-ft\",\"HasClassifieds\":true},{\"Name\":\"21 - 24 ft\",\"Number\":\"0001-0028-2902-8707-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Caravans\\/21-24-ft\",\"HasClassifieds\":true},{\"Name\":\"More than 24 ft\",\"Number\":\"0001-0028-2902-6974-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Caravans\\/More-than-24-ft\",\"HasClassifieds\":true}],\"HasClassifieds\":true},{\"Name\":\"Motorhomes\",\"Number\":\"0001-0028-2983-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Motorhomes\",\"Subcategories\":[{\"Name\":\"Up to 5 metres\",\"Number\":\"0001-0028-2983-7274-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Motorhomes\\/Up-to-5-metres\",\"HasClassifieds\":true},{\"Name\":\"5 - 5.9 metres\",\"Number\":\"0001-0028-2983-7275-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Motorhomes\\/5-59-metres\",\"HasClassifieds\":true},{\"Name\":\"6 - 6.9 metres\",\"Number\":\"0001-0028-2983-8708-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Motorhomes\\/6-69-metres\",\"HasClassifieds\":true},{\"Name\":\"7 - 7.9 metres\",\"Number\":\"0001-0028-2983-7277-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Motorhomes\\/7-79-metres\",\"HasClassifieds\":true},{\"Name\":\"8 - 8.9 metres\",\"Number\":\"0001-0028-2983-8709-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Motorhomes\\/8-89-metres\",\"HasClassifieds\":true},{\"Name\":\"9 metres and over\",\"Number\":\"0001-0028-2983-7276-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Motorhomes\\/9-metres-and-over\",\"HasClassifieds\":true}],\"HasClassifieds\":true},{\"Name\":\"Parts & accessories\",\"Number\":\"0001-0028-2903-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Parts-accessories\",\"Subcategories\":[{\"Name\":\"Awnings & covers\",\"Number\":\"0001-0028-2903-6985-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Parts-accessories\\/Awnings-covers\"},{\"Name\":\"Batteries & inverters\",\"Number\":\"0001-0028-2903-7273-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Parts-accessories\\/Batteries-inverters\"},{\"Name\":\"Cookers & hobs\",\"Number\":\"0001-0028-2903-8712-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Parts-accessories\\/Cookers-hobs\"},{\"Name\":\"Fridges\",\"Number\":\"0001-0028-2903-8713-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Parts-accessories\\/Fridges\"},{\"Name\":\"Furniture\",\"Number\":\"0001-0028-2903-6984-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Parts-accessories\\/Furniture\"},{\"Name\":\"Lighting\",\"Number\":\"0001-0028-2903-6980-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Parts-accessories\\/Lighting\"},{\"Name\":\"Power leads\",\"Number\":\"0001-0028-2903-8714-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Parts-accessories\\/Power-leads\"},{\"Name\":\"Satellites & aerials\",\"Number\":\"0001-0028-2903-8715-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Parts-accessories\\/Satellites-aerials\"},{\"Name\":\"Sinks, pumps & plumbing\",\"Number\":\"0001-0028-2903-6983-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Parts-accessories\\/Sinks-pumps-plumbing\"},{\"Name\":\"Solar panels & accessories\",\"Number\":\"0001-0028-2903-8711-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Parts-accessories\\/Solar-panels-accessories\"},{\"Name\":\"Toilets & showers\",\"Number\":\"0001-0028-2903-6982-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Parts-accessories\\/Toilets-showers\"},{\"Name\":\"Windows\",\"Number\":\"0001-0028-2903-8710-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Parts-accessories\\/Windows\"},{\"Name\":\"Other\",\"Number\":\"0001-0028-2903-6986-\",\"Path\":\"\\/Trade-Me-Motors\\/Caravans-motorhomes\\/Parts-accessories\\/Other\"}]}],\"HasClassifieds\":true}";

        Category category = new Category(jsonString);
        assertEquals(true, category.hasClassfieds());
        assertEquals("Caravans & motorhomes", category.getName());
        assertEquals("0001-0028-", category.getNumber());
        assertEquals("/Trade-Me-Motors/Caravans-motorhomes", category.getPath());

        //Should have 3 subcategories
        assertEquals(3, category.getSubcategories().size());

        //Check the subcategory
        Category subcategory = category.getSubcategories().get(0);
        assertEquals(true, subcategory.hasClassfieds());
        assertEquals("Caravans", subcategory.getName());
        assertEquals("0001-0028-2902-", subcategory.getNumber());
        assertEquals("/Trade-Me-Motors/Caravans-motorhomes/Caravans", subcategory.getPath());

        //Should have 5 subcategories
        assertEquals(5, subcategory.getSubcategories().size());

        //Check second level subcategory
        subcategory = subcategory.getSubcategories().get(0);
        assertEquals(true, subcategory.hasClassfieds());
        assertEquals("Up to 12 ft", subcategory.getName());
        assertEquals("0001-0028-2902-6971-", subcategory.getNumber());
        assertEquals("/Trade-Me-Motors/Caravans-motorhomes/Caravans/Up-to-12-ft", subcategory.getPath());
    }
}
