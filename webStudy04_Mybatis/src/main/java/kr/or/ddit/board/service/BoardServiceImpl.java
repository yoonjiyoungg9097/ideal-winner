package kr.or.ddit.board.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.executor.ResultExtractor;
import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.BoardException;
import kr.or.ddit.board.dao.BoardDAOImpl;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.board.dao.IPdsDAO;
import kr.or.ddit.board.dao.PdsDAOImpl;
import kr.or.ddit.buyer.dao.IBuyerDAO;
import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.PdsVO;
import sun.nio.fs.DefaultFileSystemProvider;

public class BoardServiceImpl implements IBoardService {
	
	IBoardDAO boardDAO = new BoardDAOImpl();
	IPdsDAO pdsDAO = new PdsDAOImpl();
	
//	private int processFiles(BoardVO board, SqlSession session) {
//		int rowCnt = 0;
//		List<PdsVO>pdsList = board.getPdsList();
//		File saveFolder = new File("d:/boardFiles");
//		if(!saveFolder.exists()) saveFolder.mkdirs();
//		if(pdsList!=null) {
////			if(1==1)
////			throw new RuntimeException("트랜잭션 관리 여부 확인을 위한 강제 예외");
//			
//			rowCnt += pdsDAO.insertPdsList(board, session);//
//			for (PdsVO pds : pdsList) {
//				try(
//						InputStream in = pds.getItem().getInputStream();
//				){
//					FileUtils.copyInputStreamToFile(in, new File(saveFolder, pds.getPds_savename()));
//				}catch (IOException e) {
//				}
//			}
//		}
//		
//		Long[] delFiles = board.getDelFiles();
//		if(delFiles!=null) {
//			check += delFiles.length;
//			String[] saveNames = new String[delFiles.length];
//			for(int idx=0; idx<delFiles.length; idx++) {
//				saveNames[idx] = pdsDAO.selectPds(delFiles[idx]).getPds_savename();
//			}
//			rowCnt += pdsDAO.deletePds(board, session);
//			
//			for(String savename : saveNames) {
//				FileUtils.deleteQuietly(new File(saveFolder, savename));
//			}
//		}
//		return rowCnt;
//	}
	
	private ServiceResult pdsInsert(int cnt, SqlSession session, BoardVO board) {
	      ServiceResult result = ServiceResult.FAILED;
	      int count = 1;
	      int totalCount=0;
	      String saveUrl = "d:/boardFiles";
	      File saveFolder = new File(saveUrl);// 폴더 생성해줌
	      if (!saveFolder.exists())
	         saveFolder.mkdirs();

	      if (cnt > 0) {
	         if (board.getPdsList() != null) {
	            count += pdsDAO.insertPdsList(board, session);
	            for (PdsVO pds : board.getPdsList()) {

	               try (InputStream in = pds.getItem().getInputStream();) {
	                  FileUtils.copyInputStreamToFile(in, new File(saveFolder, pds.getPds_savename()));
	               } catch (IOException e) {
	                  e.printStackTrace();
	               }
	            }
	            totalCount=board.getPdsList().size();
	         }
	         if (board.getDelFiles() != null) {
	            for (Long pds_no : board.getDelFiles()) {
	               PdsVO pdsVO= pdsDAO.selectPds(pds_no);
	               if(pdsVO!=null) {
	                  File delFile=new File(saveFolder,pdsVO.getPds_savename());
	                  System.out.println("요기는??111");
	                  System.out.println(delFile.exists());
	                  int check=pdsDAO.deletePds(pds_no, session);
	                  if(check>0) {
	                     if(delFile.delete()) {
	                        count+=check;
	                     }
	                  }
	               }
	            }
	            totalCount+=board.getDelFiles().length;
	         } 
	         if (count >= totalCount) {

	            result = ServiceResult.OK;
	            session.commit();

	         }
	      }
	      return result;

	   }

	@Override
	public ServiceResult createBoard(BoardVO board) {
		try (SqlSession session = CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession(false);) {

	         int cnt = boardDAO.insertBoard(board, session);

	         return pdsInsert(cnt, session, board);
	      }
	}

	@Override
	public long retriveBoardCount(PagingInfoVO<BoardVO> pagingVO) {
		return boardDAO.selectTotalRecord(pagingVO);
	}

	@Override
	public List<BoardVO> retriveBoardList(PagingInfoVO<BoardVO> pagingVO) {
		return boardDAO.selectBoardList(pagingVO);
	}

	@Override
	public BoardVO retriveBoard(long bo_no) {
		BoardVO board = boardDAO.selectBoard(bo_no);
		if(board==null) {
			throw new CommonException();
		}
		boardDAO.incrementHit(bo_no); //조회할때마다 조회수가 올라가야 하기 때문에 db에 조회수를 1증가시켜줌 리턴타입이 없기 때문에
		board.setBo_hit(board.getBo_hit()+1); // 현재 조회수가 0이라면 vo에 0이 담겨있기 때문에 실제 db값과 다르므로 기존의 가져온 vo의 조회수를 1증가시켜줘서
		//리턴해준다.
		return board;
	}

	@Override
	public ServiceResult modifyBoard(BoardVO board) {
		ServiceResult result;
	      try (SqlSession session = CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession(false);) {
	         BoardVO  boardVO=boardDAO.selectBoard(board.getBo_no());
	         if(boardVO!=null && boardVO.getBo_pass().equals(board.getBo_pass())) {
	            int cnt = boardDAO.updateBoard(board, session);
	            result= pdsInsert(cnt, session, board);
	         }else {
	            result=ServiceResult.INVALIDPASSWORD;
	         }


	         return result;
	      }
	}

	@Override
	public ServiceResult removeBoard(BoardVO board) {
		return null;
	}

	@Override
	public PdsVO downloadPds(long pds_no) {
		PdsVO pds = new PdsVO();
		pds = pdsDAO.selectPds(pds_no);
		if(pds==null) {
			throw new BoardException();
		}
		return pds;
	}

}
