package com.github.uuidcode.builder.request;

import java.util.List;

public class Result {
    private Long page;
    private Long totalPages;
    private List<Data> data;

    public List<Data> getData() {
        return this.data;
    }

    public Result setData(List<Data> data) {
        this.data = data;
        return this;
    }
    public Long getTotalPages() {
        return this.totalPages;
    }
    
    public Result setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
        return this;
    }
    public Long getPage() {
        return this.page;
    }

    public Result setPage(Long page) {
        this.page = page;
        return this;
    }
}
