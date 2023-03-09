/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

//0 현재 로그인한 사용자 아이디
	let priciplaId = $("#principalId").val() ; // 자바스크립트로 값가져오기
	//alert("사용자 아이디 번호 "+priciplaId) ;

// (1) 스토리 로드하기
let page = 0; // 페이징을 위한
function storyLoad() {
	$.ajax({

		url:'/api/image?page='+page ,
		dataType:"json"  // 시 마퍼ㅏㅣ 허ㅡㅎㄹㅇㄹ하ㅣㅗㅜㅇ
	}).done(res=>{
		console.log(res) ;


		res.data.content.forEach((image)=>{ // image 갯수 만큼 // PAGE로 받아서 , content안에 있어
			let stroyItem = getStoryItem(image) ;
			$("#storyList").append(stroyItem);
			//console.log(stroyItem);
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
		'<button>\n' ;
		if(image.likesState){ // 좋아요 액티브
			item+= '<i class="fas fa-heart active" id="storyLikeIcon-'+ image.id+'" onclick="toggleLike('+ image.id+')"></i>\n' ;

		}else{
			item+= '<i class="far fa-heart" id="storyLikeIcon-'+ image.id+'" onclick="toggleLike('+ image.id+')"></i>\n' ;


		}

 		item +='</button>\n' +
		'</div>\n' +
		'\n' +
		'<span class="like"><b id="storyLikeCount-'+image.id+'">'+image.likeCount+'</b>likes</span>\n' +
		'\n' +
		'<div class="sl__item__contents__content">\n' +
		'<p>'+ image.caption+'</p>\n' +
		'</div>\n' +
		'\n' +
		'<div id="storyCommentList-'+image.id+'">\n' ;

		image.comments.forEach((comment)=>{
			item+=
			`<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
			<p>
				<b>${comment.user.username}:</b> ${comment.content}</p>  
			 </p>`;
			if(priciplaId == comment.user.id) { // 댓글쓴 사람이 나면 x보임
				item += `<button onclick="deleteComment(${comment.id})">
					 <i class="fas fa-times"></i>
				</button>`;
			}
			item+=` </div>`;

		});

		
		item +=
		'</div>\n' +
		'<div class="sl__item__input">\n' +
		'<input type="text" placeholder="댓글 달기..." id="storyCommentInput-'+image.id+'" />\n' +
		'<button type="button" onClick="addComment('+image.id +')">게시</button>\n' +
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
	//console.log(checkNum);
	if(checkNum <1 && checkNum>-1){
		page++ ;
		storyLoad();

	}
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let name ="storyLikeIcon-"+imageId ;
	//let likeIcon = $('#storyLikeIcon-${imageId}');  // '백팁 안에 $ 먹음
	let likeIcon = $('#'+name);  // '백팁 안에 $ 먹음
	if (likeIcon.hasClass("far")) {  // 좋아요 하겠다.
		// 클래스가 far라는 것은 빈껍때기 , fas 빨간 하트임

		$.ajax({
			type:"post",
			url:'api/image/'+imageId+'/likes',
			dataType:"json"
		}).done(res=>{

			let likeCountStr = $('#storyLikeCount-'+imageId).text() ;
			let likeCount = Number(likeCountStr) +1 ; // Number로 숫자 케스팅
			console.log("좋아요 카운트",likeCount) ;
			$('#storyLikeCount-'+imageId).text(likeCount) ; // text에 숫자 넣기


			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error =>{
			console.log("오류",error) ;
		});


	} else { // 좋아요 취소 하겠다.

		$.ajax({
			type:"delete",
			url:'api/image/'+imageId+'/likes',
			dataType:"json"
		}).done(res=>{
			let likeCountStr = $('#storyLikeCount-'+imageId).text() ;
			let likeCount = Number(likeCountStr) -1 ; // Number로 숫자 케스팅
			console.log("좋아요 취소 후  카운트",likeCount) ;
			$('#storyLikeCount-'+imageId).text(likeCount) ; // text에 숫자 넣기



			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");


		}).fail(error =>{
			console.log("오류",error) ;
		});


	}
}

// (4) 댓글쓰기
function addComment(ImageId) {

	let commentInput = $('#storyCommentInput-'+ImageId);
	let commentList = $('#storyCommentList-'+ImageId);

	let data ={
		imageId: ImageId , // 이미지 아이디
		content: commentInput.val()
	}
	//console.log("첫번째 ",data);
	//console.log("두번쨰",JSON.stringify(data)); 통신하기위해 바꿔감



	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	$.ajax({
		type: 'post',
		url: '/api/comment',
		data:JSON.stringify(data) , //자바스크립트 data를 날림
		contentType:"application/json;charset=utf-8" ,
		dataType:"json"

	}).done(res=>{
		console.log("성공" , res) ;

		let comment =res.data ;
		let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
			    <p>
			      <b>${comment.user.username}</b>
			   		${comment.content}
			    </p>
			    <button onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>
			  </div>
			`;
		commentList.prepend(content);

	}).fail(error=>{
		console.log("실패" , error) ;

	});
	commentInput.val(""); // 인풋 필드 깨끗하게 비워줌


}

// (5) 댓글 삭제
function deleteComment(commentId) {
	$.ajax({
		type:"delete",
		url:`/api/comment/${commentId}`,
		datatype: 'json' 

	}).done(res=>{
		console.log("성공",res);
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(error=>{
		console.log("오류",error);

	});

}







