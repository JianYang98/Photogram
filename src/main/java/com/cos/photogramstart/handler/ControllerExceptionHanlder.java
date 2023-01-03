package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // 데이터 전달
@ControllerAdvice // 이게 Exception시 다 낚아챔
public class ControllerExceptionHanlder {
    @ExceptionHandler(CustomValidationException.class) // 런타임 입셉션을 예가 다!!! 리턴함
    public Map<String, String> ValidationException(CustomValidationException e){
        return e.getErrorMap() ;

    }
}
