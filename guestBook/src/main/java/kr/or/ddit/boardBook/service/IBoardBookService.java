package kr.or.ddit.boardBook.service;


import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.BoardBookVO;
import kr.or.ddit.vo.PagingInfoVO;

public interface IBoardBookService {
	public ServiceResult createBoardBook(BoardBookVO boardBook);
	
	public long retrieveTotalRecord(PagingInfoVO<BoardBookVO> pagingVO);
	
	public BoardBookVO retrieveBoardBook(long bo_no);
	
	public List<BoardBookVO> retrieveBoardBookList(PagingInfoVO<BoardBookVO> pagingVO);
	
	public ServiceResult modifyBoardBook(BoardBookVO boardBook);
	
	public ServiceResult removeBoardBook(BoardBookVO boardBook);
	
}
