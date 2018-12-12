package kr.or.ddit.boardBook.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.boardBook.dao.BoardBookDAOImpl;
import kr.or.ddit.boardBook.dao.IBoardBookDAO;
import kr.or.ddit.vo.BoardBookVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BoardBookServiceImpl implements IBoardBookService {
	IBoardBookDAO boardBookDAO = new BoardBookDAOImpl();

	@Override
	public ServiceResult createBoardBook(BoardBookVO boardBook) {
		return null;
	}
	
	@Override
	public long retrieveTotalRecord(PagingInfoVO<BoardBookVO> pagingVO) {
		return boardBookDAO.selectTotalRecord(pagingVO);
	}

	@Override
	public BoardBookVO retrieveBoardBook(long bo_no) {
		return boardBookDAO.selectBoardBook(bo_no);
	}

	@Override
	public List<BoardBookVO> retrieveBoardBookList(PagingInfoVO<BoardBookVO> pagingVO) {
		return boardBookDAO.selectBoardBookList(pagingVO);
	}
	
	@Override
	public ServiceResult modifyBoardBook(BoardBookVO boardBook) {
		return null;
	}

	@Override
	public ServiceResult removeBoardBook(long bo_no) {
		return null;
	}



}
