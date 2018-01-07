package com.nsahukar.android.article.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Nikhil on 08/12/17.
 */

@Entity
public class Article {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    private long id;
    private String title;
    private String author;
    private String body;
    private String thumbnail;
    private String photo;
    @ColumnInfo(name = "aspect_ratio")
    private float aspectRatio;
    @ColumnInfo(name = "published_date")
    private String publishedDate;

    /**
     *  Getters
     */
    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getBody() {
        return body;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public String getPhoto() {
        return photo;
    }
    public float getAspectRatio() {
        return aspectRatio;
    }
    public String getPublishedDate() {
        return publishedDate;
    }
    
    /**
     *  Setters
     */
    public void setId(long id) {
        this.id = id;
    }
    public void setTitle(@NonNull String title) {
        this.title = title;
    }
    public void setAuthor(@NonNull String author) {
        this.author = author;
    }
    public void setBody(@NonNull String body) {
        this.body = body;
    }
    public void setThumbnail(@NonNull String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public void setPhoto(@NonNull String photo) {
        this.photo = photo;
    }
    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }
    public void setPublishedDate(@NonNull String publishedDate) {
        this.publishedDate = publishedDate;
    }
}