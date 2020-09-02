package com.mylife.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
/**
 * @descirption : 通用请求返回结果类
 * @author : wyh
 * @date : 2020/9/2 10:52
 */
@Data
public class Result<T> {
    /**
     * 请求是否成功：true：成功；false：失败；
     */
    private boolean success;
    /**
     * 消息（失败状态码描述、请求失败信息、异常信息）
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY) //jackson实体转json，为NULL的字段不参加序列化
    private String message;
    /**
     * 请求返回数据
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T data;

    //----- 构造器 -----
    private Result(boolean success) {
        this.success = success;
    }

    private Result(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    private Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    //----- 静态方法 -----
    public static <T> Result<T> success() {
        return new Result<>(true);
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(true, message);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, data);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(false, message);
    }

}
