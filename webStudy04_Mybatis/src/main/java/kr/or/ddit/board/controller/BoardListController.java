package kr.or.ddit.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BoardListController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		IBoardService service = new BoardServiceImpl();
		PagingInfoVO<BoardVO> pagingVO = new PagingInfoVO<>();
		long currentPage = 1;
		String page = req.getParameter("page");
		if(StringUtils.isNumeric(page)) {
			currentPage = Long.parseLong(page);
		}
		String searchWord = req.getParameter("searchWord");
		String searchType = req.getParameter("searchType");
		pagingVO.setSearchWord(searchWord);
		pagingVO.setSearchType(searchType);
		pagingVO.setCurrentPage(currentPage);
		long boardCount = service.retriveBoardCount(pagingVO);
		pagingVO.setTotalRecord(boardCount);
		List<BoardVO> boardList = service.retriveBoardList(pagingVO);
		
		pagingVO.setDataList(boardList);
		req.setAttribute("pagingVO", pagingVO);
		return "board/boardList";
	}

}
