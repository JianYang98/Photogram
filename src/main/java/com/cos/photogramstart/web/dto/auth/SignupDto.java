package com.cos.photogramstart.web.dto.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

@Data // getter , setter
public class SignupDto { // signup Dto
    private String username ;
    private String password;
    private String email;
    private String name ;

//바로 바듬
    public User toEntity(){
        return User.builder().username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }
}
