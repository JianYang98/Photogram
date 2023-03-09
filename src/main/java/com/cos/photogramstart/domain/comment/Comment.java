package com.cos.photogramstart.domain.comment;

import com.cos.photogramstart.domain.Image.Image;
import com.cos.photogramstart.domain.user.User;
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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @Column(length = 100 , nullable = false)
    private String  content;// 댓글


    @JoinColumn(name="userId")
    @ManyToOne(fetch = FetchType.EAGER)
    private User  user ; // 누가 쓴 댓글 // 한명에 유저는 댓글을여러개 쓸수 있따 N:1

    @JoinColumn(name="c")
    @ManyToOne(fetch = FetchType.EAGER)
    private Image image ; // 어떤 이미지에 // 하나에 이미지에 여러 댓글이 달려 N:1
//패치 전략 셀력트 할때 여러개 오면 LAZY로 셀렉트시 여러개 와? EAGER

    private LocalDateTime createDate ; // 만든 날짜
    @PrePersist // 디비에 자동으로 insert되기 직전 실행
    public void createDate(){
        this.createDate = LocalDateTime.now() ;
    }

}
