/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 구독자 정보 모달에서 구독하기, 구독취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (8) 구독자 정보 모달 닫기

 구독 정보는 나의 구독 정보가 나온다.
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(toUserId, obj) { //페이지userid받기
// 누가 formUSER id , 누구를 touserid
	// to가 form을
//function toggleSubscribe( obj) {
	// this의 이벤트 정보를 받음
	if ($(obj).text() === "구독취소") {
		//
		$.ajax({
			type:"delete",
			url:"/api/subscribe/"+toUserId,
			dataType:"json"

		}).done(res=>{ // 정상적으로 끝나

			$(obj).text("구독하기");
			$(obj).toggleClass("blue");
		}).fail(error=>{ // 오류 뜨면
			console.log("구독취소실패",error);
		});

	} else {
		$.ajax({
			type:"post",
			url:"/api/subscribe/"+toUserId,
			dataType:"json"

		}).done(res=>{ // 정상적으로 끝나

			$(obj).text("구독취소");
			$(obj).toggleClass("blue");
		}).fail(error=>{ // 오류 뜨면
			console.log("구독하기실패",error);
		});


	}
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(pageUserId) {
	//alert(pageUserId) ;
	$(".modal-subscribe").css("display", "flex"); // modal-subscribe는 화면에 보여줌
	$.ajax({
		url:'/api/user/'+pageUserId+'/subsbcribe',
		datatype: "json"
	}).done(res=>{
		console.log(res);

		res.data.forEach((u)=>{
 			let  item = getSubscribeModalItem(u);
			console.log(item) ;
		 	$("#subscribeModalList").append(item);

		});
	}).fail(error=>{
		console.log("구독정보 불러오기",error );

	});


}

function getSubscribeModalItem(u) {
	let item = '<div class="subscribe__item" id="subscribeModalItem-'+u.id+'">\n' +
		'<div class="subscribe__img">\n' +
		'<img src="/upload/'+u.profileImageUrl+'" onerror="this.src=\'/images/person.jpeg\'"/>\n' +
		'</div>\n' +
		'<div class="subscribe__text">\n' +
		'<h2>'+u.username+'</h2>\n' +
		'</div>\n' +
		'<div class="subscribe__btn">' ;

		if(!u.equalUserState){ // 동일유저가 아닐때 버튼이 만들어져야함
			if(u.subscribeState){ // 구독한 상태
				item += '<button className="cta blue" onClick="toggleSubscribe('+u.id+',this)">구독취소</button>'
			}else{ // 구독안한 상태
				item+='<button className="cta" onClick="toggleSubscribe('+u.id+',this)">구독하기</button>'
			}
		}

		 item += ' </div>\n' +
				'</div>\n' ;

		console.log(u.username) ;



	return item ;
}


// // (3) 구독자 정보 모달에서 구독하기, 구독취소
// function toggleSubscribeModal(obj) {
// 	if ($(obj).text() === "구독취소") {
// 		$(obj).text("구독하기");
// 		$(obj).toggleClass("blue");
// 	} else {
// 		$(obj).text("구독취소");
// 		$(obj).toggleClass("blue");
// 	}
// }

// (3) 유저 프로파일 사진 변경 (완)
function profileImageUpload(  pageUserId , principalId) { // 되면 userProfileImageInput이 강제로 클릭이됨

	console.log("pageId" ,pageUserId );
	console.log("principalId" ,principalId );

	if(pageUserId != principalId){
		alert("프로필 사진을 수정할 수 없는 유저입니다. ")
		return ; // 같지 않으면 밑코드 실행 X
	}

	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}
		//  userProfileIamgeForm 안에 있는 태그 전송 , 서버에 이미지 전송!!
		let profileImageForm = $("#userProfileImageForm")[0];
		console.log(profileImageForm) ;

		//form데이터를 전송하려면 formData 객체로 받는다.
		// FormData 객체를 이용하면 form 태그의 필드와 그 값을 나타내느 일련의 key/value 쌍을 담는다.
		let formData = new FormData(profileImageForm) ;
		$.ajax({
			type:"put",
			url:'/api/user/'+principalId+'/profileImageUrl',
			data:formData,
			contentType:false , // 필수 x-ww-form-urlencode 파싱되는 것 방지
			processData:false ,// 필수 contentType을 false 줬을때 쿼리 스트링으로 가는 거 방지
			enctype:"multipart/form-data", // form태그에 enctype넣거나 여기에 넣거나
			dataType:"json"

		}).done(res =>{
			console.log("ajax 성공!") ;
			// 사진 전송 성공시 이미지 변경
			let reader = new FileReader();
			reader.onload = (e) => {
				$("#userProfileImage").attr("src", e.target.result);

			}
			reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.

		}).fail(error=>{
			console.log("오류입니다.",error) ;

		});



	});
}


// (4) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (7) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}






