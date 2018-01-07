package com.nsahukar.android.article.presentation.list;

import android.arch.lifecycle.ViewModelProvider;

import com.nsahukar.android.article.presentation.list.ArticleListCardViewHolder.ArticleListCardViewHolderBinder;
import com.nsahukar.android.article.presentation.list.ArticleListCardViewHolder.ArticleListCardViewHolderFactory;
import com.nsahukar.android.base.common.preconditions.AndroidPreconditions;
import com.nsahukar.android.base.injection.qualifiers.ForArticleList;
import com.nsahukar.android.base.presentation.recyclerview.RecyclerViewAdapter;
import com.nsahukar.android.base.presentation.recyclerview.ViewHolderBinder;
import com.nsahukar.android.base.presentation.recyclerview.ViewHolderFactory;
import com.nsahukar.android.base.presentation.utils.ViewModelUtil;

import java.util.Map;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;

import static com.nsahukar.android.article.presentation.ArticlePresentationConstants.DisplayableTypes.ARTICLE_LIST;

/**
 * Created by Nikhil on 08/12/17.
 */

@Module
public abstract class ArticleListModule {
    @ForArticleList
    @Provides
    static ViewModelProvider.Factory provideListViewModelProviderFactory
            (ViewModelUtil viewModelUtil, ArticleListViewModel viewModel) {
        return viewModelUtil.createFor(viewModel);
    }

    @Provides
    static RecyclerViewAdapter provideRecyclerAdapter(Map<Integer, ViewHolderFactory> factoryMap,
                                                      Map<Integer, ViewHolderBinder> binderMap,
                                                      AndroidPreconditions androidPreconditions) {
        return new RecyclerViewAdapter(factoryMap, binderMap, androidPreconditions);
    }

    @Binds
    @IntoMap
    @IntKey(ARTICLE_LIST)
    abstract ViewHolderFactory
    bindArticleListCardViewHolderFactory(ArticleListCardViewHolderFactory factory);

    @Binds
    @IntoMap
    @IntKey(ARTICLE_LIST)
    abstract ViewHolderBinder
    bindArticleListCardViewHolderBinder(ArticleListCardViewHolderBinder binder);
}