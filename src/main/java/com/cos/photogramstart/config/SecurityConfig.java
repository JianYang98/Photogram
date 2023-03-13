package com.cos.photogramstart.config;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 파일로 시큐리티 활성화
@Configuration // IOC
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2DetailsService oAuth2DetailsService ;

    @Bean // BCryptPasswordEncoder를 빈으로 들고 있음
    public BCryptPasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception{
//        super.configure(http); // 이게 보안 가로젬 ,
//        super삭제 - 기존 시큐리티 비활성화
        //http.csrf();

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/","/uer/**"  ,"/image/**","/subscribe/**","/comment/**","/api/**")
                .authenticated() // 인증이 필요함
                .anyRequest().permitAll() // 위가 아닌 것은 인증 x
                .and()
                .formLogin() // 인증 필요할때는 홈 로그인 할건데 그건 바로 너의 밑 페이지임
                .loginPage("/auth/signin")
                //GET 인증이 필요하면 GET으로 loginPage로 보내줌
                .loginProcessingUrl("/auth/signin")
                // POST으로 /auth/signin으로 요청시 스프링 시큐리티가 로그인 프로세스 진행
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                //oauth 로그인도 할꺼야
                .userInfoEndpoint()
                // oauth2 로그인을 하면 최종 응답을 회원정보를 바로 받을 수있다.
                .userService(oAuth2DetailsService)
                ;

    }
}
