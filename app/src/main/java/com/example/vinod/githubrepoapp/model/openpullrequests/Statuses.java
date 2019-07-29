package com.example.vinod.githubrepoapp.model.openpullrequests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Statuses implements Serializable {

    @SerializedName("href")
    @Expose
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
