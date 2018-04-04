package com.ntam.tech.metamac.api.modelResponse;

import com.google.gson.annotations.SerializedName;
import com.ntam.tech.metamac.model.Question;

/**
 * Created by bassiouny on 04/04/18.
 */

public class QuestionResponse extends ParentResponse {

    @SerializedName("data")
    private Question question;

    public Question getQuestion() {
        if (question == null)
            question = new Question();
        return question;
    }
}
