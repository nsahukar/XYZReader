package com.nsahukar.android.article.presentation.list;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.nsahukar.android.article.data.Article;
import com.nsahukar.android.base.common.utils.DateUtils;
import com.nsahukar.android.base.presentation.DisplayableItem;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import static com.nsahukar.android.article.presentation.ArticlePresentationConstants.DisplayableTypes.ARTICLE_LIST;
import static com.nsahukar.android.base.presentation.DisplayableItem.toDisplayableItem;

/**
 * Created by Nikhil on 15/12/17.
 */

class ArticleListDisplayableItemMapper implements Function<List<Article>, List<DisplayableItem>> {
    private ArticleCardViewEntityMapper mArticleCardViewEntityMapper;

    @Inject
    ArticleListDisplayableItemMapper(@NonNull ArticleCardViewEntityMapper mapper) {
        mArticleCardViewEntityMapper = mapper;
    }

    private DisplayableItem wrapInDisplayableItem(ArticleCardViewEntity articleCardViewEntity) {
        return toDisplayableItem(articleCardViewEntity, ARTICLE_LIST);
    }

    @Override
    public List<DisplayableItem> apply(List<Article> articles) throws Exception {
        return Observable.fromIterable(articles)
                .map(mArticleCardViewEntityMapper)
                .sorted(new ArticleCardViewEntityComparator())
                .map(this::wrapInDisplayableItem)
                .toList()
                .blockingGet();
    }

    @SuppressLint("SimpleDateFormat")
    private class ArticleCardViewEntityComparator implements Comparator<ArticleCardViewEntity> {
        @Override
        public int compare(ArticleCardViewEntity articleCardViewEntity1,
                           ArticleCardViewEntity articleCardViewEntity2) {
            try {
                final Date publishedDate1 =
                        DateUtils.OUTPUT_FORMAT.parse(articleCardViewEntity1.publishedDate());
                final Date publishedDate2 =
                        DateUtils.OUTPUT_FORMAT.parse(articleCardViewEntity2.publishedDate());
                return publishedDate2.compareTo(publishedDate1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return -1;
        }
        @Override
        public boolean equals(Object o) {
            return false;
        }
    }
}