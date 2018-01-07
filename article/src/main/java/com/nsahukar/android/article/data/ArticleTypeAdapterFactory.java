package com.nsahukar.android.article.data;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by Nikhil on 04/12/17.
 */

@GsonTypeAdapterFactory
public abstract class ArticleTypeAdapterFactory implements TypeAdapterFactory {
    static TypeAdapterFactory create() {
        return new AutoValueGson_ArticleTypeAdapterFactory();
    }
}
