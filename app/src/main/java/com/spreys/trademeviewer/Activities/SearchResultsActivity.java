package com.spreys.trademeviewer.Activities;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.spreys.trademeviewer.Fragments.CategoryListFragment;
import com.spreys.trademeviewer.Fragments.SearchResultsFragment;
import com.spreys.trademeviewer.R;

/**
 * An activity representing a single Category detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link CategoryListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link SearchResultsFragment}.
 */
public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
        Bundle arguments = new Bundle();

        arguments.putString(SearchResultsFragment.PARAM_KEY_CATEGORY_ID,
                getIntent().getStringExtra(SearchResultsFragment.PARAM_KEY_CATEGORY_ID));

        if(getIntent().hasExtra(CategoryListFragment.PARAM_SEARCH_QUERY)) {
            arguments.putString(SearchResultsFragment.PARAM_KEY_SEARCH_QUERY,
                    getIntent().getStringExtra(CategoryListFragment.PARAM_SEARCH_QUERY));
        }

        setTitle(getIntent().getStringExtra(SearchResultsFragment.PARAM_KEY_CATEGORY_NAME));

        searchResultsFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.category_detail_container, searchResultsFragment)
                .commit();
    }
}
