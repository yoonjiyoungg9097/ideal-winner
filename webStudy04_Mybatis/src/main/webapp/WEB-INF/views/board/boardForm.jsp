<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cPath" value="${pageContext.request.contextPath }" scope="application"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${cPath}/js/ckeditor/ckeditor.js"></script>
</head>
<body>
	<form method="post" enctype="multipart/form-data">
	<table>
		<tr>
			<th>제목</th>
			<td>
				<input type="text" name="bo_title" value="${board.bo_title}">
			</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>
				<input type="text" name="bo_writer" value="${board.bo_writer}">
			</td>
			<th>비밀번호</th>
			<td>
				<input type="text" name="bo_pass" value="${board.bo_pass}">
			</td>
		</tr>
		<tr>
			<th>이메일</th>
			<td>
				<input type="text" name="bo_mail" value="${board.bo_mail}">
			</td>
		</tr>
		<tr>
			<td>
				<div>
				<textarea rows="30" cols="80" name="bo_content" id="bo_content" value="${board.bo_content}"></textarea>
				</div>
			</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
				<input type="file" name="bo_file">
				<input type="file" name="bo_file">
				<input type="file" name="bo_file">
			</td>
		</tr>
		
		<tr>
			<td colspan="2">
				<input type="submit" value="저장" onclick="location.href='${pageContext.request.contextPath}/board/boardInsert.do';"/>
				<input type="reset" value="취소">
				<input type="button" value="뒤로가기" onclick="history.back()">
				<input type="hidden" value="${pageContext.request.remoteAddr}" name="bo_ip" >
			</td>
		</tr>
	</table>
	</form>
	<script type="text/javascript">
	 	CKEDITOR.replace( 'bo_content' );
	</script>
</body>
</html>