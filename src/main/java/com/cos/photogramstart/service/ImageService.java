package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.Image.Image;
import com.cos.photogramstart.domain.Image.ImageRepository;
import com.cos.photogramstart.web.dto.ImageUploadDto.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository ;
    @Value("${file.path}") // yml의 file에서 정보 받아옴
    private String uploadFolder;

    @Transactional(readOnly = true)
    // 영속성 컨텍스트에서 변경감지를 해서 , 더티 체킹  , flush(반영) x
    public Page<Image> 이미지스토리(int princalId , Pageable pageable){
        Page<Image> images = imageRepository.mStory(princalId , pageable) ;
        return images;
    }
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
