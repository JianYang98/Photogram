package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // final DI
@Service // IOC ,트랜잭션 관리

public class AuthService {

    private  final UserRepository userRepository ;
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Transactional // 트랜잭션 관리 해줌 // Write(insert , update , delete )할때
    public User 회원가입 (User user){ //user는 외부에서 통신 된거 user에서 줌
        //회원가입 진행
        String rawPaasword = user.getPassword() ;
        String encPassword = bCryptPasswordEncoder.encode(rawPaasword) ; // pass 해쉬
        user.setPassword(encPassword); // 패스워드
        user.setRole("ROLE_USER");  // 관리자는 ROLE_ADMIN

        User userEntity =userRepository.save(user) ; // save는 넣은 타입으로 리턴 됨!
        //userEntity는 DB에 save된 이후에 응답으로 userEntity를 줌
        return userEntity ;
    }

}
