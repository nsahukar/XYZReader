package com.nsahukar.android.xyzreader.application;

import android.app.Activity;

import com.nsahukar.android.xyzreader.presentation.article.detail.ArticleDetailActivity;
import com.nsahukar.android.xyzreader.presentation.article.detail.ArticleDetailActivityComponent;
import com.nsahukar.android.xyzreader.presentation.article.list.ArticleListActivity;
import com.nsahukar.android.xyzreader.presentation.article.list.ArticleListActivityComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by Nikhil on 08/12/17.
 */

@Module(subcomponents = {
        ArticleListActivityComponent.class,
        ArticleDetailActivityComponent.class
})
abstract class ActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(ArticleListActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindArticleListActivityInjectorFactory(ArticleListActivityComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(ArticleDetailActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindArticleDetailActivityInjectorFactory(ArticleDetailActivityComponent.Builder builder);
}