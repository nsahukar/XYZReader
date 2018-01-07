package com.nsahukar.android.article.presentation.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsahukar.android.article.R;
import com.nsahukar.android.article.data.Article;
import com.nsahukar.android.base.injection.qualifiers.ForArticleList;
import com.nsahukar.android.base.presentation.DisplayableItem;
import com.nsahukar.android.base.presentation.recyclerview.ViewHolderBinder;
import com.nsahukar.android.base.presentation.recyclerview.ViewHolderFactory;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import javax.inject.Inject;

/**
 * Created by Nikhil on 14/12/17.
 */

class ArticleListCardViewHolder extends RecyclerView.ViewHolder {
    private final ImageView mThumbnail;
    private final TextView mTitle;
    private final TextView mPublishedDate;
    private final TextView mAuthor;
    private ArticleListContract.OnItemClickListener mItemClickListener;

    ArticleListCardViewHolder(android.view.View itemView) {
        super(itemView);
        mThumbnail = itemView.findViewById(R.id.article_thumbnail);
        mTitle = itemView.findViewById(R.id.article_title);
        mPublishedDate = itemView.findViewById(R.id.article_published_date);
        mAuthor = itemView.findViewById(R.id.article_author);
        mItemClickListener = (ArticleListContract.OnItemClickListener) itemView.getContext();

    }

    private void bind(@NonNull ArticleCardViewEntity articleCardViewEntity) {
        Picasso.with(itemView.getContext())
                .load(articleCardViewEntity.thumbnail())
                .transform(new ArticleImageTransformation())
                .into(mThumbnail);
        mTitle.setText(articleCardViewEntity.title());
        mAuthor.setText(articleCardViewEntity.author());
        mPublishedDate.setText(articleCardViewEntity.publishedDate());
        itemView.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Article articleToOpen = new Article();
                articleToOpen.setId(articleCardViewEntity.id());
                mItemClickListener.openArticleDetail(articleToOpen, view);
            }
        });
    }

    static class ArticleListCardViewHolderFactory extends ViewHolderFactory {

        @Inject
        ArticleListCardViewHolderFactory(@NonNull @ForArticleList Context context) {
            super(context);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder createViewHolder(@NonNull ViewGroup parent) {
            return new ArticleListCardViewHolder(LayoutInflater.from(context)
                            .inflate(R.layout.article_list_card_item, parent, false));
        }
    }

    static class ArticleListCardViewHolderBinder implements ViewHolderBinder {

        @Inject
        ArticleListCardViewHolderBinder() {}

        @Override
        public void bind(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull DisplayableItem item) {
            ArticleListCardViewHolder articleListCardViewHolder =
                    ArticleListCardViewHolder.class.cast(viewHolder);
            ArticleCardViewEntity articleCardViewEntity = ArticleCardViewEntity.class.cast(item.model());
            articleListCardViewHolder.bind(articleCardViewEntity);
        }
    }

    class ArticleImageTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            final int requiredWidth = (int) (source.getWidth() / 1.5);
            final int requiredHeight = (int) (source.getHeight() / 1.5);
            Bitmap result = Bitmap.createScaledBitmap(source,
                    requiredWidth, requiredHeight, false);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "article:image";
        }
    }
}