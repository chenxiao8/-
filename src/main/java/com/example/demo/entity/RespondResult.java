package com.example.demo.entity;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * @author 李昕
 * @date 2021/12/30 21:23
 * 封装结果集用
 */
@Data
public class RespondResult<T> implements Serializable {

    // 信息
    private String msg;
    // 数据
    private T data;

    // 成功返回
    public static ResponseEntity<Object> success(Object data) {
        RespondResult<Object> result = new RespondResult<>();
        result.setData(data);
        return new ResponseEntity<>(data, HttpStatus.valueOf(200));
    }

    // 失败返回
    public static ResponseEntity<Object> error(String msg, Integer code) {
        RespondResult<Object> result = new RespondResult<>();
        result.setMsg(msg);
        return new ResponseEntity<>(result, HttpStatus.valueOf(code));
    }
}
