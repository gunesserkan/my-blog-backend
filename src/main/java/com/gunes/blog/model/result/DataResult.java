package com.gunes.blog.model.result;


import lombok.Getter;

@Getter
public class DataResult <T> extends Result{
    private T data;

    public DataResult(T data, boolean isSuccess) {
        super(isSuccess);
        this.data = data;
    }

    public DataResult(T data, String message, boolean isSuccess) {
        super(isSuccess, message);
        this.data = data;
    }
}
