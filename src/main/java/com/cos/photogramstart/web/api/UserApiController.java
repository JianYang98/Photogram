package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {


    private  final UserService userService ;
    @PutMapping("/api/user/{id}")
    public  CMRespDto<?> update(
            @PathVariable int id,
            @Valid UserUpdateDto userupdateDto,
            BindingResult bindingResult , // @Vaild가 꼭 적혀있는 다음 파라메타에 적어야함
            @AuthenticationPrincipal PrincipalDetails principalDetails){

            if(bindingResult.hasErrors()){ // 만약 벨레이드션 체크에서 위배되면 에러됨
                Map<String,String> errorMap = new HashMap<>();
                for(FieldError error : bindingResult.getFieldErrors()){
                    // bindingResult 에러값을 체크해서 error로 받아서 errorMap에 넣음
                    errorMap.put(error.getField() , error.getDefaultMessage()); // 에러 메세지랑 필드 넣어줌
                    System.out.println("==========");
                    System.out.println(error.getDefaultMessage()) ;
                    System.out.println("==========");
                }
                //
                throw new CustomValidationApiException("유효성 검사 실패함" ,errorMap ) ;
            }else{
                log.info("여기왔니  user  resAPI" ,userupdateDto) ;
                User userEntity =userService.회원수정(id,userupdateDto.toEntity()) ;
                principalDetails.setUser(userEntity); //  세션 정보 변경
                return new CMRespDto<>(1,"회원수정완료",userEntity);

            }

    }

}
