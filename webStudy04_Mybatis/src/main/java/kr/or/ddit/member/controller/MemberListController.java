package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingInfoVO;

//@WebServlet("/member/memberList.do")
//public class MemberListServlet extends HttpServlet{
public class MemberListController implements ICommandHandler {
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1. 요청과의 매핑 설정
//		2. 요청 분석(주소, 파라미터, 메소드, 헤더들)
//		3. Businness Logic Layer와의 의존관계 형성
//		4. 로직 선택
//		5. 컨텐츠(Model) 확보
//		6. View Layer를 선택
//		String view = "member/memberList"; //논리적인 view name
//		7. Scope를 통해 Model 공유
//		req.setAttribute("memberList", memberList);
//		8. 이동 방식을 결정하고, View Layer로 이동 frontController가 처리하기 때문에
//		RequestDispatcher rd = req.getRequestDispatcher(view);
//		rd.forward(req, resp);
		//검색해주기위해
		String searchWord = req.getParameter("searchWord");
		String searchType = req.getParameter("searchType");
		
		int currentPage = 1;
		String page = req.getParameter("page");
		if (StringUtils.isNumeric(page)) {
			currentPage = Integer.parseInt(page);
		}
		PagingInfoVO<MemberVO> pagingVO = new PagingInfoVO<>();
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSearchWord(searchWord);
		pagingVO.setSearchType(searchType);
		
		IMemberService serivce = new MemberServiceImpl();
		long totalRecord = serivce.retriveMemberCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		// 페이징
		List<MemberVO> memberList = serivce.retrieveMemberList(pagingVO);
		pagingVO.setDataList(memberList);
		String view = "member/memberList";
		System.err.println(view);
		req.setAttribute("pagingVO", pagingVO);
		return view;

	}
}
