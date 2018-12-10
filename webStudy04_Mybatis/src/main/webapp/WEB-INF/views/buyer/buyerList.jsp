<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@page import="kr.or.ddit.vo.PagingInfoVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
	PagingInfoVO pagingVO=(PagingInfoVO) request.getAttribute("pagingVO");
	
	List<BuyerVO>buyerList = (List<BuyerVO>)request.getAttribute("buyerList");//db에서 가져온 10명의 정보들
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>buyer/buyerList.jsp</title>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	crossorigin="anonymous"></script>
<script type="text/javascript">
	function ${pagingVO.funcName}(page){
		$("[name='searchForm']").find("[name='page']").val(page);
		$("[name='searchForm']").submit();
	}
	
	$(function(){
		//searchForm이름을 가진 form이 submit을 할때 그 이벤트를 가로채와서 그 이벤트 처리를 막는다.
		$("[name='searchForm']").on("submit", function(event){
			alert("아무거나")
			event.preventDefault();
			//searchForm안의 태그들을 직렬화해준다 this는 searchForm을 의미한다
			var data = $(this).serialize();
			$.ajax({
				data:data,
				dataType:"json",
				success:function(resp){
					var buyerList = resp.dataList;
					var html="";
					$.each(buyerList,function(idx,buyer){
					html+="<tr class='info'>";
					html+="<td>"+buyer.buyer_id +"</td>";
					html+="<td><a href='${pageContext.request.contextPath }/buyer/buyerView.do?who=${buyer.buyer_id }'>"+buyer.buyer_name+"</a></td>";
					html+="<td>"+buyer.buyer_add1 +"</td>";
					html+="<td>"+buyer.buyer_add2 +"</td>";
					html+="<td>"+buyer.buyer_comtel +"</td>";
					html+="<td>"+buyer.buyer_mail +"</td>";
					html+="</tr>";
					});
					
					$("#nav").html(resp.pagingHTML);
					$("#listBody").html(html);
					$("[name='page']").val("");
					
				},                                                                                                                                   
				error:function(resp){
					
				}
			});
		});
	});
</script>
</head>
<body>
	<h4>buyer 목록</h4>
	<input type="button" class="button" value="신규등록"
		onclick="location.href='${pageContext.request.contextPath}/buyer/buyerInsert.do'" />
	<table class="table">
		<thead class="thead-dark">
			<tr>
				<th>판매처아이디</th>
				<th>판매처명</th>
				<th>주소</th>
				<th>전화번호</th>
				<th>이메일</th>
			</tr>
		</thead>

		<tbody id="listBody">
			<c:set var="buyerList" value="${pagingVO.dataList }"></c:set>
			<c:if test="${not empty buyerList }">
				<c:forEach items="${buyerList }" var="buyer">
					<tr class='info'>
						<td>${buyer.buyer_id }</td>
						<td><a
							href="${pageContext.request.contextPath }/buyer/buyerView.do?who=${buyer.buyer_id }">${buyer.buyer_name}</a></td>
						<td>${buyer.buyer_add1 }</td>
						<td>${buyer.buyer_add2 }</td>
						<td>${buyer.buyer_comtel }</td>
						<td>${buyer.buyer_mail }</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty buyerList }">
			판매자 목록 0
		</c:if>
		</tbody>
		<tfoot>
			<tr>
				<td colspan='5'>
					<nav aria-label="Page navigation example" id="nav">
						${pagingVO.pagingHTML }</nav>
				</td>
			</tr>
		</tfoot>
	</table>
	<form name="searchForm">
		<input type="hidden" name="page"> <select name="searchType">
			<option value="all">전체</option>
			<option value="name">판매처명</option>
			<option value="address">주소</option>
		</select> <input type="text" name="searchWord" value="${pagingVO.searchWord }">
		<input type="submit" value="검색">
	</form>
</body>
</html>