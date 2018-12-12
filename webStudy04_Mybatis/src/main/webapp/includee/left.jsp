<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
	function goIndex(command){
		var form = document.leftForm;
		form.command.value = command;
		form.submit();
	}
</script>
<ul>
<%-- 	<li><a href="<%=request.getContextPath() %>/?command=gugudan">구구단</a></li> --%>
	<li><a href="javascript:goIndex('gugudan');">구구단</a></li>
	<%-- <li><a href="<%=request.getContextPath() %>/?command=lyrics">가사 파일</a></li> --%>
	<li><a href="javascript:goIndex('lyrics');">가사 파일</a></li>
	<li><a href="javascript:goIndex('calendar');">달력</a></li>
	<li><a href="javascript:goIndex('image');">이미지뷰어</a></li>
	<li>누적 방문자수 : ${applicationScope.usercount }</li>
	<li>
		접속자 리스트<br />
		<c:forEach items="${applicationUsers }" var="user">
			${user.mem_name }<br />
		</c:forEach>
	</li>
</ul>
<form name="leftForm" action="${cPath }/" method="post">
	<input name="command" value="" type="hidden"/> <!-- 값을전달할 목적으로 만든것 -->
</form>
