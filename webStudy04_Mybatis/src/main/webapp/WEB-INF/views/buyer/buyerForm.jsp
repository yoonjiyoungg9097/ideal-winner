<%@page import="java.util.List"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
	BuyerVO buyer = (BuyerVO) request.getAttribute("buyer");
	if (buyer == null)
		buyer = new BuyerVO();
	Map<String, String> errors = (Map) request.getAttribute("errors");
	if (errors == null)
		errors = new HashMap();
	List<Map<String, Object>> lprodList = (List) request.getAttribute("lprodList");
	
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
</head>
<body>
	<form method="post">
		<!-- action이 없어서 현재페이지 경로를 쓰기 때문에 insert와 update를 구분해줄수 있다 -->
		<table>
			<%-- 판매자 아이디는 자동생성해주고 직접 입력받지 않기때문에 hidden으로 --%>
			<tr>
				<th>판매자아이디</th>
				<td><input type="hidden" name="buyer_id"
					VALUE="${buyer.buyer_id}" /></td>
			</tr>
			<tr>
				<th>판매처명</th>
				<td><input type="text" name="buyer_name"
					VALUE="${buyer.buyer_name}" /><span class="error">${errors["buyer_name"]}</span></td>
			</tr>
			<tr>
				<th>엘지유</th>
				<td><select name="buyer_lgu">
					<c:forEach items="${lprodList }" var="lprod">
						<option value='${lprod["LPROD_GU"] }'>${lprod["lprod_nm"] }</option>
					</c:forEach>

				</select> <span class="error">${errors["buyer_lgu"]}</span></td>
			</tr>
			<tr>
				<th>은행</th>
				<td><input type="text" name="buyer_bank"
					VALUE="${buyer.buyer_bank}" /><span class="error">${errors["buyer_bank"]}</span></td>
			</tr>
			<tr>
				<th>계좌번호</th>
				<td><input type="text" name="buyer_bankno"
					VALUE="${buyer.buyer_bankno}" /><span class="error">${errors["buyer_bankno"]}</span></td>
			</tr>
			<tr>
				<th>판매자명</th>
				<td><input type="text" name="buyer_bankname"
					VALUE="${buyer.buyer_bankname}" /><span class="error">${errors["buyer_bankname"]}</span></td>
			</tr>
			<tr>
				<th>우편번호</th>
				<td><input type="text" name="buyer_zip"
					VALUE="${buyer.buyer_zip}" /><span class="error">${errors["buyer_zip"]}</span></td>
			</tr>
			<tr>
				<th>주소1</th>
				<td><input type="text" name="buyer_add1"
					VALUE="${buyer.buyer_add1}" /><span class="error">${errors["buyer_add1"]}</span></td>
			</tr>
			<tr>
				<th>주소2</th>
				<td><input type="text" name="buyer_add2"
					VALUE="${buyer.buyer_add2}" /><span class="error">${errors["buyer_add2"]}</span></td>
			</tr>
			<tr>
				<th>전번</th>
				<td><input type="text" name="buyer_comtel"
					VALUE="${buyer.buyer_comtel}" /><span class="error">${errors["buyer_comtel"]}</span></td>
			</tr>
			<tr>
				<th>팩스</th>
				<td><input type="text" name="buyer_fax"
					VALUE="${buyer.buyer_fax}" /><span class="error">${errors["buyer_fax"]}</span></td>
			</tr>
			<tr>
				<th>이멜</th>
				<td><input type="text" name="buyer_mail"
					VALUE="${buyer.buyer_mail}" /><span class="error">${errors["buyer_mail"]}</span></td>
			</tr>
			<tr>
				<th>뭔구</th>
				<td><input type="text" name="buyer_charger"
					VALUE="${buyer.buyer_charger}" /><span class="error">${errors["buyer_charger"]}</span></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit" value="전송" /> <input
					type="reset" value="취소" /> <input type="button" value="목록으로"
					onclick="location.href='${pageContext.request.contextPath}/buyer/buyerList.do';" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>