<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form id="listForm">
	<table>
		<thead>
			<tr>
				<th>방명록 글번호</th>
				<th>작성자</th>
				<th>내용</th>
				<th>작성일</th>
			</tr>
		</thead>
		
		<tbody id="listBody">
			<c:set var="boardBookList" value="${pagingVO.dataList }"></c:set>
			<c:if test="${not empty boardBookList }">
				<c:forEach items="${boardBookList }" var="boardBook">
					<tr>
						<td>${boardBook.bo_no }</td>
						<td>${boardBook.bo_writer }</td>
						<td>${boardBook.bo_content }</td>
						<td>${boardBook.bo_date }</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty boardBookList }">
				<tr>
					<td colspan="4">조건에 맞는 방명록이 없습니다 ㅜㅠㅠ
				</tr>
			</c:if>
		</tbody>
		
		<tfoot>
			<tr>
				<td colspan="4">
				
				<nav aria-label="Page navigation example" id="pagingArea">
                   ${pagingVO.pagingHTML} 
               </nav>
				</td>
			</tr>
		</tfoot>
</body>
</form>
</html>