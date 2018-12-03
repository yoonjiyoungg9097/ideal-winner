<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
	ProdVO prod = (ProdVO) request.getAttribute("prod");
	Map<String, String> errors = new HashMap();
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<th>상품코드</th>
			<td>${prod.prod_id }</td>
		</tr>
		<tr>
			<th>상품명</th>
			<td>${prod.prod_name }</td>
		</tr>
		<tr>
			<th>품번</th>
			<td>${prod.prod_lgu }</td>
		</tr>
		<tr>
			<th>거래처정보</th>
			<td>
				<table>
					<thead>
						<tr>
							<th>거래처명</th>
							<th>소재지</th>
							<th>담당자명</th>
							<th>연락처</th>
						</tr>
					</thead>

					<tbody>
						<tr>
							<td>${prod.buyer.buyer_name }</td>
							<td>${prod.buyer.buyer_add1 }</td>
							<td>${prod.buyer.buyer_charger }</td>
							<td>${prod.buyer.buyer_comtel }</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th>매입가</th>
			<td>${prod.prod_cost }</td>
		</tr>
		<tr>
			<th>판매가</th>
			<td>${prod.prod_price }</td>
		</tr>
		<tr>
			<th>특가</th>
			<td>${prod.prod_sale }</td>
		</tr>
		<tr>
			<th>상품정보</th>
			<td>${prod.prod_outline }</td>
		</tr>
		<tr>
			<th>디테일</th>
			<td>${prod.prod_detail }</td>
		</tr>
		<tr>
			<th>이미지</th>
			<td>
				<img src="<c:url value='/prodImages/${prod.prod_img }'></c:url>"/>
			</td>
		</tr>
		<tr>
			<th>토탈스톡</th>
			<td>${prod.prod_totalstock }</td>
		</tr>
		<tr>
			<th>입고날자</th>
			<td>${prod.prod_insdate }</td>
		</tr>
		<tr>
			<th>프롭스톡</th>
			<td>${prod.prod_properstock }</td>
		</tr>
		<tr>
			<th>사잊브</th>
			<td>${prod.prod_size }</td>
		</tr>
		<tr>
			<th>색</th>
			<td>${prod.prod_color }</td>
		</tr>
		<tr>
			<th>배달</th>
			<td>${prod.prod_delivery }</td>
		</tr>
		<tr>
			<th>유닛</th>
			<td>${prod.prod_unit }</td>
		</tr>
		<tr>
			<th>슈령</th>
			<td>${prod.prod_qtyin }</td>
		</tr>
		<tr>
			<th>qtysale</th>
			<td>${prod.prod_qtysale }</td>
		</tr>
		<tr>
			<th>마일리지</th>
			<td>${prod.prod_mileage }</td>
		</tr>
		
		<tr>
				<td colspan="2">
				<input type="button" value="뒤로가기"/>
				<c:if test="${mutable }">
				<input type="submit" value="수정" /> 
				<input type="reset" value="취소" />
				 <input type="button" value="탈퇴" id="delBtn" />
				 </c:if>
				 </td>
			</tr>
	</table>
	
	<c:set var="authorized" value="false" scope="session"></c:set>
	<c:if test="${not empty authMember and 'ROLE_ADMIN' eq authMember.mem_auth }">
		<c:set var="authorized" value="true" scope="session"></c:set>
	</c:if>
	<c:if test="${authorized }">
	<%--
		boolean authorized = false;
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		if (authMember != null && "ROLE_ADMIN".equals(authMember.getMem_auth())) {
			// 		authorized = authMember!=null && "ROLE_ADMIN".equals(authMember.getMem_auth());
			authorized = true;
		}

		if (true) {
	--%>
	<input type="button" value="상품수정"
		onclick="location.href='${pageContext.request.contextPath}/prod/prodUpdate.do?what=${prod.prod_id }'">
	<h4>구매자 목록</h4>
	<table>
		<thead>
			<tr>
				<th>회원아이디</th>
				<th>회원명</th>
				<th>주소</th>
				<th>연락처</th>
				<th>이메일</th>
			</tr>
		</thead>

		<tbody>
			<c:if test="${not empty prod.customers}">
				<c:forEach items="${prod.customers }" var="emp">
					<tr>
						<td>${emp.mem_id}</td>
						<td>${emp.mem_name}</td>
						<td>${emp.address}</td>
						<td>${emp.mem_hp}</td>
						<td>${emp.mem_mail}</td>
					</tr>
				</c:forEach>
			</c:if>
			<%--
				List<MemberVO> customers = prod.getCustomers();
					if (customers != null && customers.size() > 0) {
						for (MemberVO tmp : customers) {
							pageContext.setAttribute("emp", tmp);
			--%>
			<c:if test="${empty prod.customers}">
			<%--
				}
					} else {
			--%>
			<tr>
				<td colspan="5">구매자가 없습니다 ㅜㅠ</td>
			</tr>
			</c:if>
			<%--
				}
				}
			--%>
			</c:if>
		</tbody>
	</table>

</body>
</html>