package com.nsahukar.android.article.domain;

import android.support.annotation.NonNull;

import com.nsahukar.android.article.data.Article;
import com.nsahukar.android.article.data.ArticleRepository;
import com.nsahukar.android.base.domain.ReactiveInteractor.RetrieveInteractor;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by Nikhil on 07/12/17.
 */

public class RetrieveArticleDetail implements RetrieveInteractor<Long, Article> {
    private static final String TAG = RetrieveArticleDetail.class.getSimpleName();

    @NonNull
    private ArticleRepository mArticleRepository;

    @Inject
    public RetrieveArticleDetail(@NonNull final ArticleRepository articleRepository) {
        mArticleRepository = articleRepository;
    }

    @NonNull
    @Override
    public Flowable<Article> getBehaviorStream(@NonNull Long params) {
        return mArticleRepository.getArticle(params);
    }
}