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
    <class="intro">
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
        <div class="event_image">         
        </div>
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
    <script>
    	$(document).ready(function() {
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

