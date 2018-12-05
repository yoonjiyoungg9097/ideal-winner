package kr.or.ddit.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
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
//		PagingInfoVO<ReplyVO> pagingVO = new PagingInfoVO<>();
//		//pagingVO에 currentpage는 처음이니까??
//		pagingVO.setCurrentPage(1);
		
		//댓글service 객체생성
//		IReplyService replyService = new ReplyServiceImpl();
//		//replyVO객체 생성
//		ReplyVO replyVO = new ReplyVO();
//		//replyVO에 값을 넣어준다
		
		//페이징Vo에 값을 넘겨준다???
		//request 영역에 pagingVO를 담아준다
		
		req.setAttribute("board", board);
		return "board/boardView";
	}

}
