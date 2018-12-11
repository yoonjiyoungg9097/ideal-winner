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
<c:if test="${not empty message }">
	<script type="text/javascript">
		alert("${message}");
	</script>
</c:if>
<body>
	<form id="boardForm" method="post" enctype="multipart/form-data">
	<table>
		<tr>
			<th colspan="1">제목</th>
			<td colspan="3">
				<input type="text" name="bo_title" value="${board.bo_title}" size="20">
			</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td >
				<input type="text" name="bo_writer" value="${board.bo_writer}">
			<span><strong>비밀번호</strong></span>
				<input type="text" name="bo_pass" value="${board.bo_pass}">
			</td>
			
		</tr>
		<tr>
			<th colspan="1">이메일</th>
			<td colspan="3">
				<input type="text" name="bo_mail" value="${board.bo_mail}">
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div>
				<textarea rows="30" cols="80" name="bo_content" id="bo_content" value="${board.bo_content}"></textarea>
				</div>
			</td>
		</tr>
		
		<tr>
			<th>기존파일</th>
			<td>
				<c:if test="${not empty board.pdsList }">
				<c:forEach items="${board.pdsList }" var="pds" varStatus="vs">
					<ul>
						<li>${pds.pds_filename }</li><span>[삭제]</span>
					</ul>
				</c:forEach>
				</c:if>
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
				<input type="hidden" value="${board.bo_no }" name="bo_no">
				<input type="hidden" value="${param.parent }" name="bo_parent"/>
			</td>
		</tr>
	</table>
	</form>
	<script type="text/javascript">
	var boardForm = $("#boardForm");
	var inputTag = "<input type='text' name='delFiles' value='%v'/>";
	$(".pdsDelete").on("click", function(){
		$(this).parent().hide();
		var regex = /span_(\d+)/ig;
		var pds_no = regex.exec($(this).prop("id"))[1];
		boardForm.append(inputTag.replace("%v", pds_no));s
	});
	
	 CKEDITOR.replace('bo_content', {

	      extraAllowedContent: 'h3{clear};h2{line-height};h2 h3{margin-left,margin-top}',

	      // Adding drag and drop image upload.
	      extraPlugins: 'uploadimage',
	      uploadUrl: '${pageContext.request.contextPath}/board/uploadImage.do',

	      // Configure your file manager integration. This example uses CKFinder 3 for PHP.
	      filebrowserImageUploadUrl: '${pageContext.request.contextPath}/board/uploadImage.do',

	      height: 560,

	      removeDialogTabs: 'image:advanced;link:advanced'
	    });
	</script>
</body>
</html>