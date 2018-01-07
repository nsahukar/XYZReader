package com.nsahukar.android.article.presentation.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.nsahukar.android.article.R;
import com.nsahukar.android.article.data.Article;
import com.nsahukar.android.base.common.utils.CacheUtils;
import com.nsahukar.android.base.common.utils.DateUtils;
import com.nsahukar.android.base.injection.qualifiers.ForArticleDetail;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by Nikhil on 23/12/17.
 */

class ArticleDetailViewEntityMapper implements Function<Article, ArticleDetailViewEntity> {
    private CacheUtils mCacheUtils;

    @Inject
    ArticleDetailViewEntityMapper(@NonNull @ForArticleDetail Context context) {
        mCacheUtils = CacheUtils.builder()
                .cacheDir(context.getCacheDir())
                .isTablet(context.getResources().getBoolean(R.bool.is_tablet))
                .build();
    }

    @Override
    public ArticleDetailViewEntity apply(Article article) throws Exception {
        // map article body in 'string' to 'html'
        String articleHtmlAbsolutePath = mCacheUtils.getCachedHtmlPath(article.getTitle());
        if (TextUtils.isEmpty(articleHtmlAbsolutePath)) {
            articleHtmlAbsolutePath = mCacheUtils.putInHtmlCache(article.getTitle(),
                    article.getBody());
        }
        final String articleHtmlUrl = "file://" + articleHtmlAbsolutePath;

        // map article published date to readable format
        final String formattedPublishedDate = DateUtils.getFormattedDate(article.getPublishedDate());

        return ArticleDetailViewEntity.builder()
                .title(article.getTitle())
                .author(article.getAuthor())
                .publishedDate(formattedPublishedDate)
                .photo(article.getPhoto())
                .htmlBody(articleHtmlUrl)
                .build();
    }
}
