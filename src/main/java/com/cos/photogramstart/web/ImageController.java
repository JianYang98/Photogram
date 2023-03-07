package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.Image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.ImageUploadDto.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService ;
    @GetMapping({"/","/image/story"})
    public String story(){
        return "image/story" ;
    }

    @GetMapping("/image/popular")
    public String popular(Model model){
        // api는 데이터 리턴하는 서버!
        //ajax 쓸떄만 api씀!
        List<Image> images = imageService.인기사진();
        model.addAttribute("images",images);


        return "image/popular" ;
    }
    @GetMapping("/image/upload")
    public String upload(){
        return "image/upload" ;
    }


    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto , @AuthenticationPrincipal PrincipalDetails principalDetails){

        if(imageUploadDto.getFile().isEmpty()){
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.",null) ;
            //System.out.println("이미지가 첨부되디 않았습니다." );

        }
        //서비스 호출
        imageService.사진업로드(imageUploadDto,principalDetails);
        return "redirect:/user/"+principalDetails.getUser().getId(); // 업로드 후 유저 프로필로 가기

    }
}
