package com.mylife.advice;

import com.mylife.util.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @descirption : 全局controller异常处理类
 * @author : wyh
 * @date : 2020/9/4 14:50
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
       return Result.fail("请求异常，请稍后重试");
    }

}
