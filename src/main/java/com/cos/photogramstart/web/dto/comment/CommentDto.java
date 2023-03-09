package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentDto {
    @NotBlank
    private  String content;

    @NotBlank
    private int imageId ;

    //toEntity가 필요 없음
}
