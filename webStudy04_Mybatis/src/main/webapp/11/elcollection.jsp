<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>11/elcollection.jsp</title>
<script type="text/javascript" 
	src="<%=request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	var memberVO = {};
	console.log(JSON.stringify(memberVO));
	memberVO.mem_id = "a001";
	console.log(JSON.stringify(memberVO));
	memberVO["mem_name"]="김은대";
	console.log(JSON.stringify(memberVO));
	
	for(var prop in memberVO){
		console.log(prop + " : " + memberVO[prop]);
	}
	console.log("=====================");
	$.each(memberVO, function(prop, propValue){
		console.log(prop + ":" + propValue + "," + memberVO[prop]);
	});
</script>
</head>
<body>
<h4>EL 에서의 집합객체 사용</h4>
<pre>
	<%
		String[] array = new String[]{"element1", "element2", "element3"};
		pageContext.setAttribute("array", array);
		List<String>list = new ArrayList<>();
		list.add("listValue1");
		list.add("listValue2");
		list.add("listValue3");
		pageContext.setAttribute("list", list);
		
		Map<String, Object>map = new HashMap<>();
		map.put("key1", "value1");
		map.put("key-2", new Date());
		pageContext.setAttribute("map", map);
		
		MemberVO member = new MemberVO("a001", "asdf");
		pageContext.setAttribute("member", member);
		Set<String> set = new HashSet<>();
		set.add("setValue1");
		set.add("setValue2");
		set.add("setValue1"); //중복 허용X, 순서도 없다
		pageContext.setAttribute("set", set);
	%>
	${array[5]} <%-- <%=array[5] %> --%>
	${list.get(1) }, ${list[1] }
 	${list[5] } <%--${list.get(5) } --%>
 	${map.get("key1") }, ${map.key1 }
 	${map.get("key-2") }, <%-- ${map.key-2 } --%>, ${map["key-2"] }
 	${member.getMem_id() }, ${member.mem_id }, ${member["mem_id"] }<%-- 마지막꺼 연산배열구조 --%>
 	${set }
</pre>
</body>
</html>
















