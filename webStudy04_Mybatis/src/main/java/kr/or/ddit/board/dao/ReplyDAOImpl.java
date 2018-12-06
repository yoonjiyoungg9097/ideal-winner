package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;

public class ReplyDAOImpl implements IReplyDAO {
	
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public int insertReply(ReplyVO reply) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			IReplyDAO mapper = session.getMapper(IReplyDAO.class);
			int result = mapper.insertReply(reply);
			if(result>0) session.commit();
			return result;
		}
	}

	@Override
	public long selectTotalRecord(PagingInfoVO<ReplyVO> pagingVO) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
			){
				IReplyDAO mapper = session.getMapper(IReplyDAO.class);
				return mapper.selectTotalRecord(pagingVO);
			}
	}

	@Override
	public List<ReplyVO> selectReplyList(PagingInfoVO<ReplyVO> pagingVO) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
			){
				IReplyDAO mapper = session.getMapper(IReplyDAO.class);
				return mapper.selectReplyList(pagingVO);
			}
	}

	@Override
	public ReplyVO selectReply(long req_no) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			IReplyDAO mapper = session.getMapper(IReplyDAO.class);
			return mapper.selectReply(req_no);
		}
	}

	@Override
	public int updateReply(ReplyVO reply) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			IReplyDAO mapper = session.getMapper(IReplyDAO.class);
			int result = mapper.updateReply(reply);
			if(result>0) session.commit();
			return result;
		}
	}

	@Override
	public int deleteReply(long req_no) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			IReplyDAO mapper = session.getMapper(IReplyDAO.class);
			int result = mapper.deleteReply(req_no);
			if(result>0) session.commit();
			return result;
		}
	}

}
