package com.nsahukar.android.xyzreader.presentation.article.detail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nsahukar.android.article.presentation.detail.ArticleDetailViewEntity;
import com.nsahukar.android.article.presentation.detail.ArticleDetailViewModel;
import com.nsahukar.android.base.injection.qualifiers.ForArticleDetail;
import com.nsahukar.android.base.presentation.BaseActivity;
import com.nsahukar.android.base.presentation.DisplayableItem;
import com.nsahukar.android.base.presentation.State;
import com.nsahukar.android.xyzreader.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.nsahukar.android.article.presentation.ArticlePresentationConstants.IntentExtras.ARTICLE_ID;

public class ArticleDetailActivity extends BaseActivity {

    private static final String TAG = ArticleDetailActivity.class.getSimpleName();
    private static final String STATE_SCROLL_PROGRESSION = "state:web:view:progression";

    @Inject
    @ForArticleDetail ViewModelProvider.Factory mViewModelFactory;
    @BindView(R.id.detail_content) CoordinatorLayout mDetailLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.scroll_view) NestedScrollView mScrollView;
    @BindView(R.id.article_photo) ImageView mPhoto;
    @BindView(R.id.article_title) TextView mTitle;
    @BindView(R.id.article_author) TextView mAuthor;
    @BindView(R.id.article_published_date) TextView mPublishedDate;
    @BindView(R.id.article_body) WebView mBody;
    @BindView(R.id.share_fab) FloatingActionButton mShareFab;

    private float mScreenRatio;
    private int mScrollProgression;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        calculateScreenRatio();

        // get article id from intent extra
        long articleId = -1;
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null && extras.containsKey(ARTICLE_ID)) {
                articleId = extras.getLong(ARTICLE_ID);
            }
        }
        // get web view progression from savedInstanceState
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(STATE_SCROLL_PROGRESSION)) {
                mScrollProgression = savedInstanceState.getInt(STATE_SCROLL_PROGRESSION);
            }
        }

        // configure views
        configureWebView();
        configureShareFab();

        // get article live data from view model
        final ArticleDetailViewModel viewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(ArticleDetailViewModel.class);
        viewModel.getArticleLiveData(articleId).observe(this, this::checkArticleStateThenUpdateUI);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SCROLL_PROGRESSION, calculateScrollProgression());
    }

    private void calculateScreenRatio() {
        mDetailLayout.post(new Runnable() {
            @Override
            public void run() {
                float height = mDetailLayout.getHeight()
                        - (getToolbarHeight() + dpToPixels(32));
                float width = mDetailLayout.getWidth();
                mScreenRatio = height / width;
            }
        });
    }

    private void configureWebView() {
        mBody.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(GONE);
                if (mScrollProgression > 0) {
                    mScrollView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int positionY = Math.round(mScrollProgression * mScreenRatio);
                            mScrollView.scrollTo(0, positionY);
                        }
                    }, 300);
                }
            }
        });
        mBody.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    private void configureShareFab() {
        mShareFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder
                        .from(ArticleDetailActivity.this)
                        .setType("text/plain")
                        .setSubject(mTitle.getText().toString())
                        .setText("\"" + mTitle.getText().toString() + "\"\n\n<Link to the article>")
                        .getIntent(), getString(R.string.action_share)
                ));
            }
        });
    }

    private void checkArticleStateThenUpdateUI(@NonNull final State<DisplayableItem> bundle) {
        if (bundle.isInProgress()) mProgressBar.setVisibility(VISIBLE);
        if (!bundle.isInProgress()) {
            if (bundle.success()) {
                updateArticle(bundle.displayableItem());
            } else {
                Log.e(TAG, "Failed to retrieve article: "
                        + bundle.error().getMessage());
            }
        }
    }

    private void updateArticle(DisplayableItem displayableItem) {
        ArticleDetailViewEntity articleDetailViewEntity =
                ArticleDetailViewEntity.class.cast(displayableItem.model());
        Picasso.with(this).load(articleDetailViewEntity.photo()).into(mPhoto);
        mTitle.setText(articleDetailViewEntity.title());
        mAuthor.setText(articleDetailViewEntity.author());
        mPublishedDate.setText(articleDetailViewEntity.publishedDate());
        mBody.loadUrl(articleDetailViewEntity.htmlBody());
    }

    private int calculateScrollProgression() {
        return mScrollView.getScrollY();
    }

    private float getToolbarHeight() {
        return getResources().getBoolean(R.bool.is_tablet) ?
                dpToPixels(64) :
                dpToPixels(56);
    }

    private float dpToPixels(int dpValue) {
        return dpValue * getResources().getDisplayMetrics().density;
    }
}