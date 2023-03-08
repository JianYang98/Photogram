// (1) 회원정보 수정
function update(userId , event) {
    event.preventDefault() // 폼태크 액션을 막기
    console.log(userId) ;
    let data =$("#profileUpdate").serialize() ;
    //serialize은 데이터를 key, value 형태로 가져온다.
    console.log(data) ;

    $.ajax({
        type:"put",
        url:'/api/user/'+userId,
        data :data ,
        contentType:"application/x-www-form-urlencoded;charset=utf-8",
        dataType:"json"

    }).done(res=>{ // HttpSatus 상태코드가 200번때
        console.log("updata 성공" ,res) ;
        //location.href='/user/'+userId ;

    }).fail(error=>{ // HttpSatus 상태코드가 200대가 아닐대
        //console.log("update 실패 " , error.responseJSON.data.name);
        if(error.data == null){
            alert(error.responseJSON.message) ; // eorrorMap으로 오지 않는 경우 data null
        }else{
            alert(JSON.stringify(  error.responseJSON.data.name) );
            //  JSON object를 문자열로 변경해줌 JSON.stringify
        }

    });

}