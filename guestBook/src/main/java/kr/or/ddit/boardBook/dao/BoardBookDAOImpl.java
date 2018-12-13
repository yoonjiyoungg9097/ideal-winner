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
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			IBoardBookDAO mapper = session.getMapper(IBoardBookDAO.class);
			int result = mapper.insertBoardBook(boardBook);
			if(result>0) session.commit();
			return result;
		}
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
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			IBoardBookDAO mapper = session.getMapper(IBoardBookDAO.class);
			return mapper.selectBoardBook(bo_no);
		}
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
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			IBoardBookDAO mapper = session.getMapper(IBoardBookDAO.class);
			int result = mapper.updateBoardBook(boardBook);
			if(result>0)session.commit();
			return result;
		}
	}

	@Override
	public int deleteBoardBook(long bo_no) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			IBoardBookDAO mapper = session.getMapper(IBoardBookDAO.class);
			int result = mapper.deleteBoardBook(bo_no);
			if(result>0)session.commit();
			return result;
		}
	}


}
