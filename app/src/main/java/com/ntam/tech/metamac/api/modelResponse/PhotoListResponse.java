package com.ntam.tech.metamac.api.modelResponse;

import com.ntam.tech.metamac.model.Photo;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bassiouny on 19/10/17.
 */

public class PhotoListResponse extends ParentResponse {

    @SerializedName("all_photos")
    private List<Photo> photos;

    public List<Photo> getPhotos() {
        if(photos==null)
            photos=new ArrayList<>();
        return photos;
    }
}
