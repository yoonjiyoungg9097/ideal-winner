<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>13/fileForm.jsp</title>
</head>
<body>
<h4>파일 업로드 양식</h4><%-- 메소드를 지정해주지 않으면 R.L주소뒤에 문자열로--%>
<!-- 클라이언트로부터 이미지를 업로드 받고, 다시 현재 페이지로 이동해서 -->
<!-- 하단의 div 태그내에 해당 이미지가 출력되도록 하기 -->
<!-- 단, 이미지 파일의 메타데이터들도 함께 출력되도록 -->
<form action='<c:url value='/upload_2'></c:url>' method="post" enctype="multipart/form-data"><%--multipart는 body영역을 나누겠다는 의미 form태그안에 input태그 개수만큼 body영역이 생성된다  --%>
	<ul>
		<li>
			<input type="text" name="uploader"/>
		</li>
		<li>
			업로드할 파일 : <input type="file" name="uploadFile"/>
			<input type="submit" value="업로드"/>
		</li>
	</ul>
</form>
<c:set var="fileVO" value="${sessionScope.fileVO }"/>
<c:remove var="fileVO" scope="session"/>
<c:if test="${not empty fileVO }">
<div>
	<p>원본파일명: ${fileVO.originalFilename }</p>
	<p>파일 크기: ${fileVO.filesize }</p>
	<p>파일 종류: ${fileVO.filemime }</p>
	<p>업로드: ${fileVO.uploader }</p>
	<img src='<c:url value='${fileVO.saveFileUrl }'/>' />
</div>
</c:if>
</body>
</html>






