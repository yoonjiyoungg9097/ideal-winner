<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
	List<Map<String,Object>>lprodList	 = (List)request.getAttribute("lprodList");
--%>
<%-- <jsp:useBean id="prod" class="kr.or.ddit.vo.ProdVO" scope="request"></jsp:useBean> --%>
<%-- <jsp:useBean id="errors" class="java.util.HashMap" scope="request"></jsp:useBean> --%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="https://jqueryui.com/resources/demos/style.css">
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script type="text/javascript">
	$(function(){
	<c:if test="${not empty message}">
		alert("${request.message}");
	</c:if>
	<%--
		String message = (String)request.getAttribute("message");
		if(StringUtils.isNotBlank(message)){
			%>
			alert("${request.message}");
			<%
		}
	--%>
	$("[name$='date']").datepicker({
		dateFormat:"yy-mm-dd"
	});
	var pattern ="<option value='%V'>%T</option>";
	var prod_buyerTag = $("[name='prod_buyer']"); //prod_buyer라는 이름을 가진곳으로 html코드를 보내준다
	var prod_lguTag = $("[name='prod_lgu']");
	
	prod_lguTag.on("change", function(){
		var prod_lgu = $(this).val();
		$.ajax({
			url : "${pageContext.request.contextPath}/prod/getBuyerList.do",
			data : {
				prod_lgu:prod_lgu //파라미터명:값
			},
			dataType : "json", //Accept, Content-Type 응답헤더
			success : function(resp) {
				var options = "";
				$.each(resp,function(idx, buyer){//resp는 배열, 우리가 받아온 json객체 idx는 index
					options += pattern.replace("%V", buyer.buyer_id)
									 .replace("%T", buyer.buyer_name);
				});
				prod_buyerTag.html(options);
				prod_buyerTag.val("${prod.prod_buyer}");
			},
			error : function(resp) {

			}
		});
		});
	
	prod_lguTag.val("${prod.prod_lgu}"); /* 초기값 설정 */
	prod_lguTag.trigger("change");//이벤트 강제로 발생
	
	});
	
</script>
</head>
<body>
	<form method="post" enctype="multipart/form-data">
	<input type="hidden" name="prod_id" value="${prod.prod_id }"/>
	<table>
		<!-- 		<tr> -->
		<!-- 			<th>상품아이디</th> -->
		<!-- 			<td> -->
		<%-- 				<input type="text" name="prod_id" value="<%= prod.getProd_id()%>"/> --%>
		<%-- 				<span class="error"><%= %></span> --%>
		<!-- 			</td> -->
		<!-- 		</tr> -->
<!-- 		<tr> 직접입력금지--> 
<!-- 			<th>상품코드</th> -->
<!-- 			<td><input type="text" name="prod_id" -->
<%-- 				value="<%=prod.getProd_id()%>" /><span class="error"><%=errors.get("prod_id")%></span></td> --%>
<!-- 		</tr> -->
		<tr>
			<th>상품명</th>
			<td><input type="text" name="prod_name"
				value="${prod.prod_name }" /><span class="error">${errors["mem_memorialday"]}</span></td>
		</tr>
		<tr>
			<th>분류코드</th>
			<td>
				<div class="input-group">
					<select name="prod_lgu">
						<option value="">분류선택</option>
						<c:forEach items="${lprodList }" var="lprod">
								<option value='${lprod["LPROD_GU"] }'> ${lprod["lprod_nm"] }</option>
						</c:forEach>
						<%--
							for(Map<String,Object> lprod : lprodList){
								pageContext.setAttribute("lprod", lprod);
								--%> 
<%-- 								<option value='${lprod["LPROD_GU"] }'> ${lprod["lprod_nm"] }</option> --%>
								<%--
							}
						--%>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<th>판매자</th>
			<td>
			<select name="prod_buyer">
			</select>
			</td>
		</tr>
		<tr>
			<th>매입가</th>
			<td><input type="text" name="prod_cost" 
				value="${prod.prod_cost }" /><span class="error">${errors["prod_cost"]}</span></td>
		</tr>
		<tr>
			<th>판매가</th>
			<td><input type="text" name="prod_price"
				value="${prod.prod_price }" /><span class="error">${errors["prod_price"]}</span></td>
		</tr>
		<tr>
			<th>특가</th>
			<td><input type="text" name="prod_sale"
				value="${prod.prod_sale }" /><span class="error">${errors["prod_sale"]}</span></td>
		</tr>
		<tr>
			<th>상품정보</th>
			<td><input type="text" name="prod_outline"
				value="${prod.prod_outline }" /><span class="error">${errors["prod_outline"]}</span></td>
		</tr>
		<tr>
			<th>디테일</th>
			<td><input type="text" name="prod_detail"
				value="${prod.prod_detail }" /><span class="error">${errors["prod_detail"]}</span></td>
		</tr>
		<tr>
			<th>이미지</th>
			<td><input class="form-control" type="file" name="prod_image" />
			<span class="input-group-text error">${errors["prod_image"]}</span></td>
		</tr>
		<tr>
			<th>토탈스톡</th>
			<td><input type="text" name="prod_totalstock"
				value="${prod.prod_totalstock }" /><span class="error">${errors["prod_totalstock"]}</span></td>
		</tr>
		<tr>
			<th>입고날자</th>
			<td><input type="text" name="prod_insdate"
				value="${prod.prod_insdate }" /><span class="error">${errors["prod_insdate"]}</span></td>
		</tr>
		<tr>
			<th>프롭스톡</th>
			<td><input type="text" name="prod_properstock"
				value="${prod.prod_properstock }" /><span class="error">${errors["prod_properstock"]}</span></td>
		</tr>
		<tr>
			<th>사잊브</th>
			<td><input type="text" name="prod_size"
				value="${prod.prod_size }" /><span class="error">${errors["prod_size"]}</span></td>
		</tr>
		<tr>
			<th>색</th>
			<td><input type="text" name="prod_color"
				value="${prod.prod_color }" /><span class="error">${errors["prod_color"]}</span></td>
		</tr>
		<tr>
			<th>배달</th>
			<td><input type="text" name="prod_delivery"
				value="${prod.prod_delivery }" /><span class="error">${errors["prod_delivery"]}</span></td>
		</tr>
		<tr>
			<th>유닛</th>
			<td><input type="text" name="prod_unit"
				value="${prod.prod_unit }" /><span class="error">${errors["prod_unit"]}</span></td>
		</tr>
		<tr>
			<th>슈령</th>
			<td><input type="text" name="prod_qtyin"
				value="${prod.prod_qtyin }" /><span class="error">${errors["prod_qtyin"]}</span></td>
		</tr>
		<tr>
			<th>스타일</th>
			<td><input type="text" name="prod_qtysale"
				value="${prod.prod_qtysale }" /><span class="error">${errors["prod_qtysale"]}</span></td>
		</tr>
		<tr>
			<th>마일리지</th>
			<td><input type="text" name="prod_mileage"
				value="${prod.prod_mileage }" /><span class="error">${errors["prod_mileage"]}</span></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="전송"/>
				<input type="reset" value="취소"/>
				<input type="button" value="목록으로"
					onclick="location.href='${pageContext.request.contextPath}/prod/prodList.do';"/>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>