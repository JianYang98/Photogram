package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationApiException extends  RuntimeException{

    //객체 구분

    ///private String message ;
    private Map<String,String> errorMap ;

    //생성자
    public CustomValidationApiException(String message , Map<String,String> errorMap) {
        super(message);
        //this.message = message;
        this.errorMap=errorMap ;
    //생성자
    }
    //생성자

    //MapGetter 생성
    public Map<String,String>getErrorMap(){
        return errorMap ;
    }
}


