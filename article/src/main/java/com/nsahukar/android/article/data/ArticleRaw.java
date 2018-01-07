package com.nsahukar.android.article.data;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 08/12/17.
 */

@AutoValue
abstract class ArticleRaw {
    abstract long id();
    abstract String title();
    abstract String author();
    abstract String body();
    @SerializedName("thumb") abstract String thumbnail();
    abstract String photo();
    @SerializedName("aspect_ratio") abstract float aspectRatio();
    @SerializedName("published_date") abstract String publishedDate();

    static TypeAdapter<ArticleRaw> typeAdapter(@NonNull Gson gson) {
        return new AutoValue_ArticleRaw.GsonTypeAdapter(gson);
    }
}