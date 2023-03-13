package com.cos.photogramstart.domain.user;

//JPA  - JAVA Psersistence API(데이터를 영구적으로 저장 API)

import com.cos.photogramstart.domain.Image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @Column(length = 100,unique = true) // 유니크키 걸음
    // OAuth2로그인을 위해 칼럼 늘리기
    private String username ;
    @Column(nullable = false) // 널부가
    private String password ;
    @Column(nullable = false)
    private String name ;
    private String website;
    private String bio; // 자기소개
   @Column(nullable = false)
    private String email;
    private String phone ;
    private String gender ;
    
    private String profileImageUrl ; // 사진
    private String role ; //권한

    // 1.mappedBy user , 나는 연관관계의 주인 아니다 ,주인을 Iamge 테이블의 user이다. 그러므로 테이블에 컬럼 만들지마 , 넣음
    //2. User를 select 할때 해당 user id로 등록된 image들을 다 가져와 (Fetch 명시 x )
    // // Fetch Lazy는 User select 하떄 해당 UserID로 등록된 image들을 가져오지마 - 대신 getImage 함수가 호출될때 image들가져와
    // // Fetch Eager User select 하떄 해당 UserID로 등록된 image들을 전부 Join햇 가져와
    // 한명의 유저는 여러 이미지 가능
    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"}) // getter로 부를때 Image안에 user getter 호출 안되게 막음 
    //JSONIgnore 안하면 JPA 무한 참고가 걸림 ,,,,, 절대 앙ㄷ댐
    private List<Image> images ; // 양방향 매핑
    // IMage내에 있는 User를 JSON 파생되는거 막아야한다.

    private LocalDateTime createDate ; // 만든 날짜

    @PrePersist // 디비에 자동으로 insert되기 직전 실행 
    public void createDate(){
        this.createDate = LocalDateTime.now() ;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", role='" + role + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
