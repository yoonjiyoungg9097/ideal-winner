<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login/loginForm.jsp</title>
<script type="text/javascript">
	<c:if test="${not empty message}">
		alert("${message}");
		<c:remove var="message" scope="session"/>
	</c:if>
	
</script>
</head>
<body>
	<!-- 모든 인증처리는 post방식 get방식은 기본적으로 해킹된 정보라고 인식한다 -->
	<form action="<c:url value='/login/loginCheck.do'/>" method="post"> <!-- 이 주소는 브라우저가 사용 클라이언트 사이드 그래서 contextpath가 필요로 하다 -->
		<ul>
			<li>
				<c:set var="savedId" value="${cookie['idCookie']['value'] }"></c:set>
				아이디 : <input type="text" name="mem_id" value="${savedId }"/>
				<label>
				<input type="checkbox" name="idChecked" value="idSaved" ${not empty savedId?"checked":"" } />아이디 기억하기
				</label>
			</li>
			<li>
				비밀번호 : <input type="password" name="mem_pass" value="" />
				<input type="submit" value="로그인" />
			</li>
		</ul>
	</form>
</body>
</html>