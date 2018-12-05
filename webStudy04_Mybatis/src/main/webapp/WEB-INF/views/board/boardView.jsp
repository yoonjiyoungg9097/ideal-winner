<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	/* function paging(page){
		$.ajax({
			url:"${pageContext.request.contextPath}/reply/replyList.do",
			data:{
				bo_no:
			},
			dataType:"",
			success:function(resp){
				var
				if(resp.dataList){
					$.each(resp.dataList, function(idx,reply){
						html
					}					
				}else{
					html +="<tr><td colspan='4']데이터가 없습니다</td></tr>";
				}
				pagingArea.html(resp.pagingHTML);
				listBody.html(html);
			}, 
			error:function(){
				console.log(resp.status);
			}
		});
	}
	$(function(){
		pagingArea = $("#pagingArea");
		listBody = $("#listBody");
		paging(1);
	}); */
</script>
</head>
<body>
	<table>
		<tr>
			<th>글번호</th>
			<td>${board.bo_no}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${board.bo_writer}</td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td>${board.bo_pass}</td>
		</tr>
		<tr>
			<th>IP</th>
			<td>${board.bo_ip}</td>
		</tr>
		<tr>
			<th>이메일</th>
			<td>${board.bo_mail}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${board.bo_title}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${board.bo_content}</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${board.bo_date}</td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${board.bo_hit}</td>
		</tr>
		<tr>
			<th>추천수</th>
			<td>${board.bo_rcmd}</td>
		</tr>
	</table>
	
	<table>
		<thead>
			<tr>
				<th>댓글 작성자</th>
				<th>댓글 작성 IP</th>
				<th>댓글 내용</th>
				<th>댓글 작성일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${board.replyList }" var="replyList">
				<tr>
					<td>${replyList.rep_writer }</td>
					<td>${replyList.rep_ip }</td>
					<td>${replyList.rep_content }</td>
					<td>${replyList.rep_date }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>