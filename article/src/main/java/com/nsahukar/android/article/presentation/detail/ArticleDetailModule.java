package com.nsahukar.android.article.presentation.detail;

import android.arch.lifecycle.ViewModelProvider;

import com.nsahukar.android.base.injection.qualifiers.ForArticleDetail;
import com.nsahukar.android.base.presentation.utils.ViewModelUtil;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nikhil on 08/12/17.
 */

@Module
public abstract class ArticleDetailModule {
    @ForArticleDetail
    @Provides
    static ViewModelProvider.Factory provideDetailViewModelProviderFactory
            (ViewModelUtil viewModelUtil, ArticleDetailViewModel viewModel) {
        return viewModelUtil.createFor(viewModel);
    }
}