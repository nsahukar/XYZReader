package com.nsahukar.android.article.presentation.detail;

import android.support.annotation.NonNull;

import com.nsahukar.android.article.data.Article;
import com.nsahukar.android.base.presentation.DisplayableItem;

import javax.inject.Inject;

import io.reactivex.functions.Function;

import static com.nsahukar.android.article.presentation.ArticlePresentationConstants.DisplayableTypes.ARTICLE_DETAIL;
import static com.nsahukar.android.base.presentation.DisplayableItem.toDisplayableItem;

/**
 * Created by Nikhil on 15/12/17.
 */

class ArticleDetailDisplayableItemMapper implements Function<Article, DisplayableItem> {
    private static final String TAG = ArticleDetailDisplayableItemMapper.class.getSimpleName();
    private ArticleDetailViewEntityMapper mViewEntityMapper;

    @Inject
    ArticleDetailDisplayableItemMapper(@NonNull ArticleDetailViewEntityMapper viewEntityMapper) {
        mViewEntityMapper = viewEntityMapper;
    }

    private DisplayableItem wrapInDisplayableItem(@NonNull ArticleDetailViewEntity article) {
        return toDisplayableItem(article, ARTICLE_DETAIL);
    }

    @Override
    public DisplayableItem apply(Article article) throws Exception {
        return wrapInDisplayableItem(mViewEntityMapper.apply(article));
    }
}
