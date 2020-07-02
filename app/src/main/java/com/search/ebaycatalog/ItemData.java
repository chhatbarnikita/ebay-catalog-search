package com.search.ebaycatalog;

import java.io.Serializable;

public class ItemData implements Serializable {
    private String itemId;
    private String imageUrl;
    private String title;
    private String condition;
    private String isTopRatedImage;
    private Double freeShipping;
    private Double price;
    private String shippingType;
    private String handlingTime;
    private String oneDayShippingAvailable;
    private String shippingFrom;
    private String shipToLocations;
    private String expeditedShipping;


    public ItemData(String itemId, String imageUrl, String title, String condition, String isTopRatedImage, Double freeShipping, Double price,
                    String shippingType, String handlingTime, String oneDayShippingAvailable, String shippingFrom, String shipToLocations, String expeditedShipping) {
        this.itemId = itemId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.condition = condition;
        this.isTopRatedImage = isTopRatedImage;
        this.freeShipping = freeShipping;
        this.price = price;
        this.shippingType = shippingType;
        this.handlingTime = handlingTime;
        this.oneDayShippingAvailable = oneDayShippingAvailable;
        this.shippingFrom = shippingFrom;
        this.shipToLocations = shipToLocations;
        this.expeditedShipping = expeditedShipping;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String isTopRatedImage() {
        return isTopRatedImage;
    }

    public void setTopRatedImage(String topRatedImage) {
        isTopRatedImage = topRatedImage;
    }

    public Double getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(Double freeShipping) {
        this.freeShipping = freeShipping;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getHandlingTime() {
        return handlingTime;
    }

    public void setHandlingTime(String handlingTime) {
        this.handlingTime = handlingTime;
    }

    public String getOneDayShippingAvailable() {
        return oneDayShippingAvailable;
    }

    public void setOneDayShippingAvailable(String oneDayShippingAvailable) {
        this.oneDayShippingAvailable = oneDayShippingAvailable;
    }

    public String getShippingFrom() {
        return shippingFrom;
    }

    public void setShippingFrom(String shippingFrom) {
        this.shippingFrom = shippingFrom;
    }

    public String getShipToLocations() {
        return shipToLocations;
    }

    public void setShipToLocations(String shipToLocations) {
        this.shipToLocations = shipToLocations;
    }

    public String getExpeditedShipping() {
        return expeditedShipping;
    }

    public void setExpeditedShipping(String expeditedShipping) {
        this.expeditedShipping = expeditedShipping;
    }
}