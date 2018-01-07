package com.nsahukar.android.xyzreader.presentation.article.list;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nsahukar.android.article.data.Article;
import com.nsahukar.android.article.presentation.list.ArticleListContract;
import com.nsahukar.android.article.presentation.list.ArticleListViewModel;
import com.nsahukar.android.base.injection.qualifiers.ForArticleList;
import com.nsahukar.android.base.network.NetworkUnavailableException;
import com.nsahukar.android.base.presentation.BaseActivity;
import com.nsahukar.android.base.presentation.DisplayableItem;
import com.nsahukar.android.base.presentation.State;
import com.nsahukar.android.base.presentation.recyclerview.RecyclerViewAdapter;
import com.nsahukar.android.xyzreader.R;
import com.nsahukar.android.xyzreader.presentation.article.detail.ArticleDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.nsahukar.android.article.presentation.ArticlePresentationConstants.IntentExtras.ARTICLE_ID;

public class ArticleListActivity extends BaseActivity
        implements ArticleListContract.OnItemClickListener {

    private static final String TAG = ArticleListActivity.class.getSimpleName();
    @Inject
    @ForArticleList ViewModelProvider.Factory mViewModelFactory;
    @Inject RecyclerViewAdapter mAdapter;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.article_list_layout) ConstraintLayout mArticleListLayout;
    @BindView(R.id.error_layout) ConstraintLayout mErrorLayout;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.article_list) RecyclerView mArticleList;
    @BindView(R.id.error_msg) TextView mErrorMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        configureRecyclerView();
        getArticles(false);
    }

    private void configureRecyclerView() {
        mArticleList.setLayoutManager(
                new GridLayoutManager(this,
                        getResources().getInteger(R.integer.grid_span_count),
                        GridLayoutManager.VERTICAL,
                        false));
        mArticleList.setHasFixedSize(true);
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(this,
                R.dimen.grid_item_offset);
        mArticleList.addItemDecoration(itemOffsetDecoration);
        mArticleList.setAdapter(mAdapter);
    }

    private void getArticles(boolean retry) {
        showArticleListLayout();
        final ArticleListViewModel viewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(ArticleListViewModel.class);
        if (!retry) {
            viewModel.getArticleListLiveData().
                    observe(this, this::checkArticleListStateThenUpdateUI);
        } else {
            viewModel.bind();
        }
    }

    private void showArticleListLayout() {
        mArticleListLayout.setVisibility(VISIBLE);
        mErrorLayout.setVisibility(INVISIBLE);
    }

    private void showErrorLayout(String errorMessage) {
        mArticleListLayout.setVisibility(INVISIBLE);
        mErrorLayout.setVisibility(VISIBLE);
        mErrorMessageTextView.setText(errorMessage);
    }

    private void checkArticleListStateThenUpdateUI(
            @NonNull final State<List<DisplayableItem>> bundle) {
        mProgressBar.setVisibility(bundle.isInProgress() ? VISIBLE : GONE);
        if (!bundle.isInProgress()) {
            if (bundle.success()) {
                updateArticleList(bundle.displayableItem());
            } else {
                Log.e(TAG, "Failed to retrieve article list: "
                        + bundle.error().getClass());
                if (bundle.error() instanceof NetworkUnavailableException) {
                    showErrorLayout(getString(R.string.err_network_unavailable));
                }
            }
        }
    }

    private void updateArticleList(List<DisplayableItem> displayableItems) {
        mAdapter.update(displayableItems);
    }

    // Decoration for the grid item
    private class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
        private int mItemOffset;
        private int mGridSpanCount;

        ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
            mGridSpanCount = getResources().getInteger(R.integer.grid_span_count);
        }

        ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int left = 0;
            int top = 0;
            int right = 0;
            int bottom = 0;
            if (mGridSpanCount == 1) {
                final int itemCount = parent.getAdapter().getItemCount();
                final int position = parent.getChildAdapterPosition(view);
                if (position != itemCount - 1) {
                    bottom = mItemOffset;
                }
            } else {
                final int itemCount = parent.getAdapter().getItemCount();
                final int position = parent.getChildAdapterPosition(view);
                if (position % mGridSpanCount != mGridSpanCount - 1) {
                    right = mItemOffset;
                }
                if (position <= itemCount - mGridSpanCount - 1) {
                    bottom = mItemOffset;
                }
            }
            outRect.set(left, top, right, bottom);
        }
    }

    // Retry getting articles
    @OnClick(R.id.retry)
    void retry() {
        getArticles(true);
    }

    @Override
    public void openArticleDetail(Article article, View view) {
        Intent openArticleDetailIntent = new Intent(this, ArticleDetailActivity.class);
        openArticleDetailIntent.putExtra(ARTICLE_ID, article.getId());
        startActivity(openArticleDetailIntent);
    }
}