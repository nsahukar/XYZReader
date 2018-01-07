package com.nsahukar.android.article.domain;

import android.support.annotation.NonNull;

import com.nsahukar.android.article.data.Article;
import com.nsahukar.android.article.data.ArticleRepository;
import com.nsahukar.android.base.domain.ReactiveInteractor.RetrieveInteractor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

import static io.reactivex.Single.just;

/**
 * Created by Nikhil on 07/12/17.
 */

public class RetrieveArticleList implements RetrieveInteractor<Void, List<Article>> {
    private static final String TAG = RetrieveArticleList.class.getSimpleName();

    @NonNull
    private ArticleRepository mArticleRepository;

    @Inject
    public RetrieveArticleList(@NonNull final ArticleRepository articleRepository) {
        mArticleRepository = articleRepository;
    }

    @NonNull
    @Override
    public Flowable<List<Article>> getBehaviorStream(@NonNull Void params) {
        return mArticleRepository.getAllArticles()
                // fetch if emitted value is none
                .flatMapSingle(this::fetchWhenNoneAndThenArticles);
    }

    @NonNull
    private Single<List<Article>> fetchWhenNoneAndThenArticles(final List<Article> articles) {
        return fetchWhenNone(articles).andThen(just(articles));
    }

    @NonNull
    private Completable fetchWhenNone(@NonNull final List<Article> articles) {
        return articles.size() == 0
                ? mArticleRepository.fetchArticles()
                : Completable.complete();
    }
}