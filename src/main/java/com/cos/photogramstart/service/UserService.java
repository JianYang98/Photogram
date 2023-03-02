package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubScribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
//test
    private  final BCryptPasswordEncoder bCryptPasswordEncoder ;
    private  final UserRepository userRepository ;
    private  final SubScribeRepository subScribeRepository;
    //@Transactional(readOnly = true)
    public UserProfileDto 회원프로필 (int pageUserId ,int principalId) { // 해당페이지에

       //51강이후 추가된 UserProfileDto
        UserProfileDto dto = new UserProfileDto() ;

        // select * from image where  userId =:userid ;4
        User userEntity = userRepository.findById(pageUserId).orElseThrow(()-> {
            // 이거 에러시 alert 안뜸 이거 수정 바람
            throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다") ;
        }) ;
        //userEntity.getImages().get(0) ; // get 0번지 까지 // getImage가 호출되는 순간 select로 가져옴

        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId == principalId);
        dto.setImageCount(userEntity.getImages().size());

        // 페이지 주인과 , 프린시펄 주인이 같으면 true 다르면 false

        int subscribeState= subScribeRepository.mSubscribeState(principalId,pageUserId);
        int subscribeCount = subScribeRepository.mSubscribeCount(pageUserId);

        dto.setSubscribeState(subscribeState ==1);
        dto.setSubscribeCount(subscribeCount);

        return dto ; // 유저 정보

    }
    @Transactional
    public User 회원수정 (int id , User user) {
        // 1영속화
            User userEntity = userRepository.findById(id).orElseThrow(()->{ return new CustomValidationException("찾을 수 없는 id입니다. "); });


            // id찾아서 넣음
            // 1. 무조건 찾았다 걱정마 get , 2. 못찾았어 앱센션 ㄱㄱ orElseThrow()


            //비밀번호 암호화 ㄱㄱ
            String rawPassword = user.getPassword() ;
            String encPassword = bCryptPasswordEncoder.encode(rawPassword) ;

        //2영속화된 오브젝트 수정  - 더티체킹(업데이트 완료)
            userEntity.setName(user.getName());
            userEntity.setPassword(encPassword);
            userEntity.setBio(user.getBio());
            userEntity.setWebsite(user.getWebsite());
            userEntity.setPhone(user.getPhone());
            userEntity.setGender(user.getGender());


        return userEntity ;  // 이때 더티체킹이 일어나서 업데이트가 완료됨
    }
}
