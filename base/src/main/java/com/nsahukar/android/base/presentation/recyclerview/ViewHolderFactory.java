package com.nsahukar.android.base.presentation.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

/**
 * Created by Nikhil on 08/12/17.
 */

public abstract class ViewHolderFactory {
    @NonNull
    protected final Context context;

    protected ViewHolderFactory(@NonNull final Context context) {
        this.context = context;
    }

    @NonNull
    public abstract ViewHolder createViewHolder(@NonNull final ViewGroup parent);
}
