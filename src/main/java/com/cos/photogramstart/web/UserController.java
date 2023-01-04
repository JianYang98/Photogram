package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {


    @GetMapping("/user/{id}") // 경로가 /user/id !!
    public String profile(@PathVariable int id){
        return "user/profile" ;
    }
    @GetMapping("/user/{id}/update") // 경로가 /user/id !!
    public String update(@PathVariable int id , @AuthenticationPrincipal PrincipalDetails principalDetails , Model model){
        //1. 세션 정보 직접 접근
        System.out.print("세션정보 ::  "+principalDetails.getUser());

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
