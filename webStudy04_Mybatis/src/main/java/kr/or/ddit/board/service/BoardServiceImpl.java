package kr.or.ddit.board.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.BoardException;
import kr.or.ddit.board.dao.BoardDAOImpl;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.board.dao.IPdsDAO;
import kr.or.ddit.board.dao.PdsDAOImpl;
import kr.or.ddit.buyer.dao.IBuyerDAO;
import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.PdsVO;

public class BoardServiceImpl implements IBoardService {
	
	IBoardDAO boardDAO = new BoardDAOImpl();
	IPdsDAO pdsDAO = new PdsDAOImpl();

	@Override
	public ServiceResult createBoard(BoardVO board) {
		try(
			SqlSession session = 
					CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession(false);//한번에 커밋해주는데 false는 우리가 직접ㄱ커밋
			){
			int result = boardDAO.insertBoard(board, session);
			ServiceResult serviceResult = ServiceResult.FAILED;
			int check = 1;//커밋을 해주기 위한 조건
			File saveFolder = new File("d:/boardFiles");
			if(!saveFolder.exists()) saveFolder.mkdirs();
			if(result>0) {
				List<PdsVO>pdsList = board.getPdsList();
				if(pdsList!=null) {
//					if(1==1)
//					throw new RuntimeException("트랜잭션 관리 여부 확인을 위한 강제 예외");
					check += pdsList.size();//? 3
					result += pdsDAO.insertPdsList(board, session);//
					for (PdsVO pds : pdsList) {
						try(
								InputStream in = pds.getItem().getInputStream();
						){
							FileUtils.copyInputStreamToFile(in, new File(saveFolder, pds.getPds_savename()));
						}catch (IOException e) {
						}
					}
				}
			}//완벽하지 않으면 커밋해주지 않는다는다 트랜잭션
			if(result>=check) {//중간에 하나라도 빠지면 커밋을 방지해주는 부분 완전한 게시물이 아니기 때문에 쿼리문 사이즈와 결과값 비교
				serviceResult = ServiceResult.OK;
				session.commit();
			}
			
			return serviceResult;
			}
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
		PdsVO pds = new PdsVO();
		pds = pdsDAO.selectPds(pds_no);
		if(pds==null) {
			throw new BoardException();
		}
		return pds;
	}

}
