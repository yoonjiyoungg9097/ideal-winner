package kr.or.ddit.boardBook.dao;

import java.util.List;

import kr.or.ddit.vo.BoardBookVO;
import kr.or.ddit.vo.PagingInfoVO;

public interface IBoardBookDAO {
	
	public int insertBoardBook(BoardBookVO boardBook);
	
	public long selectTotalRecord(PagingInfoVO<BoardBookVO> pagingVO);
	
	public BoardBookVO selectBoardBook(long bo_no);
	
	public List<BoardBookVO> selectBoardBookList(PagingInfoVO<BoardBookVO> pagingVO);
	
	public int updateBoardBook(BoardBookVO boardBook);
	
	public int deleteBoardBook(long bo_no);
	
	
}
