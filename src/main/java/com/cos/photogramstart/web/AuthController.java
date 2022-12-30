package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    @GetMapping("/auth/signup")
    public String signupStringForm(){

        return"auth/signup" ;
    }
    //회원 가입 진행 ->/auth/signup -> /auth/signin
    @PostMapping("/auth/signup")
    public String signUp(SignupDto signupDto){
        log.info(signupDto.toString());
        User user = signupDto.toEntity(); // siguDto를 바로 toEntity
        log.info(user.toString());
        User userEntity = authService.회원가입(user) ;
        System.out.println(userEntity);

        return "auth/signin";
    }

}
