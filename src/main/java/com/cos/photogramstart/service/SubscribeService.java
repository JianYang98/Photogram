package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubScribeRepository;
import com.cos.photogramstart.domain.subscribe.Subscribe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private  final SubScribeRepository subScribeRepository ;

    @Transactional
    public void 구독하기(int fromUserId , int toUserId){
        subScribeRepository.mSubscribe(fromUserId, toUserId) ;
    }
    @Transactional
    public void 구독취소(int fromUserId , int toUserId){
        subScribeRepository.mUnSubscribe(fromUserId, toUserId) ;

    }

}
