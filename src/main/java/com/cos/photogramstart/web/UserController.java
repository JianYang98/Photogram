package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
@Slf4j

public class UserController {

    private final UserService userService ;

    @GetMapping("/user/{pageUserId}") // 경로가 /user/id !!
    public String profile(@PathVariable int pageUserId ,Model model , @AuthenticationPrincipal PrincipalDetails principalDetails ){
        //log.info("profile :: " , pageUserId ,   principalDetails.getUser().getId()    );
        UserProfileDto dto = userService.회원프로필(pageUserId , principalDetails.getUser().getId()) ;
        System.out.println(" page : " +pageUserId +" profile id :" + principalDetails.getUser().getId());

        model.addAttribute("dto" , dto) ;
        return "user/profile" ;
    }
    @GetMapping("/user/{id}/update") // 경로가 /user/id !!
    public String update(@PathVariable int id , @AuthenticationPrincipal PrincipalDetails principalDetails , Model model){
        //1. 세션 정보 직접 접근
       // System.out.print("세션정보 ::  "+principalDetails.getUser());

        /*
        2.세션정보 하나하나 접근 ( 뇌절)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails mPrincipalDetails =(PrincipalDetails)auth.getPrincipal() ;
        System.out.println("Authentication " +auth.getPrincipal());
        System.out.println("직접찾은 세션 정보 auth:: " + mPrincipalDetails.getUser());
        */

         return "user/update" ;
    }

}
