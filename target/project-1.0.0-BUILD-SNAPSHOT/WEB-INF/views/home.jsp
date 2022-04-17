<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="loginId" value="${sessionScope.id}"/>
<c:set var="loginOutLink" value="${loginId==null ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId==null ? '로그인' : '로그아웃'}"/>
<%@ page session="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link rel="stylesheet" href="<c:url value='/css/home.css'/>">
    <title>Document</title>
</head>
<body>
    <class="intro">
        <div class="intro_info">
            <div class="intro_name">
                <h3>서울 축구모임</h3>
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
        <a href="<c:url value='board/list'/>">커뮤니티</a>
    </div>
    <div id="commentList"></div>
</body>
</html>

