package com.ntam.tech.metamac.api.modelResponse;

import com.ntam.tech.metamac.model.Chat;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bassiouny on 18/10/17.
 */

public class ChatListResponse extends ParentResponse {

    @SerializedName("chat_list")
    private List<Chat> chatLists;

    public List<Chat> getChatLists() {
        if(chatLists==null)
            chatLists=new ArrayList<>();
        return chatLists;
    }
}
