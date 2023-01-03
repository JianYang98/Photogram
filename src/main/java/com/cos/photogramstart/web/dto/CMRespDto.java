package com.cos.photogramstart.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto { // 응답 DTO
    private String message ;
    private Map<String,String> errorMap ;
}
