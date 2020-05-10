package com.example.desafio_android_alberto_junior.web.character;

public class CharacterDataWrapper {
    private int code;
    private String status;
    private CharacterDataContainer data;

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

    public CharacterDataContainer getData() {
        return data;
    }

    public void setData(CharacterDataContainer data) {
        this.data = data;
    }
}
