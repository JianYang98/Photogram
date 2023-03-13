package com.cos.photogramstart.config.oauth;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository ;
   // private final BCryptPasswordEncoder bCryptPasswordEncoder ; //비크립트
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

          System.out.println("OAtu2  서비스 탐@@@@@@@@");
        //return super.loadUser(userRequest);
        OAuth2User oAuth2User = super.loadUser(userRequest) ;
        //System.out.print(oAuth2User.getAttributes()) ;
        Map<String,Object> userInfo = oAuth2User.getAttributes() ;

        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        //페이스워드로 로그인 하는 거 아님!! 그래서 UUID 해줌 1
        String name  =(String) userInfo.get("name");
        String email  =(String) userInfo.get("email");
        String username  ="facebook_"+(String) userInfo.get("id");

        User userEntity = userRepository.findByUsername(username);
        if(userEntity == null) { // 회원 x , 페이스북 최소!
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .name(name)
                    .email(email)
                    .role("ROLE_USER")
                    .build();

            return new PrincipalDetails(userRepository.save(user),oAuth2User.getAttributes()) ;


        }else { // 이미 페이스북으로 회원가입 되어 있다는 뜻!!
            return new PrincipalDetails(userEntity,oAuth2User.getAttributes()) ;
        }


     }
}
