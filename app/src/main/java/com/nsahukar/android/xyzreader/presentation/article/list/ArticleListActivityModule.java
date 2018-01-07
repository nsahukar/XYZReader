package com.nsahukar.android.xyzreader.presentation.article.list;

import android.content.Context;

import com.nsahukar.android.article.presentation.list.ArticleListModule;
import com.nsahukar.android.base.injection.qualifiers.ForArticleList;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Nikhil on 08/12/17.
 */

@Module(includes = ArticleListModule.class)
abstract class ArticleListActivityModule {
    @Binds
    @ForArticleList
    abstract Context bindArticleListActivityContext(ArticleListActivity activity);
}