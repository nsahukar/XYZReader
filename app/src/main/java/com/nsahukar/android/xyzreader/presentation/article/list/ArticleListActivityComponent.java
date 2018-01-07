package com.nsahukar.android.xyzreader.presentation.article.list;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by Nikhil on 08/12/17.
 */

@Subcomponent(modules = ArticleListActivityModule.class)
public interface ArticleListActivityComponent extends AndroidInjector<ArticleListActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<ArticleListActivity> {
    }
}