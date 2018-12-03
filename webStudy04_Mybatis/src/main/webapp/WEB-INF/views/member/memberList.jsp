<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--
// 	IMemberService service = new MemberServiceImpl();
// 	List<MemberVO>memberList = service.retrieveMemberList();
	PagingInfoVO pagingVO=(PagingInfoVO) request.getAttribute("pagingVO");
	
	List<MemberVO>memberList = pagingVO.getDataList();
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/memberList.jsp</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script type="text/javascript">
<%-- 	function <%=pagingVO.getFuncName()%>(page){ --%>
		function ${pagingVO.funcName}(page){
		document.searchForm.page.value=page;
		document.searchForm.submit();
	}
</script>
</head>
<body>
<h4>회원 목록</h4>
<input type="button" class="button" value="신규등록"
	onclick="location.href='${pageContext.request.contextPath}/member/memberInsert.do';"	
/>
<table class="table">
	<thead class="thead-dark">
		<tr>
			<th>회원아이디</th>
			<th>회원명</th>
			<th>주소</th>
			<th>휴대폰</th>
			<th>이메일</th>
			<th>마일리지</th>
		</tr>
	</thead>
	
	<tbody>
		<c:set var="memberList" value="${pagingVO.dataList }"></c:set>
		<c:if test="${not empty memberList }">
			<c:forEach items="${memberList }" var="member">
				<tr>
                <td>${member.mem_id }</td>
                <td><a href="${pageContext.request.contextPath}/member/memberView.do?who=${member.mem_id}">${member.mem_id }</td>
                <td>${member.address }</td>
                <td>${member.mem_hp }</td>
                <td>${member.mem_mail }</td>
                <td>${member.mem_mileage }</td>
                </tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty memberList }">
			<tr>
				<td colspan="6">회원 목록 없으요</td>
			</tr>
		</c:if>
		    <%
//           String pattern = "<tr class='info'><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%d</td></td>";
//           if(memberList.size()!=0){
//              for(MemberVO member:memberList){
//                 pageContext.setAttribute("member", member);
                %>
<!--                 <tr> -->
<%--                 <td>${member.mem_id }</td> --%>
<%--                 <td><a href="${pageContext.request.contextPath}/member/memberView.do?who=${member.mem_id}">${member.mem_id }</td> --%>
<%--                 <td>${member.address }</td> --%>
<%--                 <td>${member.mem_hp }</td> --%>
<%--                 <td>${member.mem_mail }</td> --%>
<%--                 <td>${member.mem_mileage }</td> --%>
<!--                 </tr> -->
<!--                 out.print(String.format(pattern, member.getMem_id(),"<a href='"+request.getContextPath()+"/member/memberView.do?who="+member.getMem_id()+"'>"+ member.getMem_name()+"</a>",member.getAddress(),member.getMem_hp(),member.getMem_mail(),member.getMem_mileage())); -->

	</tbody>
	<tfoot>
		<tr>
             <td colspan='6'>
                <nav aria-label="Page navigation example">
                   ${pagingVO.pagingHTML}             
                </nav>
               
               <form name="searchForm">
                <input type="text" name="page"/><!--값을 전달하기 위해 입력받는거 아니다 -->
               <select name="searchType">
               		<option value="all">전체</option>
               		<option value="name">이름</option>
               		<option value="address">지역</option>
               </select>
               <script type="text/javascript">
               		document.searchForm.searchType.value = "${empty pagingVO.searchType ? 'all':pagingVO.searchType }"<%-- <%=Objects.toString(pagingVO.getSearchType(), "all")%>"; --%>
               </script>
               <input type="text" name="searchWord" value="${pagingVO.searchWord}"/><%-- <%= Objects.toString(pagingVO.getSearchWord(),"")%> --%>
               <input type="submit" value="검색"/>
               </form> 
             </td>
          </tr>
	</tfoot>
</table>
</body>
</html>