package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {


    private  final UserService userService ;
    private  final SubscribeService subscribeService ;  // di

    @PutMapping("/api/user/{principalId}/profileImageUrl")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId , MultipartFile profileImageFile,@AuthenticationPrincipal PrincipalDetails principalDetails){
        //MultipartFile의 변수 이름은 , 보내는 폼의 name값으로 해야 정확하게 매칭됨
        User userEntity =userService.회원프로필사진변경(principalId,profileImageFile);
        principalDetails.setUser(userEntity); // 세션에 사진 값 변경
        return new ResponseEntity<>(new CMRespDto<>(1,"프로필사진변경성공",null),HttpStatus.OK) ;
    }

    //구독자 정보를 위한 api
    @GetMapping("api/user/{pageUserId}/subsbcribe") // page주인을 따르고 있는 정보
    public ResponseEntity<?> subscribeList(@PathVariable int pageUserId ,@AuthenticationPrincipal PrincipalDetails principalDetails){

        List<SubscribeDto> subscribeDto = subscribeService.구독리스트(principalDetails.getUser().getId(),pageUserId) ;

        return  new ResponseEntity<>(new CMRespDto<>(1,"구독자 정보 리스트 가져보기 성공",subscribeDto), HttpStatus.OK) ;
//포스트맨 코드 http://localhost:8080/api/user/2/subsbcribe/

    }

    @PutMapping("/api/user/{id}")
    public  CMRespDto<?> update(
            @PathVariable int id,
            @Valid UserUpdateDto userupdateDto,
            BindingResult bindingResult , // @Vaild가 꼭 적혀있는 다음 파라메타에 적어야함
            @AuthenticationPrincipal PrincipalDetails principalDetails){
/*
            if(bindingResult.hasErrors()){ // 만약 벨레이드션 체크에서 위배되면 에러됨
                Map<String,String> errorMap = new HashMap<>();
                for(FieldError error : bindingResult.getFieldErrors()){
                    // bindingResult 에러값을 체크해서 error로 받아서 errorMap에 넣음
                    errorMap.put(error.getField() , error.getDefaultMessage()); // 에러 메세지랑 필드 넣어줌
                     System.out.println(error.getDefaultMessage()) ;
                 }
                //
                throw new CustomValidationApiException("유효성 검사 실패함" ,errorMap ) ;
            }else{
*/
                log.info("여기왔니  user  resAPI" ,userupdateDto) ;
                User userEntity =userService.회원수정(id,userupdateDto.toEntity()) ;
                principalDetails.setUser(userEntity); //  세션 정보 변경
                return new CMRespDto<>(1,"회원수정완료",userEntity);
                // 응답시에 userEntity의 모든 getter 함수가 호출되고 , JSON으로 파싱하여 응답
                //


    }

}
