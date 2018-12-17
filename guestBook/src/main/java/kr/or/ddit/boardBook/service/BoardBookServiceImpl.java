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
		ServiceResult serviceResult = ServiceResult.FAILED;
		int result = boardBookDAO.insertBoardBook(boardBook);
		if(result>0) {
			serviceResult = ServiceResult.OK;
		}
		return serviceResult;
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
		ServiceResult serviceResult = ServiceResult.INVALIDPASSWORD;
		BoardBookVO boardBookVO = boardBookDAO.selectBoardBook(boardBook.getBo_no());
		if(boardBookVO.getBo_pass().equals(boardBook.getBo_pass())) {
			int result = boardBookDAO.updateBoardBook(boardBook);
			if(result>0) {
				serviceResult = ServiceResult.OK;
			}else {
				serviceResult = ServiceResult.FAILED;
			}
		}
		return serviceResult;
	}

	@Override
	public ServiceResult removeBoardBook(BoardBookVO boardBook) {
		ServiceResult serviceResult = ServiceResult.INVALIDPASSWORD;//비밀번호
		BoardBookVO boardBookVO = boardBookDAO.selectBoardBook(boardBook.getBo_no());
		if(boardBookVO.getBo_pass().equals(boardBook.getBo_pass())) {//이조건에서 펄스가 나오면 비밀번호 다름
			int result = boardBookDAO.deleteBoardBook(boardBook.getBo_no());
			//삭제에 성공하면 ok
			if(result>0) {
				serviceResult = ServiceResult.OK;
			}else {
				//실패 서버문제
				serviceResult = ServiceResult.FAILED;
			}
		}
		return serviceResult;
	}



}
