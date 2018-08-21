package com.jchaviel.soccerleaguesapp.entities;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

/**
 * Created by jchavielreyes On 12/07/2016.
 */
public class New implements Serializable {
    @Exclude
    private String id;

    private String mTitle;
    private String mImageLink;
    private String mDate;
    private String mLink;

    public New(String title, String imageLink, String date, String link) {
        this.mTitle = title;
        this.mImageLink = imageLink;
        this.mDate = date;
        this.mLink = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return mLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getImageLink() {
        return mImageLink;
    }

    public String getDate() {
        return mDate;
    }
}
