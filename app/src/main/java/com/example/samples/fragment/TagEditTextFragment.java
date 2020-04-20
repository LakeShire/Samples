package com.example.samples.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.samples.R;
import com.example.samples.view.RichEditText;
import com.example.samples.view.TagSpan;

public class TagEditTextFragment extends BaseFragment {

    private RichEditText mEditText;
    private View mButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_tag, container, false);
        mEditText = view.findViewById(R.id.tv);
        mButton = view.findViewById(R.id.btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable editable = mEditText.getText();
                editable.append("    ");
                int oriLen = editable.length();
                String toAdd = "Hello World Hello";
                editable.append(toAdd);
                TagSpan span = new TagSpan(Color.RED, Color.WHITE, 0, 48);
                span.setPaddingLR(8);
                editable.setSpan(span, oriLen, oriLen + toAdd.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        });

        SpannableString ss = new SpannableString("Hello World Hello World Hello World");
        TagSpan span1 = new TagSpan(Color.RED, Color.WHITE, 0, 48);
        span1.setPaddingLR(8);
        TagSpan span2 = new TagSpan(Color.RED, Color.WHITE, 0, 48);
        span2.setPaddingLR(8);
        ss.setSpan(span1, 12, 17, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ss.setSpan(span2, 24, 29, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mEditText.setText(ss);
        return view;
    }
}
