package com.nsahukar.android.base.presentation.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.ViewGroup;

import com.nsahukar.android.base.common.preconditions.AndroidPreconditions;
import com.nsahukar.android.base.presentation.DisplayableItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

/**
 * Created by Nikhil on 14/12/17.
 */

public class RecyclerViewAdapter extends Adapter {
    @NonNull
    private final List<DisplayableItem> mModelItems = new ArrayList<>();

    @NonNull
    private final Map<Integer, ViewHolderFactory> mFactoryMap;

    @NonNull
    private final Map<Integer, ViewHolderBinder> mBinderMap;

    @NonNull
    private final AndroidPreconditions mAndroidPreconditions;

    public RecyclerViewAdapter(@NonNull final Map<Integer, ViewHolderFactory> factoryMap,
                               @NonNull final Map<Integer, ViewHolderBinder> binderMap,
                               @NonNull final AndroidPreconditions androidPreconditions) {
        mFactoryMap = factoryMap;
        mBinderMap = binderMap;
        mAndroidPreconditions = androidPreconditions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mFactoryMap.get(viewType).createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DisplayableItem item = mModelItems.get(position);
        mBinderMap.get(item.type()).bind(holder, item);
    }

    @Override
    public int getItemCount() {
        return mModelItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mModelItems.get(position).type();
    }

    /**
     *  Update mModelItems currently stored in adapter with the new mModelItems.
     */
    public void update(@NonNull final List<DisplayableItem> items) {
        mAndroidPreconditions.assertUIThread();
        if (mModelItems.isEmpty()) {
            updateAllItems(items);
        }
    }

    /**
     *  Only use for first update of the adapter, when it is still empty
     */
    private void updateAllItems(@NonNull List<DisplayableItem> items) {
        Single.just(items)
                .doOnSuccess(this::updateItemsInModel)
                .subscribe(__ -> notifyDataSetChanged());
    }

    private void updateItemsInModel(@NonNull List<DisplayableItem> items) {
        mModelItems.clear();
        mModelItems.addAll(items);
    }
}
