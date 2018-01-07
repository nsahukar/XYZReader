package com.nsahukar.android.xyzreader.presentation.article.detail;

import android.content.Context;

import com.nsahukar.android.article.presentation.detail.ArticleDetailModule;
import com.nsahukar.android.base.injection.qualifiers.ForArticleDetail;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Nikhil on 16/12/17.
 */

@Module(includes = ArticleDetailModule.class)
abstract class ArticleDetailActivityModule {
    @Binds
    @ForArticleDetail
    abstract Context bindArticleDetailActivityContext(ArticleDetailActivity activity);
}