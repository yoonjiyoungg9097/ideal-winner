package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingInfoVO;

public class MemberServiceImpl implements IMemberService {
	// 의존 객체를 직접 생성하는 방식 : 결합력 최상 실행코드의 캡슐화
	IMemberDAO memberDAO = new MemberDAOImpl();
	
	@Override
	public ServiceResult registMember(MemberVO member) {
		ServiceResult result = null;
		 if(memberDAO.selectMember(member.getMem_id())==null) {
			 int rowCnt = memberDAO.insertMember(member);
			 if(rowCnt>0) {
				 result = ServiceResult.OK;
			 }else {
				 result = ServiceResult.FAILED;
			 }
		 }else {
			result = ServiceResult.PKDUPLICATED; 
		 }
		 return result;
	}
	@Override
	public long retriveMemberCount(PagingInfoVO pagingVO) {
		return memberDAO.selectTotalRecord(pagingVO);
	}

	@Override
	public List<MemberVO> retrieveMemberList(PagingInfoVO pagingVO) {
		return memberDAO.selectMemberList(pagingVO);
	}
	
	@Override
	public MemberVO retrieveMember(String mem_id) {
		MemberVO member = memberDAO.selectMember(mem_id);
		if(member == null) {
			throw new CommonException(mem_id+"에 해당하는 회원이 없습니다");
		}
		return member;
	}

	@Override
	public ServiceResult modifyMember(MemberVO member) {
		ServiceResult result = null;
		MemberVO memberInfo = retrieveMember(member.getMem_id());
		if(memberInfo==null) {
			result = ServiceResult.PKNOTFOUND;
		}else {
			if(memberInfo.getMem_pass().equals(member.getMem_pass())) {
				int res = memberDAO.updateMember(member);
				if(res>0) {
					result = ServiceResult.OK;
				}else {
					result = ServiceResult.FAILED;
				}
			}else {
				result = ServiceResult.INVALIDPASSWORD;
			}
		}
		return result;
	}

	@Override
	public ServiceResult removeMember(MemberVO member) {
		//ServiceResult에 있는 메세지를 통해 페이지를 전달해줘야 되기 때문에 변수를 선언해준다
		ServiceResult result = null;
		//입력한 mem_id의 모든 정보를 MemberVO에 담아준다
		MemberVO memberInfo = retrieveMember(member.getMem_id());
		//만약 MemberVO가 null일때 그 회원이 존재하지 않는다는 뜻이기 때문에 PKNOTFOUND
		//null이 아닐때 존재하는 회원이기 때문에 비밀번호 일치 검사 해주고 deleteMember실행해주고 그 값을 int형 변수에 담아준다 
		//그 변수가 0보다 클때는 delete 성공 그렇지 않으면 실패
		if(memberInfo.getMem_pass().equals(member.getMem_pass())) {
			int res = memberDAO.deleteMember(member.getMem_id());
			if(res>0) {
				result = ServiceResult.OK;
			}else {
				result = ServiceResult.FAILED;
			}
		}else {
			result = ServiceResult.INVALIDPASSWORD;
		}
		return result;
	}

}
