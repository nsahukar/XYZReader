package com.nsahukar.android.article.presentation.list;

import android.view.View;

import com.nsahukar.android.article.data.Article;

/**
 * Created by Nikhil on 20/12/17.
 */

public interface ArticleListContract {
    interface OnItemClickListener {
        void openArticleDetail(Article article, View view);
    }
}
