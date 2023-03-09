package com.cos.photogramstart.service;


import com.cos.photogramstart.domain.Image.Image;
import com.cos.photogramstart.domain.Image.ImageRepository;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository ;
    private final UserRepository userRepository ;

    @Transactional
    public Comment 댓글쓰기(String content , int imageId, int userId){

//        UserRepository.findById(userId);
//        imageRepository.findById(imageId);

        //return commentRepository.mSave(content , imageId,userId);

        //Tip 가짜 객체 만들어서 id값만 담아서 insert 할 수 있다  , 가짜 객체오 안하면 고생좀함
        //대신 return 시에 image객체와 user객체는 id값만 가지고 있는 빈객체를 리턴한다.
        /*
        Image image = new Image() ;
        image.setId(imageId);

        User user = new User() ;
        user.setId(userId);
        */

        Image image = new Image() ;
        image.setId(imageId);

        User userEntity = userRepository.findById(userId).orElseThrow(()->{
            throw new CustomApiException("유저아이디를 찾을 수 없습니다. ") ;
        });

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(userEntity);

        return commentRepository.save(comment) ;
    }
    @Transactional
    public void 댓글삭제 (){

    }

}
