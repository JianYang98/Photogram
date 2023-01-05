package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller // 1. IOC 2 .파일 리턴
@RequiredArgsConstructor // final 필드를  다 DI 해줌
public class AuthController {

    private final AuthService authService ;
    /* 생성자 주입
    public AuthController(AuthService authService){
        this.authService = authService ; // 없으면 의존성 주입
    }
     */

    @GetMapping("/auth/signin")
    public String signinStringForm(){

        return"auth/signin";
    }

    @PostMapping("/auth/signin")
    public String signinForm(){

        return"auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupStringForm(){

        return"auth/signup" ;
    }
    //회원 가입 진행 ->/auth/signup -> /auth/signin
    @PostMapping("/auth/signup") //ResponseBody 붙어있으면 데이터 응답함
    public   String signUp(@Valid  SignupDto signupDto , BindingResult bindingResult) {
        log.info("오기는 옴 ");
        //Valid 체크 해줌 BindingResult가  valid 체크함
        if(bindingResult.hasErrors()){ // 만약 벨레이드션 체크에서 위배되면 에러됨
            Map<String,String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()){
                // bindingResult 에러값을 체크해서 error로 받아서 errorMap에 넣음
                errorMap.put(error.getField() , error.getDefaultMessage()); // 에러 메세지랑 필드 넣어줌
                System.out.println("==========");
                System.out.println(error.getDefaultMessage()) ;
                System.out.println("==========");



            }
            // DTO가 Valid션 체크에서 위배되면 바로!!! bindingResult에.hasError가 걸리면 , 커스텀 입셉션으ㅗㄹ 떨림
            throw new CustomValidationException("유효성 검사 실패함" ,errorMap ) ;
        }else {


            log.info(signupDto.toString());
            User user = signupDto.toEntity(); // siguDto를 바로 toEntity
            log.info(user.toString());
            User userEntity = authService.회원가입(user) ;
            //System.out.println(userEntity);
        }


        return "auth/signin";
    }

}
