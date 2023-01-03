package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
@Data
public class PrincipalDetails  implements UserDetails {
    private static final long serialVersionUID = 1L;
    private User user ;
    public PrincipalDetails(User user){
        this.user =user ;
    }

    // 권한 : 한개가 아닐 수 있어서 Collection을 받음 (3개 이상의 권한 가능)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collector = new ArrayList<>() ;
        collector.add(() -> { return  user.getRole();
        }) ;
        // JAVA는 매개변수에 함수 못넣고 , 인터페이스 /오브젝트 넣을 수 있음, 람다로 함수 넘김

/*      위가 람다 , 밑이 풀코드
        collector.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        }) ;

 */
        return collector;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { // 계정이 만료가 되었니? 4개중 하나라고 false면 로그인 x
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정이 잠겨있지 않니?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비번 만료되었지 않았니?
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정이 활성화 되어있니?
        return true;
    }
}
