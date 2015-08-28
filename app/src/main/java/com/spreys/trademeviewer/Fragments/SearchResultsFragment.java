package com.spreys.trademeviewer.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.spreys.trademeviewer.Activities.SearchResultsActivity;
import com.spreys.trademeviewer.Activities.CategoryListActivity;
import com.spreys.trademeviewer.Adapt.SearchResultAdapter;
import com.spreys.trademeviewer.Model.Listing;
import com.spreys.trademeviewer.NetworkCommunication.TradeMeApiWrapper;
import com.spreys.trademeviewer.R;

import java.util.List;

/**
 * A fragment representing a single Category detail screen.
 * This fragment is either contained in a {@link CategoryListActivity}
 * in two-pane mode (on tablets) or a {@link SearchResultsActivity}
 * on handsets.
 */
public class SearchResultsFragment extends Fragment {

    public static final String PARAM_KEY_CATEGORY_ID = "category_id";
    public static final String PARAM_KEY_SEARCH_QUERY = "search_query";

    private TradeMeApiWrapper mApiWrapper;

    /**
     * The dummy content this fragment is presenting.
     */

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchResultsFragment() {
        mApiWrapper = new TradeMeApiWrapper();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);

        // Show the dummy content as text in a TextView.
//        if (mItem != null) {
////            ((TextView) rootView.findViewById(R.id.category_detail)).setText(mItem.content);
//        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        new SearchApiTask().execute();
    }

    private String getCategory() {
        if(null != getArguments()){
            return getArguments().getString(PARAM_KEY_CATEGORY_ID);
        }
        return null;
    }

    private String getSearchQuery() {
        if(null != getArguments()) {
            return getArguments().getString(PARAM_KEY_SEARCH_QUERY);
        }
        return null;
    }

    class SearchApiTask extends AsyncTask<Void, Void, List<Listing>> {

        @Override
        protected List<Listing> doInBackground(Void... voids) {
            return mApiWrapper.searchListings(getCategory(), getSearchQuery());
        }

        @Override
        protected void onPostExecute(final List<Listing> searchResults) {
            SearchResultAdapter searchResultAdapter = new SearchResultAdapter(getActivity(), searchResults);
            ((GridView)getActivity().findViewById(R.id.fragment_search_results_grid_view))
                    .setAdapter(searchResultAdapter);
        }
    }
}
