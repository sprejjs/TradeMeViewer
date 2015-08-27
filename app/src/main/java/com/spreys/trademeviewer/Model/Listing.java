package com.spreys.trademeviewer.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created with Android Studio
 *
 * @author vspreys
 *         Date: 27/08/15
 *         Project: TradeMe Viewer
 *         Contact by: vlad@spreys.com
 */
public class Listing {
    private Date asAtDate;
    private double buyNowPrice;
    private String category;
    private String categoryPath;
    private Date endDate;
    private boolean hasBuyNow;
    private boolean hasGallery;
    private boolean isBold;
    private boolean isFeatured;
    private boolean isNew;
    private int listingId;
    private Date noteDate;
    private String pictureHref;
    private String priceDisplay;
    private String region;
    private Date startDate;
    private Double startPrice;
    private String suburb;
    private String title;

    public Listing(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            if(jsonObject.has("AsAtDate")) {
                this.asAtDate = new Date(jsonObject.getString("AsAtDate").replaceAll("[^0-9]", ""));
            }
            if(jsonObject.has("BuyNowPrice")) {
                this.buyNowPrice = jsonObject.getDouble("BuyNowPrice");
            }
            if(jsonObject.has("Category")) {
                this.category = jsonObject.getString("Category");
            }
            if(jsonObject.has("CategoryPath")) {
                this.categoryPath = jsonObject.getString("CategoryPath");
            }
            if(jsonObject.has("EndDate")) {
                long milliseconds = Long.valueOf(jsonObject.getString("EndDate").replaceAll("[^0-9]", ""));
                Date date = new Date();
                date.setTime(milliseconds);
                this.endDate = date;
            }
            if(jsonObject.has("HasBuyNow")) {
                this.hasBuyNow = jsonObject.getBoolean("HasBuyNow");
            }
            if(jsonObject.has("HasGallery")) {
                this.hasGallery = jsonObject.getBoolean("HasGallery");
            }
            if(jsonObject.has("IsBold")) {
                this.isBold = jsonObject.getBoolean("IsBold");
            }
            if(jsonObject.has("IsFeatured")) {
                this.isFeatured = jsonObject.getBoolean("IsFeatured");
            }
            if(jsonObject.has("IsNew")) {
                this.isNew = jsonObject.getBoolean("IsNew");
            }
            if(jsonObject.has("ListingId")) {
                this.listingId = jsonObject.getInt("ListingId");
            }
            if(jsonObject.has("NoteDate")) {
                long milliseconds = Long.valueOf(jsonObject.getString("NoteDate").replaceAll("[^0-9]", ""));
                Date date = new Date();
                date.setTime(milliseconds);
                this.noteDate = date;
            }
            if(jsonObject.has("PictureHref")) {
                this.pictureHref = jsonObject.getString("PictureHref");
            }
            if(jsonObject.has("PriceDisplay")) {
                this.priceDisplay = jsonObject.getString("PriceDisplay");
            }
            if(jsonObject.has("Region")) {
                this.region = jsonObject.getString("Region");
            }
            if(jsonObject.has("StartDate")) {
                long milliseconds = Long.valueOf(jsonObject.getString("StartDate").replaceAll("[^0-9]", ""));
                Date date = new Date();
                date.setTime(milliseconds);
                this.startDate = date;
            }
            if(jsonObject.has("StartPrice")) {
                this.startPrice = jsonObject.getDouble("StartPrice");
            }
            if(jsonObject.has("Suburb")) {
                this.suburb = jsonObject.getString("Suburb");
            }
            if(jsonObject.has("Title")) {
                this.title = jsonObject.getString("Title");
            }
        } catch (JSONException | NullPointerException exception) {
            throw new IllegalArgumentException("Unable to parse json");
        }
    }

    public Date getAsAtDate() {
        return asAtDate;
    }

    public double getBuyNowPrice() {
        return buyNowPrice;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isHasBuyNow() {
        return hasBuyNow;
    }

    public void setHasBuyNow(boolean hasBuyNow) {
        this.hasBuyNow = hasBuyNow;
    }

    public boolean isHasGallery() {
        return hasGallery;
    }

    public boolean isBold() {
        return isBold;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public boolean isNew() {
        return isNew;
    }

    public int getListingId() {
        return listingId;
    }

    public Date getNoteDate() {
        return noteDate;
    }

    public String getPictureHref() {
        return pictureHref;
    }

    public String getPriceDisplay() {
        return priceDisplay;
    }

    public String getRegion() {
        return region;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getTitle() {
        return title;
    }
}
