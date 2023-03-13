package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService ;

    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails){
        //RequestBody 제이쓴 데이터 받기
        //System.out.println(commentDto);

/*
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
        }
*/
        Comment comment = commentService.댓글쓰기(commentDto.getContent(), commentDto.getImageId(),principalDetails.getUser().getId()) ;
        return  new ResponseEntity<>(new CMRespDto<>(1,"댓글쓰기 성공",comment), HttpStatus.CREATED) ;
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable int id ){
        commentService.댓글삭제(id);

        return  new ResponseEntity<>(new CMRespDto<>(1,"댓글 삭제성공",null), HttpStatus.OK) ;
    }

}
