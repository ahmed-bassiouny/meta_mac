package com.ntam.tech.metamac.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmed on 11/10/17.
 */

public class Comment implements Parcelable {

    @SerializedName("id")
    private String commentId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("username")
    private String userName;
    @SerializedName("comment")
    private String comment;
    @SerializedName("comment_date")
    private String commentDate;
    @SerializedName("user_image")
    private String userAvatar;


    public String getCommentId() {
        return commentId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.commentId);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.comment);
        dest.writeString(this.commentDate);
        dest.writeString(this.userAvatar);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.commentId = in.readString();
        this.userId = in.readString();
        this.userName = in.readString();
        this.comment = in.readString();
        this.commentDate = in.readString();
        this.userAvatar = in.readString();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
