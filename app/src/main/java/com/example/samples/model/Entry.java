package com.example.samples.model;

import com.example.samples.fragment.BaseFragment;

public class Entry {
    public String content;
    public Class<? extends BaseFragment> clazz;

    public Entry(String content, Class<? extends BaseFragment> clazz) {
        this.content = content;
        this.clazz = clazz;
    }
}
