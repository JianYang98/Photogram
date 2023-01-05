package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubScribeRepository;
import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private  final SubScribeRepository subScribeRepository ;

    @Transactional
    public void 구독하기(int fromUserId , int toUserId){
        try{
            subScribeRepository.mSubscribe(fromUserId, toUserId) ;

        }catch (Exception e ){
            throw new CustomApiException("이미 구독을 하였습니다. ") ;
        }
    }
    @Transactional
    public void 구독취소(int fromUserId , int toUserId){
        subScribeRepository.mUnSubscribe(fromUserId, toUserId) ;

    }

}
