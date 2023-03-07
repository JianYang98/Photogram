package com.cos.photogramstart.domain.Image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Integer> {

    @Query(value = "SELECT * FROM image WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromUserId =:principalId) ORDER BY id DESC",nativeQuery = true)
    Page<Image> mStory(int principalId, Pageable pageable);
    // 페이징 자동으로 됨 , 페이지 가져올때 3건씩 자동으로 가져옴

    @Query(value="SELECT i.* FROM image i INNER JOIN (SELECT imageId , count(imageId) AS likesCount FROM likes GROUP BY imageId)c on i.id = c.imageId ORDER BY likesCount DESC" , nativeQuery = true)
    List<Image> mPopular();

}
