package com.nsahukar.android.article.data;

import android.text.TextUtils;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by Nikhil on 08/12/17.
 */

class ArticleMapper implements Function<ArticleRaw, Article> {
    private static final String TAG = ArticleMapper.class.getSimpleName();

    @Inject
    ArticleMapper() {}

    private static void assertEssentialParams(ArticleRaw articleRaw) {
        String missingParams = "";

        if (articleRaw.id() == 0) {
            missingParams += "id";
        }
        if (TextUtils.isEmpty(articleRaw.title())) {
            missingParams += " title";
        }
        if (TextUtils.isEmpty(articleRaw.author())) {
            missingParams += " author";
        }
        if (TextUtils.isEmpty(articleRaw.body())) {
            missingParams += " body";
        }
        if (TextUtils.isEmpty(articleRaw.thumbnail())) {
            missingParams += " thumbnail";
        }
        if (TextUtils.isEmpty(articleRaw.photo())) {
            missingParams += " photo";
        }
        if (articleRaw.aspectRatio() == 0.0) {
            missingParams += " aspect_ratio";
        }
        if (TextUtils.isEmpty(articleRaw.publishedDate())) {
            missingParams += " published_date";
        }

        if (!missingParams.isEmpty()) {
            throw new EssentialParamMissingException(missingParams, articleRaw);
        }
    }

    @Override
    public Article apply(ArticleRaw articleRaw) throws Exception {
        assertEssentialParams(articleRaw);
        Article article = new Article();
        article.setId(articleRaw.id());
        article.setTitle(articleRaw.title());
        article.setAuthor(articleRaw.author());
        article.setBody(articleRaw.body());
        article.setThumbnail(articleRaw.thumbnail());
        article.setPhoto(articleRaw.photo());
        article.setAspectRatio(articleRaw.aspectRatio());
        article.setPublishedDate(articleRaw.publishedDate());
        return article;
    }
}