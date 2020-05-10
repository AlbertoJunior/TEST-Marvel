package com.example.desafio_android_alberto_junior.web;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class WebClient {
    private static final String hash = "625064f6c38964b223225c0411a320df";
    private static final String JSON_CONFIG = "application/json; charset=utf-8";
    private static final MediaType JSON_TYPE = MediaType.parse(JSON_CONFIG);
    private OkHttpClient client;

    public WebClient() {
        client = new OkHttpClient();
        client.setConnectTimeout(2000, TimeUnit.MILLISECONDS);
        client.setReadTimeout(2000, TimeUnit.MILLISECONDS);
        client.setWriteTimeout(2000, TimeUnit.MILLISECONDS);
    }

    private void get(String url, Callback callback) {
        Request request = new Request
                .Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void getCharactersEnqueue(int limit, Callback callback) {
        getCharactersEnqueue(limit, 0, callback);
    }

    public void getCharactersEnqueue(int limit, int offset, Callback callback) {
        String url = String.format(Locale.ENGLISH,
                "https://gateway.marvel.com/v1/public/characters?orderBy=name&limit=%d&offset=%d&ts=1&apikey=ecb68ec3f6e8fae2cbf91a98e74018eb&hash=%s",
                limit, offset, hash);
        get(url, callback);
    }

    public void getCharacterComicsEnqueue(int characterId, int limit, int offset, Callback callback) {
        String url = String.format(Locale.ENGLISH,
                "https://gateway.marvel.com/v1/public/characters/%d/comics?limit=%d&offset=%d&ts=1&apikey=ecb68ec3f6e8fae2cbf91a98e74018eb&hash=%s",
                characterId, limit, offset, hash);
        get(url, callback);
    }
}
