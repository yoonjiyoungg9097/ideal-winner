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
<!-- 파일을 업로드 할때 <form>태그에서 enctype="multipart/form-data"라는 애트리뷰트를 반드시 써야 한다. -->
<!-- 그렇게 하지 않으면 웹 서버로 데이터를 넘길때 파일의 경로명만 전송되고 파일 내용이 전송되지 않기 때문이다. -->
<!-- 파일이나 이미지를 서버로 전송할 경우 이 방식을 사용한다. -->

<%-- <c:url> 태그는 URL에 자동으로 Context Path 를 붙여주는 일을 합니다. 컨텍스트를 변경하더라도 URL을 수정할 필요가 없게 되는 것입니다. --%>
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
<c:remove var="fileVO" scope="session"/><!-- 왜 session스코프 영역에 저장하는지???? -->
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






