package com.nsahukar.android.base.presentation.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

import com.nsahukar.android.base.presentation.DisplayableItem;

/**
 * Created by Nikhil on 08/12/17.
 */

public interface ViewHolderBinder {
    // Populates the passed ViewHolder with the details of the passed DisplayableItem
    void bind(@NonNull final ViewHolder viewHolder, @NonNull final DisplayableItem item);
}
