<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
	function ${pagingVO.funcName}(page){
		$("[name='searchForm']").find("[name='page']").val(page);
		$("[name='searchForm']").submit();
	}
	$(function(){
	var url="${pageContext.request.contextPath}/boardBook/boardBookInsert.do";
	$("[name='searchForm']").on("submit", function(event){
		alert("dddd");
		event.preventDefault();
		var data = $(this).serialize();
		
		$.ajax({
			method : "post",
			data : data,
			dataType : "json",
			success : function(resp) {
				var boardList = resp.dataList;
				var html = "";
				if(boardList){
					$.each(boardList, function(idx, board){
						html+="<tr id='TR_"+board.bo_no+"'>";
						html+="<td>"+board.bo_no+"</td>";
						html+="<td>"+board.bo_writer+"</td>";
						html+="<td>"+board.bo_content+"</td>";
						html+="<td>"+board.bo_date+"</td>";
						html+="<td><span data-toggle='modal' class='DelBtn' id='delBoardbook'>[삭제]</span><td>";
						html+="<td><span data-toggle='modal' class='DelBtn' id='upBoardbook'>[수정]</span><td>";
					});
				}else{
					html += "<tr><td colspan='7'>방명록이 없습니다 </td></tr>";
				}
				$("#listBody").html(html);
				$("#pagingArea").html(resp.pagingHTML);
				$("[name='page']").val("");
			},
			error : function(resp) {

			}
		});
	});
	
	$("[name=boardForm]").on("submit", function(event){
		alert("insert");
		event.preventDefault();
		var data = $(this).serialize();
		
		$.ajax({
			url :url ,
			method : "post",
			data : data,
			dataType : "json",
			success : function(resp) {
				url="${pageContext.request.contextPath}/boardBook/boardBookInsert.do";
				$("[name='searchForm']").submit();
			}
			
		});
	});
	$("#listBody").delegate("#delBoardbook","click", function(){
		var trId=$(this).closest("tr").prop("id");
		var bo_no = trId.substring(trId.indexOf("_")+1);
		alert(bo_no);
		$("#bo_no").val(bo_no);
		var bo_pw = prompt("비밀번호를 입력해주세요");
		$("#bo_pass").val(bo_pw);
		$("[name='passForm']").submit();
		
	});
	
	$("[name=passForm]").on("submit", function(event){
		event.preventDefault();
		var data = $(this).serialize();
		
		$.ajax({
			url :"${pageContext.request.contextPath }/boardBook/boardBookDelete.do",
			method : "post",
			data : data,
			dataType : "json",
			success : function(resp) {
				alert("삭제성공!!");
			},
			error : function(resp) {

			}
		});
			${pagingVO.funcName}(1);
	});
	
	$("#listBody").delegate("#upBoardbook", "click", function(){
		var trId=$(this).closest("tr").prop("id");
		var bo_no = trId.substring(trId.indexOf("_")+1);
		alert(bo_no);
		$("[name=boardForm]").find("[name=bo_no]").val(bo_no);
		alert($("[name=boardForm]").find("[name=bo_no]").val())
		var writer = $(this).closest("tr").find(".writer").text();
		$("#bo_writer").val(writer);
		var content = $(this).closest("tr").find(".content").text();
		$("#bo_content").val(content);
		$("[name=boardForm]").find("#bo_content").text(content);
		url="${pageContext.request.contextPath}/boardBook/boardUpdate.do";
		
	});
	
}); 
	
</script>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>방명록번호</th>
				<th>작성자</th>
				<th>내용</th>
				<th>작성일</th>
			</tr>
		</thead>

		<tbody id="listBody">
			<c:set var="boardBookList" value="${pagingVO.dataList }"></c:set>
			<c:if test="${not empty boardBookList }">
				<c:forEach items="${boardBookList }" var="boardBook">
					<tr id="TR_${boardBook.bo_no }">
						<td>${boardBook.bo_no }</td>
						<td class="writer">${boardBook.bo_writer }</td>
						<td class="content">${boardBook.bo_content }</td>
						<td>${boardBook.bo_date }&nbsp;&nbsp;<span
							data-toggle='modal' class='DelBtn' id="delBoardbook">[삭제]</span>&nbsp;&nbsp;<span
							data-toggle='modal' class='DelBtn' id="upBoardbook">[수정]</span></td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty boardBookList }">
				<tr>
					<td colspan="4">조건에 맞는 방명록이 없습니다 ㅜㅠㅠ
				</tr>
			</c:if>
		</tbody>

		<tfoot>
			<tr>
				<td colspan="4">
					<nav aria-label="Page navigation example" id="pagingArea">
						${pagingVO.pagingHTML}</nav>
				</td>
			</tr>
		</tfoot>

	</table>
	<form name="searchForm" method="post">
		<input type="hidden" name="page">
	</form>

	<form
		action="${pageContext.request.contextPath }/boardBook/boardBookDelete.do"
		name="passForm" method="post">
		<input type="hidden" name="bo_no" id="bo_no" /> <input type="hidden"
			name="bo_pass" id="bo_pass" />
	</form>

	<form
		action="${pageContext.request.contextPath }/boardBook/boardBookUpdate.do"
		name="updateForm" method="post">
		<input type="hidden" name="bo_no" id="bo_no" /> <input type="hidden"
			name="bo_pass" id="bo_pass" /> <input type="hidden"
			name="bo_content" id="bo_content" />
	</form>

	<form name="boardForm" method="post"
		action="${pageContext.request.contextPath}/boardBook/boardBookInsert.do">
		<table>
			<tr>
				<th>작성자</th>
				<td><input type="text" name="bo_writer"
					value="${boardBook.bo_writer }" id="bo_writer" />
					<input type="hidden" name="bo_no"> </td>
			</tr>

			<tr>
				<th>비밀번호</th>
				<td><input type="text" name="bo_pass"
					value="${boardBook.bo_pass }" /></td>
			</tr>

			<tr>
				<th>방명록 내용</th>
				<td><textarea rows="10" cols="50" id="bo_content"
						name="bo_content"></textarea></td>
			</tr>

			<tr>
				<td colspan="2"><input type="hidden" name="bo_ip"
					value="${pageContext.request.remoteAddr }"> <input
					type="submit" value="전송" /> <input type="reset" value="취소" /></td>
			</tr>
		</table>
	</form>
</body>
</html>