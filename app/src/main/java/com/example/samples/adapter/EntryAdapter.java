package com.example.samples.adapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.samples.model.Entry;

import java.util.List;

public class EntryAdapter extends BaseAdapter<Entry, EntryViewHolder> {
    public EntryAdapter(Context context, List<Entry> list, int layout) {
        super(context, list, layout);
    }

    @Override
    protected Class<EntryViewHolder> getViewHolderClass() {
        return EntryViewHolder.class;
    }

    @Override
    protected void bindData(@NonNull Entry model, @NonNull EntryViewHolder vh, int position) {
        vh.tvContent.setText(model.content);
    }
}
