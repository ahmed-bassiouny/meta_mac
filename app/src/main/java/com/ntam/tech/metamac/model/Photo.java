package com.ntam.tech.metamac.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bassiouny on 19/10/17.
 */

public class Photo implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("username")
    private String userName;
    @SerializedName("image")
    private String image;
    @SerializedName("image_time")
    private String imageTime;
    @SerializedName("comments")
    private List<Comment> comments;
    @SerializedName("likes")
    private String numberOfLike;
    @SerializedName("make_like")
    private int makeLike;

    public Photo(String id,String imagUrl){
        this.id=id;
        this.image=imagUrl;
    }

    public int getId() {
        try{
            return Integer.parseInt(id);
        }catch (Exception e){
            return 0;
        }
    }

    private String getUserId() {
        return userId;
    }

    public String getImage() {
        return image;
    }

    public String getImageTime() {
        return imageTime;
    }

    public List<Comment> getComments() {
        return comments;
    }

    private String getNumberOfLike() {
        return numberOfLike;
    }

    public boolean isLiked() {
        if (makeLike == 1)
            return true;
        return false;
    }

    public void changeLike() {
        if(makeLike == 0)
            makeLike = 1;
        else
            makeLike=0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.image);
        dest.writeString(this.imageTime);
        dest.writeList(this.comments);
        dest.writeString(this.numberOfLike);
        dest.writeInt(this.makeLike);
    }

    protected Photo(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.userName = in.readString();
        this.image = in.readString();
        this.imageTime = in.readString();
        this.comments = new ArrayList<Comment>();
        in.readList(this.comments, Comment.class.getClassLoader());
        this.numberOfLike = in.readString();
        this.makeLike = in.readInt();
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
