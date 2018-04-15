package com.ntam.tech.metamac.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bassiouny on 18/10/17.
 */

public class About {

    @SerializedName("content")
    private String content;
    @SerializedName("image")
    private String image;
    @SerializedName("event_tag")
    private String tag;
    @SerializedName("pdf_file")
    private String pdf;

    public String getImage() {
        return image;
    }

    public String getTag() {
        return tag;
    }

    public String getContent() {
        return content;
    }

    public String getPdf() {
        return pdf;
    }
}
