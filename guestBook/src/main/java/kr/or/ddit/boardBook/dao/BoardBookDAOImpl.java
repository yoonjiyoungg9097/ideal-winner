package kr.or.ddit.boardBook.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardBookVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BoardBookDAOImpl implements IBoardBookDAO {
	
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public int insertBoardBook(BoardBookVO boardBook) {
		return 0;
//		try(
//			SqlSession session = sqlSessionFactory.openSession();
//		){
//			IBoardBookDAO mapper = session.getMapper(IBoardBookDAO.class);
//			int result = mapper.insertBoardBook(boardBook);
//			if(result>0) session.commit();
//			return result;
//		}
	}
	
	@Override
	public long selectTotalRecord(PagingInfoVO<BoardBookVO> pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			IBoardBookDAO mapper = session.getMapper(IBoardBookDAO.class);
			return mapper.selectTotalRecord(pagingVO);
		}
	}

	@Override
	public BoardBookVO selectBoardBook(long bo_no) {
		return null;
//		try(
//			SqlSession session = sqlSessionFactory.openSession();
//		){
//			IBoardBookDAO mapper = session.getMapper(IBoardBookDAO.class);
//			return mapper.selectBoardBook(bo_no);
//		}
	}
	
	@Override
	public List<BoardBookVO> selectBoardBookList(PagingInfoVO<BoardBookVO> pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			IBoardBookDAO mapper = session.getMapper(IBoardBookDAO.class);
			return mapper.selectBoardBookList(pagingVO);
		}
	}

	@Override
	public int updateBoardBook(BoardBookVO boardBook) {
		return 0;
	}

	@Override
	public int deleteBoardBook(long bo_no) {
		return 0;
	}

	@Override
	public void incrementHit(long bo_no) {
//		try(
//			SqlSession session = sqlSessionFactory.openSession();
//		){
//			IBoardBookDAO mapper = session.getMapper(IBoardBookDAO.class);
//			mapper.incrementHit(bo_no);
//		}
	}

	@Override
	public void incrementRcmd(long bo_no) {

	}



}