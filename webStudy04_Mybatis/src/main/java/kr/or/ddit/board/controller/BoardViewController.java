package kr.or.ddit.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;

public class BoardViewController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String what = req.getParameter("what");
		long bo_no = 0;
		if(StringUtils.isNumeric(what)) {
			bo_no = Long.parseLong(what);
		}
		IBoardService service = new BoardServiceImpl();
		BoardVO board = service.retriveBoard(bo_no);
		
		//댓글의 페이징처리를 위한 pagingVO객체생성해주기 제너릭 타입은 replyVO
		//pagingVO에 currentpage는 처음이니까??
		//댓글service 객체생성
		//replyVO객체 생성
		//replyVO에 값을 넣어준다
		//페이징Vo에 값을 넘겨준다???
		//request 영역에 pagingVO를 담아준다
		PagingInfoVO<ReplyVO> pagingVO = new PagingInfoVO<>();
		pagingVO.setCurrentPage(currentPage);
		
		req.setAttribute("board", board);
		return "board/boardView";
	}

}
