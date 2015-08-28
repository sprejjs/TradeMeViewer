package com.spreys.trademeviewer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.spreys.trademeviewer.Fragments.SearchResultsFragment;
import com.spreys.trademeviewer.Fragments.CategoryListFragment;
import com.spreys.trademeviewer.Model.Category;
import com.spreys.trademeviewer.R;
import com.spreys.trademeviewer.Sync.TradeMeSyncAdapter;


/**
 * An activity representing a list of Categories. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link SearchResultsActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link CategoryListFragment} and the item details
 * (if present) is a {@link SearchResultsFragment}.
 * <p/>
 * This activity also implements the required
 * {@link CategoryListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class CategoryListActivity extends AppCompatActivity
        implements CategoryListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);


        if (findViewById(R.id.category_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
            Bundle arguments = new Bundle();

            if(getIntent().hasExtra(CategoryListFragment.PARAM_PARENT_CATEGORY_NAME)) {
                arguments.putString(SearchResultsFragment.PARAM_KEY_CATEGORY_NAME,
                        getIntent().getStringExtra(CategoryListFragment.PARAM_PARENT_CATEGORY_NAME));
            }

            if(getIntent().hasExtra(CategoryListFragment.PARAM_SEARCH_QUERY)) {
                arguments.putString(SearchResultsFragment.PARAM_KEY_SEARCH_QUERY,
                        getIntent().getStringExtra(CategoryListFragment.PARAM_SEARCH_QUERY));
            }

            searchResultsFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.category_detail_container, searchResultsFragment)
                    .commit();
        }

        //Initialise fragment
        CategoryListFragment fragment = new CategoryListFragment();
        Bundle arguments = new Bundle();

        //Add category id if exists
        if(getIntent().hasExtra(CategoryListFragment.PARAM_CATEGORY_ID)) {

            arguments.putString(CategoryListFragment.PARAM_CATEGORY_ID,
                    getIntent().getStringExtra(CategoryListFragment.PARAM_CATEGORY_ID));
        }

        if (getIntent().hasExtra(CategoryListFragment.PARAM_PARENT_CATEGORY_NAME)) {
            arguments.putString(CategoryListFragment.PARAM_PARENT_CATEGORY_NAME,
                    getIntent().getStringExtra(CategoryListFragment.PARAM_PARENT_CATEGORY_NAME));
        }

        arguments.putBoolean(CategoryListFragment.PARAM_TWO_PANE, mTwoPane);
        fragment.setArguments(arguments);

        //Initiate transition
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.categories_list_container, fragment)
                .commit();

        TradeMeSyncAdapter.initializeSyncAdapter(this);
    }

    /**
     * Callback method from {@link CategoryListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(Category selectedCategory) {
        Intent intent = new Intent(this, CategoryListActivity.class);
        intent.putExtra(CategoryListFragment.PARAM_PARENT_CATEGORY_NAME, selectedCategory.getName());
        intent.putExtra(CategoryListFragment.PARAM_CATEGORY_ID, selectedCategory.getNumber());
        startActivity(intent);
    }
}