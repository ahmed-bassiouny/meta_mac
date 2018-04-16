package com.ntam.tech.metamac.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bassiouny on 05/04/18.
 */

public class WebViewModel {

    @SerializedName("body")
    private String body;
    @SerializedName("image")
    private String image;
    @SerializedName("pdf")
    private String pdf;

    public String getBody() {
        if(body == null)
            body = "";
        return body;
    }
    public String getPdf(){
        if(pdf == null)
            pdf = "";
        return pdf;
    }

    public String getImage() {
        if(image == null)
            image = "";
        return image;
    }
}
