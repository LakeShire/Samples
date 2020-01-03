package com.example.samples.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.samples.R;

public class EntryViewHolder extends ViewHolder {

    public final TextView tvContent;

    public EntryViewHolder(View view) {
        super(view);
        tvContent = view.findViewById(R.id.tv_content);
    }
}
