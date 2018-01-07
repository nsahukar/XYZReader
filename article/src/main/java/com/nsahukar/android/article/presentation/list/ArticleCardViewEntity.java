package com.nsahukar.android.article.presentation.list;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * Created by Nikhil on 23/12/17.
 */

@AutoValue
abstract public class ArticleCardViewEntity {
    @NonNull public abstract long id();
    @NonNull public abstract String title();
    @NonNull public abstract String author();
    @NonNull public abstract String publishedDate();
    @NonNull public abstract String thumbnail();

    @NonNull
    public static Builder builder() {
        return new AutoValue_ArticleCardViewEntity.Builder();
    }

    @AutoValue.Builder
    interface Builder {
        Builder id(final long id);
        Builder title(final String title);
        Builder author(final String author);
        Builder publishedDate(final String publishedDate);
        Builder thumbnail(final String photoUrl);
        ArticleCardViewEntity build();
    }
}
