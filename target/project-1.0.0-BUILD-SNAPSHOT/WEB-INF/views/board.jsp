<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="true"%>
<c:set var="loginId" value="${sessionScope.id}"/>
<c:set var="loginOutLink" value="${loginId==null ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId==null ? '로그인' : '로그아웃'}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   	<link rel="stylesheet" href="<c:url value='/css/board.css'/>">
   	<script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <title>Document</title>
</head>
<body>
    <div class="intro">
        <div class="intro_info">
            <div class="intro_name">
                <h3><a href="<c:url value='/'/>">서울 축구모임</a></h3>
            </div>
            <div class="intro_login">
                <a class="login" href="<c:url value='${loginOutLink}'/>">${loginOut}</a>
                ....
                <a class="join" href="<c:url value='/register/add'/>">회원가입</a>
            </div>
        </div>
    </div>
        <div class="event_image">
        </div>
    <div class="icon-bar">
        <a class="active" href="<c:url value='/'/>">home</a>
        <a >팀원모집</a>
        <a href="#">용병모집</a>
        <a href="#">중고나라</a>
        <a href="<c:url value='/board/list'/>">커뮤니티</a>
    </div>
    <script>
    	let msg = "${msg}";
    	if(msg=="WRT_ERR") alert("게시물 등록에 실패하였습니다. 다시 시도해 주세요");
    	if(msg=="MOD_ERR") alert("게시물 수정에 실패하였습니다. 다시 시도해 주세요");
    </script>
    <div class="container">
        <h2 class="writing-header">게시판 ${mode=="new" ? " 글쓰기" : " 읽기"}</h2>
        <form id="form" class="frm" action="" method="post">
            <input type="hidden" name="bno" value="${boardDto.bno}">

            <input name="title" type="text" value="${boardDto.title}" placeholder="  제목을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><br>
            <textarea name="content" rows="20" placeholder=" 내용을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}>${boardDto.content}</textarea><br>

            <c:if test="${mode eq 'new'}">
                <button type="button" id="writeBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 등록</button>
            </c:if>
            <c:if test="${mode ne 'new'}">
                <button type="button" id="writeNewBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 글쓰기</button>
            </c:if>
            <c:if test="${boardDto.writer eq loginId}">
                <button type="button" id="modifyBtn" class="btn btn-modify"><i class="fa fa-edit"></i> 수정</button>
                <button type="button" id="removeBtn" class="btn btn-remove"><i class="fa fa-trash"></i> 삭제</button>
            </c:if>
            <button type="button" id="listBtn" class="btn btn-list"><i class="fa fa-bars"></i> 목록</button>
        </form>
    </div>
    <c:if test="${mode ne 'new'}">
        <div id="commentList">
        </div>
    <div class="paging-container">
    </div>
    <div id="comment-writebox">
        <div class="commenter commenter-writebox">${id}</div>
        <div class="comment-writebox-content">
            <textarea name="comment" id="" cols="30" rows="3" placeholder="댓글을 남겨보세요"></textarea>
        </div>
        <div id="comment-writebox-bottom">
            <div class="register-box">
                <a href="#" class="btn" id="btn-write-comment">등록</a>
            </div>
        </div>
    </div>
    <div id="reply-writebox">
        <div class="commenter commenter-writebox">${id}</div>
        <div class="reply-writebox-content">
            <textarea name="reComment" id="" cols="30" rows="3" placeholder="댓글을 남겨보세요"></textarea>
        </div>
        <div id="reply-writebox-bottom">
            <div class="register-box">
                <a href="#" class="btn" id="btn-write-reply">등록</a>
                <a href="#" class="btn" id="btn-cancel-reply">취소</a>
            </div>
        </div>
    </div>
    <div id="modalWin" class="modal">
        <!-- Modal content -->
        <div class="modal-content">
            <span class="close">&times;</span>
            <p>
            <h2> | 댓글 수정</h2>
            <div id="modify-writebox">
                <div class="commenter commenter-writebox"></div>
                <div class="modify-writebox-content">
                    <textarea name="modComment" id="" cols="30" rows="5" placeholder="댓글을 남겨보세요"></textarea>
                </div>
                <div id="modify-writebox-bottom">
                    <div class="register-box">
                        <a href="#" class="btn" id="btn-write-modify">등록</a>
                    </div>
                </div>
            </div>
            </p>
        </div>
    </div>
    </c:if>
    </body>
    <script>

        let page = 1;

        let showList = function (page) {
            $.ajax({
                type:'GET',       // 요청 메서드
                url: '/project/comments?bno='+ ${boardDto.bno} + '&page='+ page,  // 요청 URI
                success : function(result){
                    $("#commentList").html(toHtml(result.list));// 서버로부터 응답이 도착하면 호출될 함수
                    $(".paging-container").html(CmtHtml(result));// 서버로부터 응답이 도착하면 호출될 함수
                    $(".page").on("click", function(e){
                        let page = $(this).attr("href");
                        e.preventDefault();
                        showList(page)
                    });
                },
                // error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()
        }

        let toHtml = function (comments) {
            let tmp = "<ul>";

            comments.forEach(function (comment) {

                let addZero = function(value){
                    return value > 9 ? value : "0"+value;
                }

                let dateToString = function(comment) {
                    let date = new Date(comment.up_date);

                    let yyyy = date.getFullYear();
                    let mm = addZero(date.getMonth() + 1);
                    let dd = addZero(date.getDate());

                    let HH = addZero(date.getHours());
                    let MM = addZero(date.getMinutes());
                    let ss = addZero(date.getSeconds());

                    return yyyy+"."+mm+"."+dd+ " " + HH + ":" + MM + ":" + ss;
                }

                tmp += ' <li class="comment-item" data-cno='+comment.cno
                tmp += ' data-pcno=' + comment.pcno
                tmp += ' data-bno=' + comment.bno + '>'
                tmp += ' <div class="comment-area">'
                tmp += ' <div class="commenter">'+ comment.commenter + '</div>'
                tmp += ' <div class="comment-content">'+ comment.comment + '</div>'
                tmp += ' <div class="comment-bottom">'
                tmp += ' <span class="up-date">' + dateToString(comment) + '</span>'
                tmp += ' <a href="#" class="btn-write" data-cno='+ comment.cno + ' data-bno='+comment.bno+ ' data-pcno='+comment.pcno+'>답글쓰기</a>'
                tmp += ' <a href="#" class="btn-modify" data-cno='+ comment.cno + ' data-bno='+comment.bno+ ' data-pcno='+comment.pcno+'>수정</a>'
                tmp += ' <a href="#" class="btn-delete" data-cno='+ comment.cno + ' data-bno='+comment.bno+ ' data-pcno='+comment.pcno+'>삭제</a>'
                tmp += ' </div>'
                tmp += ' </div>'
                tmp += ' </li>'
            })
            return tmp + "</ul>";
        }

        let CmtHtml = function (cmt) {
           let str = "<div class='paging'>";

           if (cmt.showPrev){
               str += "<a class='page' href='"+(cmt.beginPage-1)+"'>이전</a>";
           }

           for(let i =cmt.beginPage ; i <= cmt.endPage ; i++){
               if (i==cmt.page){
                   str += "<a class='page paging-active' href='"+i+"'>"+i+"</a>";
               }else{
                   str += "<a class='page ' href='"+i+"'>"+i+"</a>";
               }
           }

            if (cmt.showNext){
                str += "<a class='page ' href='"+(cmt.endPage+1)+"'>다음</a>";
            }

           return str += "</div>";
        }

    	$(document).ready(function() {
            showList(page);

            $("#commentList").on("click" , ".btn-modify", function(e){
                let target = e.target;
                let cno = target.getAttribute("data-cno");
                let bno = target.getAttribute("data-bno");
                let pcno = target.getAttribute("data-pcno");
                let li = $("li[data-cno="+cno+"]");
                let commenter = $(".commenter", li).first().text();
                let comment = $(".comment-content", li).first().text();
                e.preventDefault();

                $("#modalWin .commenter").text(commenter);
                $("#modalWin textarea").text(comment);
                $("#btn-write-modify").attr("data-cno", cno);
                $("#btn-write-modify").attr("data-pcno", pcno);
                $("#btn-write-modify").attr("data-bno", bno);
                $("#modalWin").css("display","block");
            });

            $("#btn-write-modify").click(function(e){
                let cno = $(this).attr("data-cno");
                let comment = $("textarea[name=modComment]").val();
                e.preventDefault();

                if (comment.trim()==''){
                    alert("댓글을 입력해주세요");
                    $("textarea[name=modComment]").focus()
                    return;
                }

                $.ajax({
                    type:'PATCH',       // 요청 메서드
                    url: '/project/comments/'+cno,  // 요청 URI
                    headers : { "content-type": "application/json"}, // 요청 헤더
                    data : JSON.stringify({cno:cno, comment:comment}),
                    success : function(result){
                        alert(result);
                        showList(page);
                    },
                    error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
                }); // $.ajax()

                $("#modalWin").css("display","none");
            });

            $(".close").click(function(){
                $("#modalWin").css("display","none");
            });

            $("#commentList").on("click" , ".btn-write", function(e){
                let target = e.target;
                let cno = target.getAttribute("data-cno")

                $("#reply-writebox").appendTo($("li[data-cno="+cno+"]"));
                $("#reply-writebox").css("display", "block");
            });

            $("#btn-cancel-reply").click(function(e){
                $("#reply-writebox").css("display", "none");
            });

            $("#btn-write-reply").click(function(){
                let comment = $("textarea[name=reComment]").val();
                let pcno = $("#reply-writebox").parent().attr("data-pcno");

                if (comment.trim()==''){
                    alert("댓글을 입력해주세요");
                    $("textarea[name=reComment]").focus()
                    return;
                }

                $.ajax({
                    type:'POST',       // 요청 메서드
                    url: '/project/comments?bno='+${boardDto.bno},  // 요청 URI
                    headers : { "content-type": "application/json"}, // 요청 헤더
                    data : JSON.stringify({pcno:pcno, bno:${boardDto.bno}, comment:comment}),
                    success : function(result){
                        alert(result);
                        showList(page);
                    },
                    error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
                }); // $.ajax()

                $("#reply-writebox").css("display", "none");
                $("textarea[name=reComment]").val('');
                $("#reply-writebox").appendTo("body");
            });

            $("#btn-write-comment").click(function(){
                let comment = $("textarea[name=comment]").val();

                if (comment.trim()==''){
                    alert("댓글을 입력해주세요");
                    $("textarea[name=comment]").focus()
                    return;
                }

                $.ajax({
                    type:'POST',       // 요청 메서드
                    url: '/project/comments?bno='+ ${boardDto.bno},  // 요청 URI
                    headers : { "content-type": "application/json"}, // 요청 헤더
                    data : JSON.stringify({bno: ${boardDto.bno}, comment:comment}),
                    success : function(result){
                        alert(result);
                        showList(page);
                    },
                    error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
                }); // $.ajax()
            });

            $("#commentList").on("click" , ".btn-delete", function(e){
                let cno = $(this).attr("data-cno");
                let bno = $(this).attr("data-bno");
                let page = 1;
                e.preventDefault();

                $.ajax({
                    type:'DELETE',       // 요청 메서드
                    url: '/project/comments/' + cno + '?bno='+bno,  // 요청 URI
                    success : function(result){
                        alert(result);
                        showList(page);
                    },
                    error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
                }); // $.ajax()
            });

    		let formCheck = function () {
				if(form.title.value==""){
					alert("제목을 입력해 주세요.");
					form.title.focus();
					return false;
				}
				if(form.content.value==""){
					alert("내용을 입력해주세요");
					form.content.focus();
					return false;
				}
				return true;
			}

    		$("#removeBtn").on("click", function(){
                if(!confirm("정말로 삭제하시겠습니까?")) return;
                let form = $("#form");
                form.attr("action", "<c:url value='/board/remove${sc.queryString}'/>");
                form.attr("method", "post");
                form.submit();
              });

    		$("#modifyBtn").on("click", function(){
                let form = $("#form");
                let isReadonly = $("input[name=title]").attr('readonly');

                if(isReadonly=='readonly'){
                	$(".writing-header").html("게시판 수정");
                	$("input[name=title]").attr('readonly', false);
                	$("textarea").attr('readonly', false);
                	$("#modifyBtn").html("<i class='fa fa-pencil'></i> 등록");
                	return;
                }

                form.attr("action", "<c:url value='/board/modify${sc.queryString}'/>");
                form.attr("method", "post");
                if(formCheck())
                	form.submit();
             });

    		$("#writeBtn").on("click", function(){
                let form = $("#form");
                form.attr("action", "<c:url value='/board/write'/>");
                form.attr("method", "post");
                if(formCheck())
                	form.submit();
              });

    		$("#writeNewBtn").on("click", function(){
              location.href="<c:url value='/board/write'/>";
            });

    		$("#listBtn").on("click", function(){
                location.href="<c:url value='/board/list${sc.queryString}'/>";
            });
		});
    </script>
</body>
</html>

