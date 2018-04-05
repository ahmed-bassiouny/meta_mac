package com.ntam.tech.metamac.api.modelResponse;

import com.google.gson.annotations.SerializedName;
import com.ntam.tech.metamac.model.WebViewModel;

/**
 * Created by bassiouny on 05/04/18.
 */

public class WebViewResponse extends ParentResponse{

    @SerializedName("data")
    private WebViewModel webViewModel;

    public WebViewModel getWebViewModel() {
        if(webViewModel == null)
            webViewModel = new WebViewModel();
        return webViewModel;
    }
}
