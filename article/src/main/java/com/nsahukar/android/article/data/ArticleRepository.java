package com.nsahukar.android.article.data;

import android.support.annotation.NonNull;

import com.nsahukar.android.base.injection.scopes.ApplicationScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikhil on 04/12/17.
 */

@ApplicationScope
public class ArticleRepository {
    private static final String TAG = ArticleRepository.class.getSimpleName();

    @NonNull
    private final ArticleService mArticleService;

    @NonNull
    private final ArticleMapper mArticleMapper;

    @NonNull
    private final ArticleReactiveStore mArticleReactiveStore;

    @Inject
    public ArticleRepository(@NonNull final ArticleService articleService,
                             @NonNull final ArticleMapper articleMapper,
                             @NonNull final ArticleReactiveStore articleReactiveStore) {
        mArticleService = articleService;
        mArticleMapper = articleMapper;
        mArticleReactiveStore = articleReactiveStore;
    }

    @NonNull
    public Flowable<List<Article>> getAllArticles() {
        return mArticleReactiveStore.getAll();
    }

    @NonNull
    public Flowable<Article> getArticle(Long articleId) {
        return mArticleReactiveStore.getSingular(articleId);
    }

    @NonNull
    public Completable fetchArticles() {
        return mArticleService.getArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMapObservable(Observable::fromIterable)
                // map from raw to safe
                .map(mArticleMapper)
                .toList()
                // put mapped objects in store
                .doOnSuccess(mArticleReactiveStore::storeAll)
                .toCompletable();
    }
}
