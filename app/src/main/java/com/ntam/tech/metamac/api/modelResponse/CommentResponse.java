package com.ntam.tech.metamac.api.modelResponse;

import com.ntam.tech.metamac.model.Comment;
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
