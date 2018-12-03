package kr.or.ddit.buyer.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BuyerDAOImpl implements IBuyerDAO{

	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
	
	@Override
	public List<BuyerVO> selectBuyerList(PagingInfoVO pagingVO) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
		){
				IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
				return mapper.selectBuyerList(pagingVO);
		}
	}

	@Override
	public long selectTotalRecord() {
		try(
				SqlSession session = sqlSessionFactory.openSession();
		){
				IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
				return mapper.selectTotalRecord();
		}
	}

	@Override
	public BuyerVO selectBuyer(String buyer_id) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
				//자동 커밋해주는데 기본값이 false
		){
				IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
				return mapper.selectBuyer(buyer_id);
		}
	}

	@Override
	public int insertBuyer(BuyerVO buyer) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
		){
				IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
				int result = mapper.insertBuyer(buyer);
				if(result>0) session.commit();
				return result;
		}
	}

	@Override
	public int deleteBuyer(String buyer_id) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
		){
				IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
				int result = mapper.deleteBuyer(buyer_id);
				if(result>0) session.commit();
				return result;
		}
	}

	@Override
	public int updateBuyer(BuyerVO buyer) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
		){
				IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
				int result = mapper.updateBuyer(buyer);
				if(result>0) session.commit();
				return result;
		}
	}
}
