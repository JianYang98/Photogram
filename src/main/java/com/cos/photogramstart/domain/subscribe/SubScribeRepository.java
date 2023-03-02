package com.cos.photogramstart.domain.subscribe;

import com.cos.photogramstart.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubScribeRepository extends JpaRepository<Subscribe,Integer>  {
    // :는 변수를 바인해서 넣겠따.
    @Modifying // 네이티브 쿼리에 넣어 줘야한다. insert , delete,update를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요
    @Query(value = "INSERT INTO subscribe (fromUserId,toUserId,createDate ) VALUES (:fromUserId,:toUserId, now())" ,nativeQuery = true)
     void mSubscribe(int fromUserId , int toUserId);
    @Modifying
    @Query(value = "DELETE  FROM subscribe WHERE fromUserId = :fromUserId and toUserId =:toUserId",nativeQuery = true)
     void mUnSubscribe(int fromUserId , int toUserId);


    @Query(value="SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId AND toUserId = :principalId ",nativeQuery = true)  // select는 모디바이 필요없음
    int mSubscribeState(int principalId, int pageUserId); // 페이지 구독상태
    @Query(value="SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId",nativeQuery = true)
    int mSubscribeCount(int pageUserId); // 구독자수


}
