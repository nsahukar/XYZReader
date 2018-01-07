package com.nsahukar.android.xyzreader.application;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nsahukar.android.article.data.Article;
import com.nsahukar.android.article.data.ArticleReactiveStore;

/**
 * Created by Nikhil on 09/12/17.
 */

@Database(entities = {Article.class}, version = 1)
abstract class ApplicationDatabase extends RoomDatabase {
    static final String NAME = "xyzreader.db";
    abstract ArticleReactiveStore articleReactiveStore();
}