package com.situ.shoplook2025.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult<T> {
    private int code;
    private boolean success;
    private String msg;
    private T data;

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<T>(200, true, "操作成功", data);
    }

    public static <T> JsonResult<T> success() {
        return new JsonResult<T>(200, true, "操作成功", null);
    }

    public static <T> JsonResult<T> fail(int code, String msg) {
        return new JsonResult<T>(code, false, msg, null);
    }

    public static <T> JsonResult<T> fail(String msg) {
        return new JsonResult<T>(500, false, msg, null);
    }
}