package com.search.ebaycatalog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class DetailedItemData implements Serializable {
    private JSONObject itemSpecifics;
    private String itemUrl;
    private JSONObject seller;
    private JSONObject returnPolicy;
    private JSONArray pictureUrl;
    private String subtitle;

    public DetailedItemData() { }

    public DetailedItemData(JSONObject itemSpecifics, String itemUrl, JSONObject seller, JSONObject returnPolicy, JSONArray pictureUrl, String subtitle) {
        this.itemSpecifics = itemSpecifics;
        this.itemUrl = itemUrl;
        this.seller = seller;
        this.returnPolicy = returnPolicy;
        this.pictureUrl = pictureUrl;
        this.subtitle = subtitle;
    }

    public JSONObject getItemSpecifics() {
        return itemSpecifics;
    }

    public void setItemSpecifics(JSONObject itemSpecifics) {
        this.itemSpecifics = itemSpecifics;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public JSONObject getSeller() {
        return seller;
    }

    public void setSeller(JSONObject seller) {
        this.seller = seller;
    }

    public JSONObject getReturnPolicy() {
        return returnPolicy;
    }

    public void setReturnPolicy(JSONObject returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    public JSONArray getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(JSONArray pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
