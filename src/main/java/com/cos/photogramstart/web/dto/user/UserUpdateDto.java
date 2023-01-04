package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateDto {
    @NotBlank
    private String name; // 필수
    @NotBlank
    private String password; // 필수
    private String website ;
    private String bio ;
    private String phone ;
    private String gender ;
    //조금 위험함
    public User toEntity(){
        return User.builder()
                .name(name)
                .password(password) // b패스워드 기재 안했으면 문제 Validation 체크
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }
}
