package com.nsahukar.android.base.common.preconditions;

import android.os.Looper;

import javax.inject.Inject;

/**
 * Created by Nikhil on 14/12/17.
 */

public class AndroidPreconditions {
    @Inject
    AndroidPreconditions() {}

    /**
     *  Assert that the current thread is a worker thread
     */
    public void assertWorkerThread() {
        if (isMainThread()) {
            throw new IllegalStateException(
                    "This task must be run on a worker thread and not on the Main thread.");
        }
    }

    /**
     *  Assert that the current thread is the main thread
     */
    public void assertUIThread() {
        if (!isMainThread()) {
            throw new IllegalStateException(
                    "This task must be run on a worker thread and not on the Main thread.");
        }
    }

    /**
     *  Returns whether the current thread is the Andoroid Main Thread
     */
    public boolean isMainThread() {
        return Looper.getMainLooper().getThread().equals(Thread.currentThread());
    }
}
