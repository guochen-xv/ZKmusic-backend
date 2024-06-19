package com.android.serviceBase.handler;




import commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//统一异常类
@ControllerAdvice
@Slf4j//统一日志，将错误输出的文件
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error().message("执行了全局的异常处理");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了特定的异常处理(这里为除数不能为0)");
    }

    //自定义异常
    @ExceptionHandler(ZkException.class)
    @ResponseBody
    public R error(ZkException e){
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMessage());
    }
}
