package com.cos.photogramstart.handler.aop;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component // 애매한거 component로 띄우기!
@Aspect //
public class ValidationAdvice {

    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))") // 전/후 처리  접근제어자 패키지(경로에) ~컨트롤러에 메시지
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
    //proceedingJoinPoint 는 매개변수 뿐만 아니라 , 내부까지 접근 할 수 있다.
        //profile 함수보다 먼저 실행
        System.out.println("=============web api 컨틀러 =============");

        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg:args){ // arg 매개변수에 접근해서 뽑아봄
            System.out.println(arg);
            if(arg instanceof BindingResult){
                System.out.println("유효성검사하는 함수입니다. ");
                BindingResult bindingResult = (BindingResult) arg ; // 다운 캐스팅
                // api CommentApi컨트롤러 있떤 유효성 검사 코드 그대로 가져옴
                if(bindingResult.hasErrors()){ // 만약 벨레이드션 체크에서 위배되면 에러됨
                    Map<String,String> errorMap = new HashMap<>();
                    for(FieldError error : bindingResult.getFieldErrors()){
                        // bindingResult 에러값을 체크해서 error로 받아서 errorMap에 넣음
                        errorMap.put(error.getField() , error.getDefaultMessage()); // 에러 메세지랑 필드 넣어줌
                        System.out.println(error.getDefaultMessage()) ;
                    }
                    //
                    throw new CustomValidationApiException("유효성 검사 실패함" ,errorMap ) ;
                }
            }
        }
        return proceedingJoinPoint.proceed(); // 그 함수로 다시 돌아가라
    }

    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        System.out.println("=============web 컨틀러 =============");
        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg:args){
            System.out.println(arg);
            if(arg instanceof BindingResult){
                BindingResult bindingResult = (BindingResult) arg ; // 다운 캐스팅

                if(bindingResult.hasErrors()){ // 만약 벨레이드션 체크에서 위배되면 에러됨
                    Map<String,String> errorMap = new HashMap<>();
                    for(FieldError error : bindingResult.getFieldErrors()){
                        // bindingResult 에러값을 체크해서 error로 받아서 errorMap에 넣음
                        errorMap.put(error.getField() , error.getDefaultMessage()); // 에러 메세지랑 필드 넣어줌
                        System.out.println(error.getDefaultMessage()) ;
                    }
                    // DTO가 Valid션 체크에서 위배되면 바로!!! bindingResult에.hasError가 걸리면 , 커스텀 입셉션으ㅗㄹ 떨림
                    throw new CustomValidationException("유효성 검사 실패함" ,errorMap ) ;
                } // bindingResult end

            } //BindingResult end
        } // for end
        return proceedingJoinPoint.proceed();

    }

}
