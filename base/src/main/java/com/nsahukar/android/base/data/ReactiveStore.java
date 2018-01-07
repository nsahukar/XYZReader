package com.nsahukar.android.base.data;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;

/**
 Interface for any kind of reactive store.
 */
public interface ReactiveStore<Key, Value> {
    void storeSingular(@NonNull final Value model);

    void storeAll(@NonNull final List<Value> modelList);

    void replaceAll(@NonNull final List<Value> modelList);

    Flowable<Value> getSingular(@NonNull final Key key);

    Flowable<List<Value>> getAll();
}
