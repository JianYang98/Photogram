package com.cos.photogramstart.domain.subscribe;

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
@Table(
        uniqueConstraints = { // 두개 복합 유니크시 할것
                @UniqueConstraint(
                        name = "subscribe_uk"  , // 모델명_네임
                        columnNames={"fromUserId" ,"toUserId"}  //칼럼명 적기

                )
        }
)
public class Subscribe {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @JoinColumn(name="fromUserId")
    @ManyToOne
    private User fromUSer ;

    @JoinColumn(name="toUserId")
    @ManyToOne
    private User toUser ;
    private LocalDateTime createDate ; // 만든 날짜

    @PrePersist // 디비에 자동으로 insert되기 직전 실행
    public void createDate(){
        this.createDate = LocalDateTime.now() ;
    }


}
