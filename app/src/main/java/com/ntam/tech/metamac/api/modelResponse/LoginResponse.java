package com.ntam.tech.metamac.api.modelResponse;

import com.ntam.tech.metamac.model.User;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmed on 10/10/17.
 */

public class LoginResponse extends ParentResponse {
    @SerializedName("user_data")
    private User user;

    public User getUser() {
        if(user == null)
            user = new User();
        return user;
    }

}
