package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.Image.Image;
import com.cos.photogramstart.domain.Image.ImageRepository;
import com.cos.photogramstart.web.dto.ImageUploadDto.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository ;
    @Value("${file.path}") // yml의 file에서 정보 받아옴
    private String uploadFolder;

    public void 사진업로드(ImageUploadDto imageUploadDto , @AuthenticationPrincipal PrincipalDetails principalDetails){ // 이미지 업로드 Dto , 세션
        UUID uuid = UUID.randomUUID() ;// UUID

        String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename() ; // 실제 파일 내일 가져감 ex)1.jpg들어감
        System.out.println("이미지파일이름" + imageFileName);

        Path imageFilePath = Paths.get(uploadFolder+imageFileName) ;  // yml의 폴더 뿅
        // 경로 + 파일 명

        // 통신 I/ O -> 예외가 발생 할 수 있다.
        try {
            Files.write(imageFilePath,imageUploadDto.getFile().getBytes()) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        // image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName , principalDetails.getUser()) ; // 파일 이름 쑉 들어감
        Image imageEntity = imageRepository.save(image) ;
        //System.out.println("imageEntity :: " + imageEntity);
    }



}
