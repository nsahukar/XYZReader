package com.nsahukar.android.article.presentation.list;

import com.nsahukar.android.article.data.Article;
import com.nsahukar.android.base.common.utils.DateUtils;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by Nikhil on 23/12/17.
 */

class ArticleCardViewEntityMapper implements Function<Article, ArticleCardViewEntity> {
    @Inject
    ArticleCardViewEntityMapper() {}

    @Override
    public ArticleCardViewEntity apply(Article article) throws Exception {
        // map article published date to readable format
        final String formattedPublishedDate = DateUtils.getFormattedDate(article.getPublishedDate());

        return ArticleCardViewEntity.builder()
                .id(article.getId())
                .title(article.getTitle())
                .author(article.getAuthor())
                .publishedDate(formattedPublishedDate)
                .thumbnail(article.getPhoto())
                .build();
    }
}
