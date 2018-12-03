package kr.or.ddit.buyer.dao;

import java.util.List;

import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;

public interface IBuyerDAO {
	public List<BuyerVO> selectBuyerList(PagingInfoVO pagingVO);
	
	public long selectTotalRecord();
	
	public BuyerVO selectBuyer(String buyer_id);
	
	public int insertBuyer(BuyerVO buyer);
	
	public int deleteBuyer(String buyer_id);
	
	public int updateBuyer(BuyerVO buyer);
}
