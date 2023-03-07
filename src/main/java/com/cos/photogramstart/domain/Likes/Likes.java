package com.cos.photogramstart.domain.Likes;

import com.cos.photogramstart.domain.Image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder // 패턴으로 데이터를 담을 수 있게 해주는 어노테이션
@AllArgsConstructor // 모든 생성자 자동으로 만들어주는 너도 테이션
@NoArgsConstructor // 빈 생성자 자동으로 만들어주는 어노테이션
@Data   // 자동으로 getter,setter,toString 만들어주는 어노테이션
@Entity // 디비에 테이블 생성
@Table(
        uniqueConstraints = { // 두개 복합 유니크시 할것
                @UniqueConstraint(
                        name = "likes_uk"  , // 유니크 제약조건 이름모델명_네임
                        columnNames={"imageId" ,"userId"}  //칼럼명 적기
                        //어떤 이미지를 누가 좋아했나요?
                )
        }
)
public class Likes { // N  여러좋아요는 하나에 이미지에 할 수 있따.
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    //무한 참조
    @JoinColumn(name="imageId")
    @ManyToOne
    private Image image ; // 1  // 좋아요가 된 것이 어떤 이미지인가?


    // likes 안에 user안에 imagesd들을 못나오게 막는 중
    @JsonIgnoreProperties({"images"}) // 유저를 들고 올때 images
    @JoinColumn(name="userId")
    @ManyToOne
    private User user ; //무한 참조 , 좋아요를 누가 한 것인가?


    private LocalDateTime createDate ; // 만든 날짜

    @PrePersist // 디비에 자동으로 insert되기 직전 실행
    public void createDate(){
        this.createDate = LocalDateTime.now() ;
    }


}



