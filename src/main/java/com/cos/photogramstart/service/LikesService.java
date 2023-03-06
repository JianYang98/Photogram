package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.Likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikesService {
    private  final LikesRepository likesRepository ;


    @Transactional
    public void 좋아요(int imageId, int principalId) {
        likesRepository.mLikes(imageId,principalId) ;

    }
    @Transactional
    public void 좋아요취소(int imageId, int principalId) {
          likesRepository.mUnLikes( imageId, principalId);
    }
}
