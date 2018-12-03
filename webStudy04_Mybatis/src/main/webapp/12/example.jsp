<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.green {
	color: green;
}

.yellow {
	color: yellow;
}

.red {
	color: red;
	background: red;
}
.blue{
	background: blue;
}

.silver {
	color: silver;
}

.aqua {
	color: aqua;
}

.orange {
	color: orange;
}
</style>
</head>
<body>
<!-- 	2단부터 9단까지 구구단을 승수1~9까지 table 태그로 출력  -->
<!-- 	첫번째 단은 파란색 배경  -->
<!-- 	네번째 단은 빨간색 배경 -->

	<c:set var="minDan" value="${empty param.minDan?2:param.minDan }"/>
	<c:set var="maxDan" value="${empty param.maxDan?9:param.maxDan }"/>
	<form>
		최소단 : <input type="number" name="minDan" value="${minDan }"/>
		최대단 : <input type="number" name="maxDan" value="${maxDan }"/>
		<input type="submit" value="구구단"/>
	</form>

	<table>
		<c:forEach var="dan" begin="${minDan }" end="${maxDan }" varStatus="lts">
			<c:set var="colorClz" value="normal"></c:set>
			<c:if test="${lts.first }">
				<c:set var="colorClz" value="blue"></c:set>
			</c:if>
			<c:if test="${lts.count eq 4 }"> <!-- 4번 반복했을때 -->
				<c:set var="colorClz" value="red"></c:set>
			</c:if>
			<tr class="${colorClz }">
				<c:forEach var="mul" begin="1" end="9" >
					<td>${dan }*${mul }=${dan*mul }</td>
				</c:forEach>
			</tr>	
		</c:forEach>
	</table>

	<form>
		당신의 나이는 ? <input type="number" name="age" value="" /> <input
			type="submit" value="전송" />
	</form>
	<!-- 	age 파라미터가 있다면 -->
	<!-- 	1. age가 20대면 "반갑다 친구야" green-->
	<!-- 	2. age가 30대면 "반갑다 햄아" yellow-->
	<!-- 	3. age가 40대면 "담배끊고 건강관리 합시다" red-->
	<!-- 	4. age가 50대면 "아빠??" silver-->
	<!-- 	5. age가 그 이상이면 "무슨일로 오셨나요?" aqua-->
	<!-- 	6. age가 20대 미만이면, "나가 놀아라" orange-->

	<c:set var="age" value="${param.age }" />
	<c:if test="${not empty age }">

		<c:choose>
			<c:when test="${age ge 20 and age lt 30}">
				<c:set var="message" value="반갑다 친구야"></c:set>
				<c:set var="clz" value="green"></c:set>
			</c:when>

			<c:when test="${age ge 30 and age lt 40}">
				<c:set var="message" value="반갑다 햄아"></c:set>
				<c:set var="clz" value="yellow"></c:set>
			</c:when>

			<c:when test="${age ge 40 and age lt 50}">
				<c:set var="message" value="담배끊고 건강관리 합시다"></c:set>
				<c:set var="clz" value="red"></c:set>
			</c:when>

			<c:when test="${age ge 50 and age lt 60}">
				<c:set var="message" value="아빠??"></c:set>
				<c:set var="clz" value="silver"></c:set>
			</c:when>

			<c:when test="${age ge 60}">
				<c:set var="message" value="무슨일로 오셨나요?"></c:set>
				<c:set var="clz" value="aqua"></c:set>
			</c:when>

			<c:otherwise>
				<c:set var="message" value="나가 놀아라"></c:set>
				<c:set var="clz" value="orange"></c:set>
			</c:otherwise>
		</c:choose>

		<div id="msgArea" class="${clz }">${message}</div>
	</c:if>
</body>
</html>














