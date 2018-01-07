package com.nsahukar.android.base.presentation;

/**
 * Created by Nikhil on 12/12/17.
 */

public class State<T> {
    private final boolean mInProgress;
    private final Throwable mError;
    private final boolean mSuccess;
    private final T mDisplayableItem;

    private State(boolean inProgress, Throwable error, boolean success, T displayableItem) {
        mInProgress = inProgress;
        mError = error;
        mSuccess = success;
        mDisplayableItem = displayableItem;
    }

    public static <T> State<T> inProgress() {
        return new State<>(true, null, false, null);
    }

    public static <T> State<T> error(Throwable error) {
        return new State<>(false, error, false, null);
    }

    public static <T> State<T> success(T bundle) {
        return new State<>(false, null, true, bundle);
    }

    public boolean isInProgress() {
        return mInProgress;
    }

    public Throwable error() {
        return mError;
    }

    public boolean success() {
        return mSuccess;
    }

    public T displayableItem() {
        return mDisplayableItem;
    }
}