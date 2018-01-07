package com.nsahukar.android.xyzreader.presentation.article.detail;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by Nikhil on 25/12/17.
 */

@Subcomponent(modules = ArticleDetailActivityModule.class)
public interface ArticleDetailActivityComponent extends AndroidInjector<ArticleDetailActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<ArticleDetailActivity> {
    }
}
