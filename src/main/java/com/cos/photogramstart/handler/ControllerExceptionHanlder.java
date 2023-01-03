package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // 데이터 전달
@ControllerAdvice // 이게 Exception시 다 낚아챔
public class ControllerExceptionHanlder {
    @ExceptionHandler(CustomValidationException.class) // 런타임 입셉션을 예가 다!!! 리턴함
    public String ValidationException(CustomValidationException e){
        return Script.back(e.getErrorMap().toString());
        // CRM 코드 , 메세지 , T에 맞춰서 넣음
        // CMRespDTo, Script 비교
        //1.클라이언트에게 응답할때는 Script 좋음
        // 2. Ajax 통신 - CMRespDto
        //3 .Android 통신 - CMRespDto
//    CMRespDto
//    public CMRespDto<?> ValidationException(CustomValidationException e){
//        return new CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrorMap()) ;
//        // CRM 코드 , 메세지 , T에 맞춰서 넣음

    }
}
