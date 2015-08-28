package com.spreys.trademeviewer.Adapt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spreys.trademeviewer.Model.Listing;
import com.spreys.trademeviewer.R;

import java.util.List;

/**
 * Created with Android Studio
 *
 * @author vspreys
 *         Date: 28/08/15
 *         Project: TradeMeViewer
 *         Contact by: vlad@spreys.com
 */
public class SearchResultAdapter extends ArrayAdapter<Listing> {
    private List<Listing> searchResults;

    public SearchResultAdapter(Context context, List<Listing> searchResults) {
        super(context, 0, searchResults);
        this.searchResults = searchResults;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listing_preview, parent, false);
        }
        Listing currentListing = searchResults.get(position);
        ((TextView)convertView.findViewById(R.id.item_listing_preview_title)).setText(currentListing.getTitle());
        Glide.with(getContext()).load(currentListing.getPictureHref())
                .placeholder(R.drawable.loading).into((ImageView)convertView.findViewById(R.id.item_listing_preview_thumbnails));

        return convertView;
    }

    @Override
    public int getCount() {
        return searchResults.size();
    }
}
