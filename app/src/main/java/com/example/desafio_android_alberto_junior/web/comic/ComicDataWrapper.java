package com.example.desafio_android_alberto_junior.web.comic;

public class ComicDataWrapper {
    private int code;
    private String status;
    private ComicDataContainer data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ComicDataContainer getData() {
        return data;
    }

    public void setData(ComicDataContainer data) {
        this.data = data;
    }
}
