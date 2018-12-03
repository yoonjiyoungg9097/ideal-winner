<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/fmtDesc.jsp</title>
<style type="text/css">
input[type='image'] {
	width: 50px;
	height: 50px;
}
</style>

</head>
<body>
	<h4>국제화 태그 (format 태그, fmt 태그)</h4>
	<input type="image" src="<c:url value='/images/korea.png'/>"<%-- contextPath가 필요하지 않다 --%>
		onclick="location.href='?click=ko_KR'" name="click" value="ko">
	<input type="image" src="<c:url value='/images/america.png'/>"
		onclick="location.href='?click=en_US'" name="click" value="en">
	<pre>
	1.국제화(i18n, internationalization)
		setLocale, setBundle, message
		 
		1) 언어 결정
		2) 메세지 번들 작성 (basename_locale) : 로케일 정보는 basename 포함되지 않음
		** setLocale : 로딩할 메세지 번들의 로케일을 결정
		3) 메세지 번들 로딩(setBundle/bundle) 
		4) 메세지 키를 이용해 메시지를 출력(message)
		
	<c:if test="${not empty param.click }">
	
	<fmt:setLocale value="${param.click }" />
	</c:if>
	<fmt:bundle basename="kr.or.ddit.msgs.message">
		<fmt:message key="bow" />
	
	</fmt:bundle>
	<%
		String[] zoneIds = TimeZone.getAvailableIDs();
		TimeZone zone = TimeZone.getTimeZone(zoneIds[2]);
		
	%>	
	<%=zone.getDisplayName() %>
	
		
	2.지역화(l10n, localization)
		파싱(parseNumber,parsDate)
		-일정한 형식의 문자열을 찾아서 특정 타입의 데이터로 변환
		포맷팅(fomatNumber,fomatDate)
		-특정타입의 데이터를 일정한 형식의 문자열로 바꿔주는 작업
		
		<fmt:formatDate value="<%=new Date()%>" type="both" dateStyle="long"
			timeStyle="long" timeZone="<%=zone %>"/>
		<fmt:formatNumber value="100000" type="currency" var="numStr" />

		${numStr } -----------------------
		<fmt:parseNumber value="${numStr }" type="currency"
			parseLocale="${param.click }" var="number"></fmt:parseNumber>
		${number * 3 }
		
	
</pre>

</body>
</html>