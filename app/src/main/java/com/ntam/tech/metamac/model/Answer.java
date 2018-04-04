package com.ntam.tech.metamac.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmed on 15/10/17.
 */

public class Answer {

    @SerializedName("id")
    private int id;
    @SerializedName("body")
    private String body;

    public int getId() {
        return id;
    }

    public String getBody() {
        if(body == null)
            body = "";
        return body;
    }
}
