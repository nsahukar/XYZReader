package com.nsahukar.android.article.data;

import android.support.annotation.NonNull;

import com.google.gson.TypeAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import retrofit2.Retrofit;

/**
 * Created by Nikhil on 04/12/17.
 */

@Module
public class ArticleDataModule {
    @Provides
    @IntoSet
    TypeAdapterFactory provideTypeAdapterFactory() {
        return ArticleTypeAdapterFactory.create();
    }

    @Provides
    ArticleService provideArticleService(@NonNull Retrofit retrofit) {
        return retrofit.create(ArticleService.class);
    }
}
