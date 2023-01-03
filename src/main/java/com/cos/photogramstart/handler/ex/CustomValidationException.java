package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationException  extends  RuntimeException{

    //객체 구분
    private static final long serialVersionUID = 1L;

    ///private String message ;
    private Map<String,String> errorMap ;

    //생성자
    public CustomValidationException(String message , Map<String,String> errorMap) {
        super(message);
        //this.message = message;
        this.errorMap=errorMap ;
    //생성자
    }
    
    //MapGetter 생성
    public Map<String,String>getErrorMap(){
        return errorMap ;
    }
}


