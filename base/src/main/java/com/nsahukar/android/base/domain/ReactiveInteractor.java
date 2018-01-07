package com.nsahukar.android.base.domain;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 Interfaces for Interactors. This interfaces represent use cases
 (this means any use case in the application should implement this contract).
 */
public interface ReactiveInteractor {
    /**
     Retrieves changes from the data layer.
     It returns an Flowable that emits updates for the retrieved object.
     The returned Flowable will never complete, but it can error if there are any problems
     performing the required actions to serve the data.
     */
    interface RetrieveInteractor<Params, Object> extends ReactiveInteractor {

        @NonNull
        Flowable<Object> getBehaviorStream(@NonNull final Params params);
    }

    /**
     The request interactor is used to request some result once.
     The returned observable is a single, emits once and then completes or errors.
     */
    interface RequestInteractor<Params, Result> extends ReactiveInteractor {

        @NonNull
        Single<Result> getSingle(@NonNull final Params params);
    }

    /**
     The refresh interactor is used to refresh the reactive store with new data.
     Typically calling this interactor will trigger events in its get interactor counterpart.
     The returned observable will complete when the refresh is finished or error if there was any
     problem in the process.
     */
    interface RefreshInteractor<Params> extends ReactiveInteractor {

        @NonNull
        Completable getRefreshSingle(@NonNull final Params params);
    }

}

