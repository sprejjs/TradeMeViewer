package com.spreys.trademeviewer.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.spreys.trademeviewer.Model.Category;
import com.spreys.trademeviewer.R;
import com.spreys.trademeviewer.Adapters.CategoryAdapter;
import com.spreys.trademeviewer.DateStorage.TradeMeContract;

/**
 * A list fragment representing a list of Categories. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link SearchResultsFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class CategoryListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String PARAM_CATEGORY_ID = "category_id";
    public static final String PARAM_PARENT_CATEGORY_NAME = "parent-category_name";
    public static final String PARAM_TWO_PANE = "two_pane";
    public static final String PARAM_SEARCH_QUERY = "search_query";
    private CategoryAdapter adapter;
    private static final int CATEGORY_LOADER = 0;
    private Callbacks mCallbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View listContent = super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        String parentCategoryName = getParentCategoryName();

        TextView textView = (TextView)view.findViewById(R.id.fragment_categories_parent_category_name);
        textView.setPaintFlags(textView.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        textView.setText(parentCategoryName);
        textView.setVisibility(parentCategoryName == null ? View.GONE : View.VISIBLE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        assert listContent != null;
        ((FrameLayout)view.findViewById(R.id.fragment_categories_list_container)).addView(listContent);

        return view;
    }

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri contentUri;
        if(getCategoryId() != null) {
            contentUri = TradeMeContract.CategoryEntry.buildCategoryUri(getCategoryId());
        } else {
            contentUri = TradeMeContract.CategoryEntry.CONTENT_URI;
        }
        return new CursorLoader(
                getContext(),
                contentUri,
                Category.CATEGORY_COLUMNS,
                null,
                null,
                null
        );
    }

    private String getCategoryId(){
        if(null != getArguments()){
            return getArguments().getString(PARAM_CATEGORY_ID);
        }
        return null;
    }

    private String getParentCategoryName() {
        if(null != getArguments()) {
            return getArguments().getString(PARAM_PARENT_CATEGORY_NAME);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        attachListViewAdapter();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    public void attachListViewAdapter(){
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        getLoaderManager().restartLoader(CATEGORY_LOADER, null, this);
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(Category selectedCategory);
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategoryListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CategoryAdapter(getContext(), null, 0);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }

        setActivateOnItemClick(getArguments().getBoolean(PARAM_TWO_PANE));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mCallbacks = (Callbacks)context;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(new Category(adapter.getCursor()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
}
