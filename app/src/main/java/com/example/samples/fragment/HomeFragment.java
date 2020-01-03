package com.example.samples.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.samples.model.Entry;
import com.example.samples.adapter.EntryAdapter;
import com.example.samples.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private ListView mListView;
    private List<Entry> mList = new ArrayList<>();
    private EntryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_home, container, false);
        mListView = view.findViewById(R.id.listview);
        mList.add(new Entry("支持标签的EditText", TagEditTextFragment.class));
        mList.add(new Entry("自定义小提示", TipFragment.class));
        mAdapter = new EntryAdapter(getActivity(), mList, R.layout.item_entry);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Entry entry = mAdapter.getItem(position);
                startFragment(entry.clazz);
            }
        });
        return view;
    }
}
