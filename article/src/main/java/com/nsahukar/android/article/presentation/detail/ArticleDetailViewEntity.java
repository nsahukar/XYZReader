package com.nsahukar.android.article.presentation.detail;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * Created by Nikhil on 23/12/17.
 */

@AutoValue
abstract public class ArticleDetailViewEntity {
    @NonNull public abstract String title();
    @NonNull public abstract String author();
    @NonNull public abstract String publishedDate();
    @NonNull public abstract String photo();
    @NonNull public abstract String htmlBody();

    @NonNull
    public static Builder builder() {
        return new AutoValue_ArticleDetailViewEntity.Builder();
    }

    @AutoValue.Builder
    interface Builder {
        Builder title(final String title);
        Builder author(final String author);
        Builder publishedDate(final String publishedDate);
        Builder photo(final String photoUrl);
        Builder htmlBody(final String htmlUrl);
        ArticleDetailViewEntity build();
    }
}
