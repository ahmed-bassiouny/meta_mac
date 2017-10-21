package com.example.ahmed.eyecare.api.modelResponse;

import com.example.ahmed.eyecare.model.Comment;
import com.google.gson.annotations.SerializedName;


/**
 * Created by ahmed on 21/10/17.
 */

public class CommentResponse extends ParentResponse {

    @SerializedName("comment")
    private Comment comment;

    public Comment getComment() {
        if (comment == null)
            comment = new Comment();
        return comment;
    }
}
