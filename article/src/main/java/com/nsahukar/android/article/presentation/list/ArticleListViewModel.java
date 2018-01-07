package com.nsahukar.android.article.presentation.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nsahukar.android.article.domain.RetrieveArticleList;
import com.nsahukar.android.base.presentation.DisplayableItem;
import com.nsahukar.android.base.presentation.State;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Nikhil on 08/12/17.
 */

public class ArticleListViewModel extends ViewModel {
    private static final String TAG = ArticleListViewModel.class.getSimpleName();

    @NonNull
    private final RetrieveArticleList mRetrieveArticleList;

    @NonNull
    private final ArticleListDisplayableItemMapper mDisplayableItemMapper;

    @NonNull
    private final MutableLiveData<State<List<DisplayableItem>>> mArticleListLiveData = new MutableLiveData<>();

    @NonNull
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Inject
    ArticleListViewModel(@NonNull final RetrieveArticleList retrieveArticleList,
                         @NonNull final ArticleListDisplayableItemMapper displayableItemMapper) {
        mRetrieveArticleList = retrieveArticleList;
        mDisplayableItemMapper = displayableItemMapper;
        bind();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
    }

    public void bind() {
        mCompositeDisposable.add(bindToArticles());
    }

    @NonNull
    public LiveData<State<List<DisplayableItem>>> getArticleListLiveData() {
        return mArticleListLiveData;
    }

    @NonNull
    private Disposable bindToArticles() {
        return mRetrieveArticleList.getBehaviorStream(null)
                .map(mDisplayableItemMapper)
                .map(State::success)
                .onErrorReturn(State::error)
                .observeOn(AndroidSchedulers.mainThread())
                .startWith(State.inProgress())
                .subscribe(mArticleListLiveData::postValue,
                        e -> Log.e(TAG, "Error updating article list live data", e));
    }
}
