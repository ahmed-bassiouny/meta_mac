package com.ntam.tech.metamac.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ahmed on 15/10/17.
 */

public class Question {

    @SerializedName("id")
    private int id;
    @SerializedName("body")
    public String question;
    @SerializedName("answers")
    public List<Answer> answers;

}
