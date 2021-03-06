package com.ntam.tech.metamac.api.modelResponse;

import com.ntam.tech.metamac.model.Post;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmedbassiouny on 12/10/17.
 */

public class PostResponse extends  ParentResponse {

    @SerializedName("post_data")
    private Post post;

    public Post getPost() {
        if(post ==null)
            post=new Post();
        return post;
    }
}
