package com.example.samples.model;

import androidx.fragment.app.Fragment;

public class Entry {
    public String content;
    public Class<? extends Fragment> clazz;

    public Entry(String content, Class<? extends Fragment> clazz) {
        this.content = content;
        this.clazz = clazz;
    }
}
