/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

// (1) 스토리 로드하기
let page = 0; // 페이징을 위한
function storyLoad() {
	$.ajax({
		url:'/api/image?page='+page ,
		dataType:"json"
	}).done(res=>{
		console.log(res) ;

		res.data.content.forEach((image)=>{ // image 갯수 만큼 // PAGE로 받아서 , content안에 있어
			let stroyItem = getStoryItem(image) ;
			$("#storyList").append(stroyItem);
		});

	}).fail(error =>{
		console.log("에러" , error) ;

	});


}
storyLoad();
function getStoryItem(image) {
	let item = '<div class="story-list__item">\n' +
		'<div class="sl__item__header">\n' +
		'<div>\n' +
		'<img class="profile-image" src="/upload/'+ image.user.profileImageUrl+'"  \n' +
		'onerror="this.src=\'/images/person.jpeg\'" />\n' +
		'</div>\n' +
		'<div>'+image.user.username+'</div>\n' +
		'</div>\n' +
		'\n' +
		'<div class="sl__item__img">\n' +
		'<img src="/upload/'+image.postImageUrl+'" />\n' +
		'</div>\n' +
		'\n' +
		'<div class="sl__item__contents">\n' +
		'<div class="sl__item__contents__icon">\n' +
		'\n' +
		'<button>\n' +
		'<i class="fas fa-heart active" id="storyLikeIcon-1" onclick="toggleLike()"></i>\n' +
		'</button>\n' +
		'</div>\n' +
		'\n' +
		'<span class="like"><b id="storyLikeCount-1">3 </b>likes</span>\n' +
		'\n' +
		'<div class="sl__item__contents__content">\n' +
		'<p>'+ image.caption+'</p>\n' +
		'</div>\n' +
		'\n' +
		'<div id="storyCommentList-1">\n' +
		'\n' +
		'<div class="sl__item__contents__comment" id="storyCommentItem-1"">\n' +
		'<p>\n' +
		'<b>Lovely :</b> 부럽습니다.\n' +
		'</p>\n' +
		'\n' +
		'<button>\n' +
		'<i class="fas fa-times"></i>\n' +
		'</button>\n' +
		'\n' +
		'</div>\n' +
		'\n' +
		'</div>\n' +
		'\n' +
		'<div class="sl__item__input">\n' +
		'<input type="text" placeholder="댓글 달기..." id="storyCommentInput-1" />\n' +
		'<button type="button" onClick="addComment()">게시</button>\n' +
		'</div>\n' +
		'\n' +
		'</div>\n' +
		'</div>';
	return item ;

}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
	//console.log("윈도우 scrollTop",$(window).scrollTop()) ;
	//console.log("문서의 높이",$(document).height()) ;
	//console.log("윈도우 높이",$(window).height()) ;
	let  checkNum = $(window).scrollTop() - ($(document).height()  -$(window).height() );
	console.log(checkNum);
	if(checkNum <1 && checkNum>-1){
		page++ ;
		storyLoad();

	}
});


// (3) 좋아요, 안좋아요
function toggleLike() {
	let likeIcon = $("#storyLikeIcon-1");
	if (likeIcon.hasClass("far")) {
		likeIcon.addClass("fas");
		likeIcon.addClass("active");
		likeIcon.removeClass("far");
	} else {
		likeIcon.removeClass("fas");
		likeIcon.removeClass("active");
		likeIcon.addClass("far");
	}
}

// (4) 댓글쓰기
function addComment() {

	let commentInput = $("#storyCommentInput-1");
	let commentList = $("#storyCommentList-1");

	let data = {
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-2""> 
			    <p>
			      <b>GilDong :</b>
			      댓글 샘플입니다.
			    </p>
			    <button><i class="fas fa-times"></i></button>
			  </div>
	`;
	commentList.prepend(content);
	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment() {

}







