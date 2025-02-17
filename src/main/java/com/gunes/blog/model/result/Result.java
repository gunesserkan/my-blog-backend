package com.gunes.blog.model.result;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private boolean isSuccess;
    private String message;

    public Result(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Result success(String message) {
        return new Result(true, message);
    }

}
