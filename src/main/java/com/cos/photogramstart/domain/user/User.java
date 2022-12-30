package com.cos.photogramstart.domain.user;

//JPA  - JAVA Psersistence API(데이터를 영구적으로 저장 API)

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder // 패턴으로 데이터를 담을 수 있게 해주는 어노테이션
@AllArgsConstructor // 모든 생성자 자동으로 만들어주는 너도 테이션
@NoArgsConstructor // 빈 생성자 자동으로 만들어주는 어노테이션
@Data   // 자동으로 getter,setter,toString 만들어주는 어노테이션
@Entity // 디비에 테이블 생성
 public class User {
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //번호 증가 전략이 데이터비으스를 따라감
    private int id ;
    //정말 집에 가고 싶어
   // @Column(unique = true)
    private String username ;
    private String password ;
    private String name ;
    private String website;
    private String bio; // 자기소개
    private String email;
    private String phone ;
    private String gender ;
    
    private String profileImageUrl ; // 사진
    private String role ; //권한 
    private LocalDateTime createDate ; // 만든 날짜

    @PrePersist // 디비에 자동으로 insert되기 직전 실행 
    public void createDate(){
        this.createDate = LocalDateTime.now() ;
    }
}
