package com.example.desafio_android_alberto_junior.web.comic;

import com.example.desafio_android_alberto_junior.database.Comic;

import java.util.List;

public class ComicDataContainer {
    private int offset;
    private int limit;
    private int total;
    private int count;
    private List<Comic> results;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Comic> getResults() {
        return results;
    }

    public void setResults(List<Comic> results) {
        this.results = results;
    }
}
