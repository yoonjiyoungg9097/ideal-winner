<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.ddit.or.kr/dateFn" prefix="dateFn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/fmtExample.jsp</title>
<script type="text/javascript">
	function changeHandler(){
		document.localeForm.submit();
	}
</script>
</head>
<body>
<h4>Locale에 따른 포맷팅 처리</h4>
<c:set var="clientLocale" value="${pageContext.request.locale }"></c:set>
<c:set var="clientTimezone" value="${dateFn:getDefault() }"></c:set>
<c:choose>
	<c:when test="${not empty param.locale }">
		<c:set var="pageLocale" value="${clientLocale.forLanguageTag(param.locale) }"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="pageLocale" value="${clientLocale }"></c:set>
	</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${not empty param.timezone }">
			<c:set var="pageTimezone" value="${clientTimezone.getTimeZone(param.timezone) }"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="pageTimeZone" value="${clientTimezone }"></c:set>
		</c:otherwise>
	</c:choose>
<form name="localeForm">
	<select name="locale" onchange="changeHandler();">
		<option value="">로케일 선택</option>
<%-- 		<c:forEach items="<%=Locale.getAvailableLocales() %>" var="locale"> --%>
		<c:forEach items="${dateFn:getAvaliableLocales() }" var="locale">
			<c:if test="${not empty locale.displayName }">
				<option value='${locale.toLanguageTag() }'>${locale.displayName }</option>
			</c:if>
		</c:forEach>
	</select>
	
	<select name="timezone" onchange="changeHandler();">
		<option value="">타임존 선택</option>
		<c:forEach items="${dateFn:getAvailableIDs()}" var="zoneId">
			<option value='${zoneId }'>${clientTimezone.getTimeZone(zoneId).displayName }</option>			
		</c:forEach>
	</select>
</form>

<script type="text/javascript">
	document.localeForm.locale.value="${pageLocale.toLanguageTag() }";
	document.localeForm.timezone.value="${pageTimezone.getID()}";
</script>
<fmt:setLocale value="${pageLocale }"/>
	
<table>
	<thead>
		<tr>
			<th>언어</th>
			<th>지역</th>
			<th>해당지역의 통화(1000을 기준)</th>
			<th>현재 시각을 해당 언어 방식으로</th>
			<th>선택한 타임존의 현재 시각</th>
		</tr>
	</thead>
	
	<tbody>
		<tr>
			<td>${pageLocale.displayLanguage }</td>
			<td>${pageLocale.displayCountry }</td>
			<td>
				<fmt:formatNumber value="1000" type="currency"></fmt:formatNumber>
			</td>
			<td>
				<fmt:formatDate value="${dateFn:getNow() }" type="both"/>
			</td>
			<td>
			${pageTimezone.displayName }
			<br/>
				<fmt:formatDate value="${dateFn:getNow() }" type="both" timeZone="${pageTimezone }"/>
			</td>
		</tr>
	</tbody>	
</table>
</body>
</html>




