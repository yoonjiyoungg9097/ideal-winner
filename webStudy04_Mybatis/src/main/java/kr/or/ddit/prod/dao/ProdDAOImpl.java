package kr.or.ddit.prod.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ProdVO;

public class ProdDAOImpl implements IProdDAO {
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
	
	@Override
	public int insertProd(ProdVO prod) {
		try(
				SqlSession session = sqlSessionFactory.openSession(false);
			){
				IProdDAO mapper = session.getMapper(IProdDAO.class);
				int rowCnt = mapper.insertProd(prod);
				session.commit();
				return rowCnt;
		}
	}

	@Override
	public ProdVO selectProd(String prod_id) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
			){
				IProdDAO mapper = session.getMapper(IProdDAO.class);
				return mapper.selectProd(prod_id);
		}
	}

	@Override
	public long selectTotalRecord(PagingInfoVO<ProdVO> pagingVO) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
			){
				IProdDAO mapper = session.getMapper(IProdDAO.class);
				return mapper.selectTotalRecord(pagingVO);
		}
	}

	@Override
	public List<ProdVO> selectProdList(PagingInfoVO<ProdVO> pagingVO) {
		try(
				SqlSession session = sqlSessionFactory.openSession();
			){
				IProdDAO mapper = session.getMapper(IProdDAO.class);
				return mapper.selectProdList(pagingVO);
		}
	}

	@Override
	public int updateProd(ProdVO prod) {
		try(
			SqlSession session = sqlSessionFactory.openSession(false);
		){
			IProdDAO mapper = session.getMapper(IProdDAO.class);
			int result = mapper.updateProd(prod);
			if(result>0) session.commit();
			return result;
		}
	}
	

}
