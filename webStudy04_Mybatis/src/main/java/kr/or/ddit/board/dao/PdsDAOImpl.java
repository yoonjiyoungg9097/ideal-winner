package kr.or.ddit.board.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PdsVO;

public class PdsDAOImpl implements IPdsDAO {
	
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public int insertPds(PdsVO pds) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
				){
				IPdsDAO mapper = session.getMapper(IPdsDAO.class);
				int result = mapper.insertPds(pds);
				session.commit();
				return result;
		}
	}
	
	@Override
	public int insertPdsList(BoardVO board, SqlSession session) {
//		IPdsDAO mapper = session.getMapper(IPdsDAO.class);
//		int result = mapper.insertPdsList(board, session);
//		return result;
		return session.insert("kr.or.ddit.board.dao.IPdsDAO.insertPdsList", board);
	}
	

	@Override
	public PdsVO selectPds(long pds_no) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
				){
			IPdsDAO mapper = session.getMapper(IPdsDAO.class);
			return mapper.selectPds(pds_no);
		}
	}

	@Override
	public int deletePds(long pds_no, SqlSession session) {
		return session.delete("kr.or.ddit.board.dao.IPdsDAO.deletePds",pds_no);
	}

	@Override
	public int deletePdses(BoardVO board, SqlSession session) {
		return 0;
	}

}
