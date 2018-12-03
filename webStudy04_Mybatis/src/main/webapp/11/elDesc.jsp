<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>EL(표현언어 Expression language)</h4>
<pre>
	:데이터를 출력할 목적의 스크립트 언어
	\${속성명(EL변수)}
	1.네가지 scope 내의 attribute데이터에 대한 접근  방법 제공(**)
		: 속성을 검색시, 영역에 대한 명시가 없는 경우, 네가지 영역을 순차적으로 검색
		: 직접 접근시, pageScope, requestScope, sessionScope, applicationScope 객체 사용
	
	2.EL연산자 제공 : 피연산자로 리터럴이나 속성만 사용가능
		1) 산술연산자 : +,-,/,*,% 기본으로 실수 연산 수행, "+"연산자는 산술연산만 수행한다 
					concat연산자는 수행하지 않는다
<%-- 			${"a"+3 } --%>
			${2/3 }, ${3%2 }, ${"1"+"1" }, ${"1"+1 }, ${"3"/2 }
		2) 논리연산자 : AND(&&, and), OR(||, or), NOT(!, not)
		<%
			Boolean bool = new Boolean(true);
			pageContext.setAttribute("bool", bool);
		%>
		${bool2 } |	${bool2 and true } | ${not bool }
		3) 비교연산자 : ==(eq), !=(ne), &gt;(gt), &lt;(lt), &gt;=(ge), %lt;=(le)
			${3 gt 2 }, ${bool ne false }
		4) 단항연산자 : ++/--(불가능, EL 3.0부터 지원), empty(***)
		<%
			String test = "  ";
			pageContext.setAttribute("testAttr", test);
			List<String> testList = Arrays.asList("a", "b");
// 			List<String> testList = null;
			pageContext.setAttribute("testList", testList);
		%>
			${empty testAttr }, ${empty testList }
		5) 삼항연산자 : 조건식?참표현:거짓표현
			${empty testList?"컬렉션이 비어있다":"컬렉션이 비어있지 않다" }
	3.자바객체의 메소드에 대한 접근 방법 제공(since EL 2.2 version)
		<%
			MemberVO member = new MemberVO("a001", "asdf");
			pageContext.setAttribute("member", member);
			member.setMem_add1("대전");
			member.setMem_add2("대흥동");
		%>
		${member.getMem_id() }, ${member.mem_id }
		${member.getAddress() }, ${member.addressTest }
	4.집합객체의 요소에 대한 접근 방법 제공
	5. EL의 기본객체 제공
	<%
		Date today = new Date(); //지역변수
		pageContext.setAttribute("today", today+"pageScope");
		request.setAttribute("today", today+"requestScope");
	%>
	<%=today %>, ${requestScope.today }, ${today } <!--today라는  속성을 어디서 찾았는지 스코프영역에서 가장 작은부분부터 찾아나간다 --> 
</pre>
</body>
</html>