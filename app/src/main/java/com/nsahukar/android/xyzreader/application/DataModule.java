package com.nsahukar.android.xyzreader.application;

import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.nsahukar.android.article.data.ArticleDataModule;
import com.nsahukar.android.article.data.ArticleReactiveStore;
import com.nsahukar.android.base.injection.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nikhil on 08/12/17.
 */

@Module(includes = {ArticleDataModule.class})
class DataModule {
    @Provides
    ApplicationDatabase provideApplicationDatabase(XYZReaderApplication application) {
        return Room.databaseBuilder(application, ApplicationDatabase.class, ApplicationDatabase.NAME)
                .build();
    }

    @Provides
    @ApplicationScope
    ArticleReactiveStore provideArticleReactiveStore(@NonNull ApplicationDatabase database) {
        return database.articleReactiveStore();
    }
}
