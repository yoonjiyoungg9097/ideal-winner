package kr.or.ddit.board.dao;

import java.util.List;

import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;

/**
 * 
 * @author 윤지영
 * @since 2018. 12. 4.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 12. 4.      윤지영       덧글 관리를 위한 persistence layer
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
public interface IReplyDAO {
	/**
	 * 덧글 작성
	 * @param reply
	 * @return row count
	 */
	public int insertReply(ReplyVO reply);
	
	
	/**
	 * 특정 게시글의 전체 덧글 수
	 * @param pagingVO
	 * @return
	 */
	public long selectTotalRecord(PagingInfoVO<ReplyVO> pagingVO);
	
	
	/**
	 * 특정 게시글의 덧글 목록 조회
	 * @param pagingVO
	 * @return
	 */
	public List<ReplyVO> selectReplyList(PagingInfoVO<ReplyVO> pagingVO);
	
	
	/**
	 * 덧글 상세 조회
	 * @param req_no
	 * @return 존재하지 않는다면, null 반환
	 */
	public ReplyVO selectReply(long req_no);
	
	
	/**
	 * 덧글 수정
	 * @param reply
	 * @return row count
	 */
	public int updateReply(ReplyVO reply);
	
	
	/**
	 * 덧글 삭제
	 * @param req_no
	 * @return row count
	 */
	public int deleteReply(long req_no);
}













