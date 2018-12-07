package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.dao.IReplyDAO;
import kr.or.ddit.board.dao.ReplyDAOImpl;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;

public class ReplyServiceImpl implements IReplyService {
	
	IReplyDAO replyDAO = new ReplyDAOImpl();

	@Override
	public ServiceResult createReply(ReplyVO reply) {
		ServiceResult serviceResult = null;
		int result = replyDAO.insertReply(reply);
		if(result>0) {
			serviceResult = ServiceResult.OK;
		}else {
			serviceResult = ServiceResult.FAILED;
		}
		return serviceResult;
	}

	@Override
	public long retriveReplyCount(PagingInfoVO<ReplyVO> pagingVO) {
		return replyDAO.selectTotalRecord(pagingVO);
	}

	@Override
	public List<ReplyVO> retriveReplyList(PagingInfoVO<ReplyVO> pagingVO) {
		return replyDAO.selectReplyList(pagingVO);
	}

	@Override
	public ServiceResult modifyReply(ReplyVO reply) {
		ServiceResult serviceResult = null;
		ReplyVO replyVO = replyDAO.selectReply(reply.getRep_no());
		if(replyVO==null) {
			throw new CommonException();
		}
		if(replyVO.getRep_pass().equals(reply.getRep_pass())) {
			int result = replyDAO.updateReply(reply);
			if(result>0) {
				serviceResult = ServiceResult.OK;
			}else {
				serviceResult = ServiceResult.FAILED;
			}
		}else {
			serviceResult = ServiceResult.INVALIDPASSWORD;
		}
		return serviceResult;
	}

	@Override
	public ServiceResult removeReply(ReplyVO reply) {
		ServiceResult serviceResult = null;
		ReplyVO replyVO = replyDAO.selectReply(reply.getRep_no());
		if(replyVO==null) {
			throw new CommonException();
		}
		if(replyVO.getRep_pass().equals(reply.getRep_pass())) {
			int result = replyDAO.deleteReply(reply.getRep_no());
			if(result>0) {
				serviceResult = ServiceResult.OK;
			}else {
				serviceResult = ServiceResult.FAILED;
			}
		}else {
			serviceResult = ServiceResult.INVALIDPASSWORD;
		}
		return serviceResult;
	}

}
