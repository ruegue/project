<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page import="java.net.URLDecoder"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
   <link rel="stylesheet" href="<c:url value='/css/register.css'/>">
</head>
<body>
    <div class="intro_info">
        <div class="intro_name">
            <h3><a href="<c:url value='/'/>">서울 축구모임</a></h3>
        </div>
        <div class="intro_login">
            <a class="login" href="<c:url value='/login/login'/>">로그인</a>
            ....
            <a class="join" href="<c:url value='/register/add'/>">회원가입</a>
        </div>
    </div>

    <div class="container">
        <div class="headMessage">
            <h2>서울 아마추어 축구인</h2>
            <h2>회원가입</h2>
        </div>
   <form:form class="form" modelAttribute="user">
    <div id="msg" class="msg"><form:errors path="id"/></div>  
    <label for="">아이디</label>
    <input class="input-field" type="text" name="id" placeholder="8~12자리의 영대소문자와 숫자 조합">
    <label for="">비밀번호</label>
    <input class="input-field" type="text" name="pwd" placeholder="8~12자리의 영대소문자와 숫자 조합">
    <label for="">이름</label>
    <input class="input-field" type="text" name="name" placeholder="홍길동">
    <label for="">이메일</label>
    <input class="input-field" type="text" name="email" placeholder="example@fastcampus.co.kr"> 
    <label for="">생일</label>
    <input class="input-field" type="text" name="birth" placeholder="2020-12-31">
    <label for="">전화번호</label>
    <input class="input-field" type="text" name="phone" placeholder="010-1111-1111">
    <button>회원 가입</button>
  </form:form> 
    </div>
    <script>
        function formCheck(frm) {
             let msg ='';
             if(frm.id.value.length<3) {
                 setMessage('id의 길이는 3이상이어야 합니다.', frm.id);
                 return false;
             }
            return true;
        }
        function setMessage(msg, element){
             document.getElementById("msg").innerHTML = `<i class="fa fa-exclamation-circle"> ${'${msg}'}</i>`;
             if(element) {
                 element.select();
             }
        }
    </script>
</body>
</html>