package com.spreys.trademeviewer.Adapt;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.spreys.trademeviewer.Model.Category;

/**
 * Created with Android Studio
 *
 * @author vspreys
 *         Date: 26/08/15
 *         Project: TradeMe Viewer
 *         Contact by: vlad@spreys.com
 */
public class CategoryAdapter extends CursorAdapter {

    public CategoryAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                android.R.layout.simple_list_item_activated_1,
                parent,
                false
        );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Category category = new Category(cursor);
        ((TextView)view.findViewById(android.R.id.text1)).setText(category.getName());
    }
}
