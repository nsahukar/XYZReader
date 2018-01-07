package com.nsahukar.android.article.data;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by Nikhil on 04/12/17.
 */

public interface ArticleService {
    @GET("xyz-reader-json")
    Single<List<ArticleRaw>> getArticles();
}
