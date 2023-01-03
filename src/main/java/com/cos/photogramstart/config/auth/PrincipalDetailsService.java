package com.cos.photogramstart.config.auth;


import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service //ioc
public class PrincipalDetailsService implements UserDetailsService {
//로그인 요청시

    //1. 패스워드는 알아서 체킹하니까 신경 쓸필요 없다.
    // 2. 리턴이 잘되면 자동으로 UserDetails 타입으로 세션으로 만들다.
    private  final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);
        //System.out.println("나 실행돼?" +username) ;
        if(userEntity == null){
            return  null ; // 결과 없으면 null
        }else {
            // 있으면 UserDetails로 리턴해야함
            return new PrincipalDetails(userEntity);


        }

    }
}
