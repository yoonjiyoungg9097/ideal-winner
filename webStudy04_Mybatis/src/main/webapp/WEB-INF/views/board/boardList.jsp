<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<%=request.getContextPath() %>/css/board.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
	function ${pagingVO.funcName}(page){
		document.searchForm.page.value = page;
		document.searchForm.submit();
	}
	$(function(){
		
	$("#output1").on("click","tr", function(){
		var what = $(this).find("td:nth-child(2)").text();
		location.href="${pageContext.request.contextPath}/board/boardView.do?what="+what;
		alert(what);
	});
	})
</script>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>순번</th>
				<th>글번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
				<th>추천수</th>
			</tr>
		</thead>
		
		<tbody id="output1">
			<c:set var="boardList" value="${pagingVO.dataList }"></c:set>
			<c:if test="${not empty boardList }">
			<c:forEach items="${boardList}" var="board">
			<tr>
				<td>${board.rnum }</td>
				<td>${board.bo_no }</td>
				<td>${board.bo_title }</td>
				<td>${board.bo_writer }</td>
				<td>${board.bo_date }</td>
				<td>${board.bo_hit }</td>
				<td>${board.bo_rcmd }</td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty boardList }">
				게시판 목록 0
			</c:if>
		</tbody>
		
		<tfoot>
			<tr>
				<td colspan="7">
					${pagingVO.pagingHTML }
				</td>
			</tr>
		</tfoot>
	</table>
	
	<form name="searchForm">
		<select name="searchType">
			<option value="writer">작성자</option>
			<option value="title">제목</option>
			<option value="content">내용</option>
			<option value="all">전체</option>
		</select>
		<input type="text" name="searchWord">
		<input type="hidden" name="page">
		<input type="submit" value="검색" >
		<input type="button" value="새글쓰기" onclick="location.href='${pageContext.request.contextPath}/board/boardInsert.do';"/>
	</form>
	
</body>
</html>