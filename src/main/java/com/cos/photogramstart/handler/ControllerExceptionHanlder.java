package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        // 자바스크립트 리턴
    }
    /*
     CRM 코드 , 메세지 , T에 맞춰서 넣음
     CMRespDTo, Script 비교
    1.클라이언트에게 응답할때는 Script 좋음
     2. Ajax 통신 - CMRespDto
    3 .Android 통신 - CMRespDto
    CMRespDto
    public CMRespDto<?> ValidationException(CustomValidationException e){
        return new CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrorMap()) ;
        // CRM 코드 , 메세지 , T에 맞춰서 넣음
*/

    // 데이터 리턴
    @ExceptionHandler(CustomValidationApiException.class) // 런타임 입셉션을 예가 다!!! 리턴함
    public ResponseEntity<?> ValidationApiException(CustomValidationApiException e){
        System.out.println("==========나실행해?===============================================================================");

        return new ResponseEntity<>( new CMRespDto<>(-1, e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);
        // AJAX 할때는 ResponseEntity 가 있어야 상태코드가 간다
        // Body와 상태코드 return , BadRequest 400
    }

//    // 데이터 리턴
//    @ExceptionHandler(CustomValidationApiException.class) // 런타임 입셉션을 예가 다!!! 리턴함
//    public CMRespDto<?> ValidationApiException(CustomValidationException e){
//        return new CMRespDto<>(-1, e.getMessage(),e.getErrorMap());
//
//    }

    @ExceptionHandler(CustomApiException.class) // 런타임 입셉션을 예가 다!!! 리턴함
    public ResponseEntity<?> apiException(CustomApiException e){

        return new ResponseEntity<>( new CMRespDto<>(-1, e.getMessage(),null),HttpStatus.BAD_REQUEST);
        // AJAX 할때는 ResponseEntity 가 있어야 상태코드가 간다
        // Body와 상태코드 return , BadRequest 400
    }
}
