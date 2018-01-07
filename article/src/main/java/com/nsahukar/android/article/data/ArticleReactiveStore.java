package com.nsahukar.android.article.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.nsahukar.android.base.data.ReactiveStore;

import java.util.List;

import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Nikhil on 09/12/17.
 */

@Dao
public interface ArticleReactiveStore extends ReactiveStore<Long, Article> {
    @Insert(onConflict = IGNORE)
    void storeSingular(@NonNull Article article);

    @Insert(onConflict = IGNORE)
    void storeAll(@NonNull List<Article> articles);

    @Update(onConflict = REPLACE)
    void replaceAll(@NonNull List<Article> articles);

    @Query("SELECT * FROM Article WHERE _id = :arg0")
    Flowable<Article> getSingular(@NonNull Long articleId);

    @Query("SELECT * FROM Article")
    Flowable<List<Article>> getAll();
}
