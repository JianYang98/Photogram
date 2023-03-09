package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//Not Null = Null 체크
//NonEmpty = 빈값 or null 체크
// 빈칸 체크 or null   or 빈공백 체크

@Data
public class CommentDto {
    @NotBlank // 빈칸 체크 or null   or 빈공백 체크
    private  String content;

    @NotNull
    private Integer imageId ;

    //toEntity가 필요 없음
}
