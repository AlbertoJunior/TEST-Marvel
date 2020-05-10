package com.example.desafio_android_alberto_junior.database;

import java.util.List;

public class Comic {
    private String title;
    private String description;
    private List<ComicPrice> prices;
    private Image thumbnail;

    private int id;
    private int digitalId;
    private int pageCount;

    private transient float auxPrice;
    private transient ComicPrice mostCost;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ComicPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<ComicPrice> prices) {
        this.prices = prices;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(int digitalId) {
        this.digitalId = digitalId;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public float getAuxPrice() {
        return auxPrice;
    }

    public void setAuxPrice(float auxPrice) {
        this.auxPrice = auxPrice;
    }
}
