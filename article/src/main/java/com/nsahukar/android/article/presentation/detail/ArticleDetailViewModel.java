package com.nsahukar.android.article.presentation.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nsahukar.android.article.domain.RetrieveArticleDetail;
import com.nsahukar.android.base.presentation.DisplayableItem;
import com.nsahukar.android.base.presentation.State;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Nikhil on 08/12/17.
 */

public class ArticleDetailViewModel extends ViewModel {
    private static final String TAG = ArticleDetailViewModel.class.getSimpleName();

    @NonNull
    private final RetrieveArticleDetail mRetrieveArticleDetail;

    @NonNull
    private final ArticleDetailDisplayableItemMapper mDisplayableItemMapper;

    @NonNull
    private final MutableLiveData<State<DisplayableItem>> mArticleLiveData = new MutableLiveData<>();

    @NonNull
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Inject
    ArticleDetailViewModel(@NonNull final RetrieveArticleDetail retrieveArticleDetail,
                           @NonNull final ArticleDetailDisplayableItemMapper displayableItemMapper) {
        mRetrieveArticleDetail = retrieveArticleDetail;
        mDisplayableItemMapper = displayableItemMapper;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
    }

    @NonNull
    public LiveData<State<DisplayableItem>> getArticleLiveData(long articleId) {
        // Bind view model
        mCompositeDisposable.add(bindToArticle(articleId));
        return mArticleLiveData;
    }

    @NonNull
    private Disposable bindToArticle(long articleId) {
        return mRetrieveArticleDetail.getBehaviorStream(articleId)
                .map(mDisplayableItemMapper)
                .map(State::success)
                .onErrorReturn(State::error)
                .observeOn(AndroidSchedulers.mainThread())
                .startWith(State.inProgress())
                .subscribe(mArticleLiveData::postValue,
                        e -> Log.e(TAG, "Error updating article list live data", e));
    }
}
