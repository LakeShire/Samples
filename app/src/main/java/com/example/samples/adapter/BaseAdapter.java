package com.example.samples.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

abstract public class BaseAdapter<T, V extends ViewHolder> extends android.widget.BaseAdapter {

    private final Context mContext;
    private final List<T> mList;
    private final int mLayoutId;

    public BaseAdapter(Context context, List<T> list, int layout) {
        super();
        mContext = context;
        mList = list;
        mLayoutId = layout;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public T getItem(int position) {
        if (mList == null) {
            return null;
        }
        if (position >= 0 && position < mList.size()) {
            return mList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
            Class<V> clazz = getViewHolderClass();
            V obj = null;
            if (clazz != null) {
                try {
                    Constructor<V> constructor = clazz.getConstructor(View.class);
                    obj = constructor.newInstance(convertView);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            convertView.setTag(obj);
        }
        Object obj = convertView.getTag();
        if (obj instanceof ViewHolder) {
            V vh = (V) obj;
            T model = mList.get(position);
            if (model != null) {
                bindData(model, vh, position);
            }
        }
        return convertView;
    }

    abstract protected Class<V> getViewHolderClass();

    abstract protected void bindData(@NonNull T model, @NonNull V vh, int position);
}
