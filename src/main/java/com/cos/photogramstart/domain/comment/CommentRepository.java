package com.cos.photogramstart.domain.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    /* 이렇게 하면 comment가 리턴이 안됨 !! modifying은 int만 줌
   @Modifying
    @Query(value = "INSERT INTO comment(content,imageId,userId,createDate) values (:content,:imageId,:userId,now())" , nativeQuery = true)
    Comment mSave(String content, int imageId, int userId) ;
    */


}
