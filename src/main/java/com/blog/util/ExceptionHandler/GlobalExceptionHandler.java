package com.blog.util.ExceptionHandler;

import com.blog.util.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Result exceptionHandler(Exception e){
        //这里先判断拦截到的Exception是不是我们自定义的异常类型
        if(e instanceof BlogException){
            BlogException blogException = (BlogException)e;
            return Result.fail(blogException);
        }
        //如果拦截的异常不是我们自定义的异常(例如：数据库主键冲突)
        return Result.fail("大错特错错误");
    }
}