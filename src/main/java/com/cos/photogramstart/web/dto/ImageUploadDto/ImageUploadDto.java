package com.cos.photogramstart.web.dto.ImageUploadDto;

import com.cos.photogramstart.domain.Image.Image;
import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data

public class ImageUploadDto {  // 이미지 경로를 받을 dto 1)파일과 2)캡셥 받기

    private MultipartFile file ;
    private String caption ;

    public Image toEntity(String postImageUrl , User user ) {
        return Image.builder()
                .caption(caption)
                .postImageUrl(postImageUrl)
                .user(user)  // 어떤 유저가 insert했는지 정보 괘중요
                .build();
    }
}
