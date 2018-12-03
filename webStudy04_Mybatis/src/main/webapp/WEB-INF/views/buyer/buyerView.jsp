<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://jqueryui.com/resources/demos/style.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	crossorigin="anonymous"></script>
<script type="text/javascript">
	$(function() {
<%String message = (String) request.getAttribute("message");
			if (StringUtils.isBlank(message)) {
				message = (String) session.getAttribute("message");
				session.removeAttribute("message");
			}
			if (StringUtils.isNotBlank(message)) {%>
	alert("${message}");
<%}%>
	// 	$("[type='date']").datePicker({
		// 			dateFormat : "yy-mm-dd"
		// 		});
		$("#delBtn").on('click', function() {
			document.delForm.submit();
		});
	});
</script>
<title>Insert title here</title>
</head>
<body>
	<jsp:useBean id="member" class="kr.or.ddit.vo.MemberVO" scope="request" />
	<jsp:useBean id="buyer" class="kr.or.ddit.vo.BuyerVO" scope="request" />
	<jsp:useBean id="errors" class="java.util.LinkedHashMap"
		scope="request" />
	<c:set var="mutable" value="false" />
	<c:if
		test='${not empty authMember and "ROLE_ADMIN" eq authMember.mem_auth }'>
		<c:set var="mutable" value="true" />
	</c:if>
	<c:if test="${mutable }">
		<form name="delForm" method="post"
			action="${pageContext.request.contextPath}/buyer/buyerDelete.do">
			<input type="hidden" name="buyer_id" value="${buyer.buyer_id}" />
		</form>
	</c:if>
	<h4>판매처정보 상세조회</h4>
	<table>
		<tr>
			<th>활동여부</th>
			<td>${buyer.buyer_delete }</td>
		</tr>
		<tr>
			<th>판매자아이디</th>
			<td>${buyer.buyer_id }</td>
		</tr>
		<tr>
			<th>판매처명</th>
			<td>${buyer.buyer_name }</td>
		</tr>
		<tr>
			<th>엘지유</th>
			<td>${buyer.buyer_lgu }</td>
		</tr>
		<tr>
			<th>은행</th>
			<td>${buyer.buyer_bank }</td>
		</tr>
		<tr>
			<th>계좌번호</th>
			<td>${buyer.buyer_bankno }</td>
		</tr>
		<tr>
			<th>판매자명</th>
			<td>${buyer.buyer_bankname }</td>
		</tr>
		<tr>
			<th>우편번호</th>
			<td>${buyer.buyer_zip }</td>
		</tr>
		<tr>
			<th>주소1</th>
			<td>${buyer.buyer_add1 }</td>
		</tr>
		<tr>
			<th>주소2</th>
			<td>${buyer.buyer_add2 }</td>
		</tr>
		<tr>
			<th>전번</th>
			<td>${buyer.buyer_comtel }</td>
		</tr>
		<tr>
			<th>팩스</th>
			<td>${buyer.buyer_fax }</td>
		</tr>
		<tr>
			<th>이멜</th>
			<td>${buyer.buyer_mail }</td>
		</tr>
		<tr>
			<th>대표자</th>
			<td>${buyer.buyer_charger }</td>
		</tr>
		<tr>
			<th></th>
			<td>${buyer.buyer_telext }</td>
		</tr>
		<tr>
			<td colspan="2"><input type="button" value="뒤로가기" /> 
			<c:if test="${mutable}">
					<input type="submit" value="수정"
						onclick="location.href='${pageContext.request.contextPath}/buyer/buyerUpdate.do?who=${buyer.buyer_id}'" />
					<input type="reset" value="취소" />
					<input type="button" value="탈퇴" id="delBtn" />
				</c:if></td>
		</tr>
	</table>


</body>
</html>