package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubScribeRepository;
import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper; // qlrm 은 db에서 result된 결과를 java클래스에 매핑해줌
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private  final SubScribeRepository subScribeRepository ;
    private final EntityManager em ;
    // 엔티티 매니저 Repostity Entity는 Manager를 구현해서 만든 구현 체임
    //쿼리를 직접 구현 할거임

    @Transactional(readOnly = true)
    public List<SubscribeDto>구독리스트 (int principalId,int pageUserId){
        // 꼭 append 후에 한깔 뛰우기 ,

        //쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id , u.username , u.profileImageUrl, ");
        sb.append("if ( (SELECT True FROM  subscribe WHERE fromUserId =? AND toUserId = u.id) ,1,0) subscribeState , "); //?는 프린시펄 아이디
        sb.append("if( (? =u.id),1,0 ) equalUserState "); // equal 확인 이니까 로그인 한 아이디 들어와
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?"); //세미쿨론 첨부하면 안됨 // ? 페이지 주인
        //1.물음표 principalId
        //2. 물음표 principalId
        //3. 물음표 pageUserId

        //쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1,principalId)
                .setParameter(2,principalId)
                .setParameter(3,pageUserId) ; // 페이지 주인 아이디

        //쿼리 실행 (qlrm 라이브러리 필룡 = Dto에 DB결과를 매핑하기 위하여 )
        JpaResultMapper result = new JpaResultMapper() ;
        result.list(query,SubscribeDto.class); // 한건 리턴 유니크 리절트 , 여러개리턴 list
        List<SubscribeDto> subscribeDtos =result.list(query, SubscribeDto.class) ;
        System.out.println(subscribeDtos);
        return subscribeDtos;
    }


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
