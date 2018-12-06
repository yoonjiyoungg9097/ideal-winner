package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.dao.BoardDAOImpl;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.board.dao.IPdsDAO;
import kr.or.ddit.board.dao.PdsDAOImpl;
import kr.or.ddit.buyer.dao.IBuyerDAO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.PdsVO;

public class BoardServiceImpl implements IBoardService {
	
	IBoardDAO boardDAO = new BoardDAOImpl();
	IPdsDAO pdsDAO = new PdsDAOImpl();

	@Override
	public ServiceResult createBoard(BoardVO board) {
		return null;
	}

	@Override
	public long retriveBoardCount(PagingInfoVO<BoardVO> pagingVO) {
		return boardDAO.selectTotalRecord(pagingVO);
	}

	@Override
	public List<BoardVO> retriveBoardList(PagingInfoVO<BoardVO> pagingVO) {
		return boardDAO.selectBoardList(pagingVO);
	}

	@Override
	public BoardVO retriveBoard(long bo_no) {
		BoardVO board = boardDAO.selectBoard(bo_no);
		if(board==null) {
			throw new CommonException();
		}
		boardDAO.incrementHit(bo_no); //조회할때마다 조회수가 올라가야 하기 때문에 db에 조회수를 1증가시켜줌 리턴타입이 없기 때문에
		board.setBo_hit(board.getBo_hit()+1); // 현재 조회수가 0이라면 vo에 0이 담겨있기 때문에 실제 db값과 다르므로 기존의 가져온 vo의 조회수를 1증가시켜줘서
		//리턴해준다.
		return board;
	}

	@Override
	public ServiceResult modifyBoard(BoardVO board) {
		return null;
	}

	@Override
	public ServiceResult removeBoard(BoardVO board) {
		return null;
	}

	@Override
	public PdsVO downloadPds(long pds_no) {
		return null;
	}

}
