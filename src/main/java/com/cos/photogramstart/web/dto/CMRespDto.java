package com.cos.photogramstart.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto<T> { // 응답 DTO
    //T를 넣는것은 에러 Map이나 , USer가 있을수 있으니 제네릭 하자
    private int code ; // 성공 1 , 실패 -1
    private String message ;
    private T data ;
//    private Map<String,String> errorMap ;

}
