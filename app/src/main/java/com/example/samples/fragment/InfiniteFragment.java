package com.example.samples.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samples.R;
import com.example.samples.adapter.EntryAdapter;
import com.example.samples.adapter.NoteAdapter;
import com.example.samples.fragment.animation.AnimatorFragment;
import com.example.samples.fragment.canvas.LeafLoadingFragment;
import com.example.samples.model.Entry;
import com.example.samples.model.Note;

import java.util.ArrayList;
import java.util.List;

public class InfiniteFragment extends BaseFragment {

    private WebView mWebView1;
    private WebView mWebView2;
    private ListView mListView;
    private RecyclerView mRecyclerView;
    private List<Entry> mList = new ArrayList<>();
    private List<Note> mNoteList = new ArrayList<>();
    private EntryAdapter mAdapter;
    private NoteAdapter mNoteAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_infinite, container, false);
        mWebView1 = view.findViewById(R.id.web_view_1);
        mWebView2 = view.findViewById(R.id.web_view_2);
        mListView = view.findViewById(R.id.list_view);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mWebView1.loadUrl("http://m.2345.com");
        mWebView1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView2.loadUrl("http://m.2345.com");
        mWebView2.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        for (int i = 0; i < 20; i++) {
            mList.add(new Entry("列表项 " + (i + 1), null));
        }
        mAdapter = new EntryAdapter(getActivity(), mList, R.layout.item_entry);
        mListView.setAdapter(mAdapter);

        for (int i = 0; i < 20; i++) {
            mNoteList.add(new Note("笔记 " + (i + 1)));
        }
        mNoteAdapter = new NoteAdapter(getActivity(), mNoteList);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mNoteAdapter);

        return view;
    }
}
