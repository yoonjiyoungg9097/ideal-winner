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
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReplyVO selectReply(long req_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateReply(ReplyVO reply) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteReply(long req_no) {
		// TODO Auto-generated method stub
		return 0;
	}

}
