package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {// 구독 정보 dto
    private int id ;
    private String username ;
    private String profileImageUrl ;
    private Integer subscribeState ; // 구독 상태야?
    private Integer equalUserState ; // 동일인지 아닌지
}
