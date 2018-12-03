<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--
	PagingInfoVO<ProdVO> pagingVO = (PagingInfoVO)request.getAttribute("pagingVO");
	List<ProdVO> prodList = pagingVO.getDataList();
	
	List<Map<String, Object>>lprodList = (List)request.getAttribute("lprodList");
	List<BuyerVO>buyerList = (List)request.getAttribute("buyerList");
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script type="text/javascript">
	function ${pagingVO.funcName}(page){
		$("[name='searchForm']").find("[name='page']").val(page);
// 		document.searchForm.page.value=page;
		$("[name='searchForm']").submit(); //이벤트 발샐??
// 		document.searchForm.submit(); 함수 하나만 호출하고 끝
	}
	
	$(function() {
<%-- 		$("[name='prod_lgu']").val("<%=pagingVO%>"); --%>
		var prod_lguTag = $("[name='prod_lgu']");
		var prod_buyerTag = $("[name='prod_buyer']");
		prod_lguTag.val("${pagingVO.searchVO.prod_lgu}");
		prod_buyerTag.val("${pagingVO.searchVO.prod_buyer}");
		prod_lguTag.on("change", function(){
			var lprod_gu = $(this).val();
			var buyerOptions = prod_buyerTag.find("option");
			$(buyerOptions).hide();
			if(lprod_gu){
				var buyerOptions2 = prod_buyerTag.find("option."+lprod_gu);
				$(buyerOptions2).show();
			}else{
				$(buyerOptions).show();
			}
			
		});
		var listBody = $("#listBody");
		listBody.on("click","tr", function(){
			var prod_id = $(this).find("td:first").text();//find()는 자식중에서 특정조건에 맞는 것을 찾아온다
			location.href = "${pageContext.request.contextPath}/prod/prodView.do?what="+prod_id; //클라이언트가 사용
		});
		
		var pattern = "";
		var nar=$("#page2");
		$("[name='searchForm']").on("submit", function(event){<!-- 	onsubmit="return false;" -->
			event.preventDefault();//form은 기본적으로 동기전송방식인데 그거 없애는거
			var data = $(this).serialize(); //queryString 생성
// 			{
// 				prod_lgu: dff
// 				prod_buyer: fdfsfd
				
// 			}
			$.ajax({
// 				url : "", //어디로 요청을 보내는지 생략가능한건 현재 브라우저의 주소 사용
// 				method : "", //디폴트는 get방식
				data:data,//어떤 데이터를 보내는지 위의 쿼리스트링을 넣어준다
				dataType : "json", //Accept/Content-type
				success : function(resp) {//응답데이터가 언마샬링 된거 resp는 pagingVO
					var prodList = resp.dataList;
					var html = "";
					if(prodList){
						$.each(prodList, function(idx, prod){
							html += "<tr>";
							html += "<td>"+prod.prod_id+"</td>";
							html += "<td>"+prod.prod_name+"</td>";
							html += "<td>"+prod.lprod_nm+"</td>";
							html += "<td>"+prod.buyer_name+"</td>";
							html += "<td>"+prod.prod_price+"</td>";
							html += "<td>"+prod.prod_outline+"</td>";
							html += "<td>"+prod.prod_mileage+"</td>";
							html += "</tr>";
						});
					}else{
						html += "<tr><td colspan='7'>상품이 없습니다 </td></tr>";
					}
					listBody.html(html);
					$("#pagingArea").html(resp.pagingHTML);
					$("[name='page']").val("");
				},
				error : function(resp) {

				}
			});
			return false;
		});
	});
</script>
</head>
<body>
	<!-- 스크린사이즈 7 -->
	<!-- 블럭사이즈 4 -->

	<form name="searchForm" >
	<input type="text" name="page"/>
	<select name="prod_lgu">
<!-- 		<option value="p101">전자제품</option> -->
		<option value="">분류선택</option>
		<c:forEach items="${lprodList}" var="lprod">
		<%--
			for(Map<String, Object> lprod : lprodList){
				pageContext.setAttribute("lprod", lprod);
				//lprod가 현재 스코프 영역에 담아있지 않아서 el에서 사용하지 못하기때문에 사용하기 위해 페이지컨텍스트
				//스코프 영역에 lprod라는 속성명을 가진 값을 담아준다
				--%>
				<option value="${lprod['LPROD_GU']}">${lprod["lprod_nm"]}</option>
				</c:forEach>
				<%--
			}
		--%>
	</select>
	<select name="prod_buyer">
<!-- 		<option value="p10101">삼성전자</option> -->
		<option value="">거래처선택</option>
		<c:forEach items="${buyerList}" var="buyer">
		<%--
			for(BuyerVO buyer : buyerList){
				pageContext.setAttribute("buyer", buyer);
				--%>
					<option value="${buyer.buyer_id }" class="${buyer.buyer_lgu }">${buyer.buyer_name }</option>
					</c:forEach>
				<%--
			}
		%>
	</select>
<%-- 	<input type="text" name="prod_name" value="<%= pagingVO.getSearchVO().getProd_name()%>"/> --%>
	<input type="text" name="prod_name" value="${pagingVO.searchVO.prod_name}"/>
	<input type="submit" value="검색"/>
	</form>
	<input type="button" class="btn btn-info" value="신규상품등록" 
		onclick="location.href='${pageContext.request.contextPath}/prod/prodInsert.do';"/>
		<input type="image" src="<c:url value='/images/korea.png'/>"
onclick="location.href='?click=ko'" name="click" value="ko">
<input type="image" src="<c:url value='/images/america.png'/>"
	onclick="location.href='?click=en'" name="click" value="en">
	<c:if test="${not empty param.click }">
	
	<fmt:setLocale value="${param.click }"/>
	</c:if>
	<table class="table">
		<thead>
		<fmt:bundle basename="kr.or.ddit.msgs.message">
			<tr>
				<th><fmt:message key="prod.prod_id"/></th>
				<th><fmt:message key="prod.prod_name"/></th>
				<th><fmt:message key="prod.prod_lgu"/></th>
				<th><fmt:message key="prod.prod_buyer"/></th>
				<th><fmt:message key="prod.prod_price"/></th>
				<th><fmt:message key="prod.prod_outline"/></th>
				<th><fmt:message key="prod.prod_mileage"/></th>
<!-- 				<th>상품코드</th> -->
<!-- 				<th>상품명</th> -->
<!-- 				<th>분류명</th> -->
<!-- 				<th>거래처명</th> -->
<!-- 				<th>판매가</th> -->
<!-- 				<th>상품개요</th> -->
<!-- 				<th>마일리지</th> -->
			</tr>
		</fmt:bundle>
		</thead>

		<c:set var="prodList" scope="request" value="${pagingVO.dataList }"></c:set>
		<tbody id="listBody">
			<c:if test="${not empty prodList }">
				<c:forEach items="${prodList}" var="prod">
					<tr>
           				<td>${prod.prod_id }</td>   
               			<td>${prod.prod_name }</td>   
               			<td>${prod.lprod_nm }</td>   
               			<td>${prod.buyer_name }</td>   
               			<td>${prod.prod_price }</td>   
              			<td>${prod.prod_outline }</td>   
               			<td>${prod.prod_mileage }</td>
           			</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty prodList }">
           		<tr>
           			<td colspan="7"> 조건에 맞는 상품이 없습니다 ㅜㅠ</td>
           		</tr>
			</c:if>
			<%--
        		if(prodList.size()>0){
           			for(ProdVO prod:prodList){
           				pageContext.setAttribute("prod", prod);
           				--%>
			
		</tbody>

		<tfoot>
			<tr>
				<td colspan="7">
				
				<nav aria-label="Page navigation example" id="pagingArea">
                   ${pagingVO.pagingHTML} 
               </nav>
				</td>
			</tr>
		</tfoot>
	</table>
</body>
</html>