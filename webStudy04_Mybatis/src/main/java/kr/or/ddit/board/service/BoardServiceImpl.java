package kr.or.ddit.board.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.BoardException;
import kr.or.ddit.board.dao.BoardDAOImpl;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.board.dao.IPdsDAO;
import kr.or.ddit.board.dao.PdsDAOImpl;
import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.PdsVO;

public class BoardServiceImpl implements IBoardService {
	IBoardDAO boardDAO = new BoardDAOImpl();
	IPdsDAO pdsDAO = new PdsDAOImpl();
	File saveFolder = new File("d:/boardFiles");
	{
		if (!saveFolder.exists())
			saveFolder.mkdirs();
	}

	private int processFiles(BoardVO board, SqlSession session) {
		int rowCnt = 0;
		List<PdsVO> pdsList = board.getPdsList();

		if (pdsList != null) {
//         if(1==1)
//         throw new RuntimeException("트랜잭션 관리 여부 확인을 위한 강제 예외");

			rowCnt += pdsDAO.insertPdsList(board, session);
			for (PdsVO pds : pdsList) {
				try (InputStream in = pds.getItem().getInputStream();) {
					FileUtils.copyInputStreamToFile(in, new File(saveFolder, pds.getPds_savename()));
				} catch (IOException e) {
					// TODO: handle exception
				}
			}
		}

		Long[] delFiles = board.getDelFiles();
		if (delFiles != null) {

			String[] saveNames = new String[delFiles.length];
			for (int idx = 0; idx < delFiles.length; idx++) {
				saveNames[idx] = pdsDAO.selectPds(delFiles[idx]).getPds_savename();
			}
			rowCnt += pdsDAO.deletePdses(board, session);
			// 파일 삭제
			for (String savename : saveNames) {
				FileUtils.deleteQuietly(new File(saveFolder, savename));
			}

		}

		return rowCnt;
	}

	@Override
	public ServiceResult createBoard(BoardVO board) {
		try (SqlSession session = CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession(false);) {
			int rowCnt = boardDAO.insertBoard(board, session);
			int check = 1;
			if (rowCnt > 0) {
				if (board.getPdsList() != null)
					check += board.getPdsList().size();
				rowCnt += processFiles(board, session);
			}
			ServiceResult result = ServiceResult.FAILED;
			if (rowCnt >= check) {
				result = ServiceResult.OK;
				session.commit();
			}
			return result;
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
		if (board == null) {
			throw new BoardException(bo_no + "에 해당하는 게시글이 없음");
		}
		boardDAO.incrementHit(bo_no);
		return board;
	}

	@Override
	public ServiceResult modifyBoard(BoardVO board) {
		try (SqlSession session = CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession(false);) {
			BoardVO savedBoard = retriveBoard(board.getBo_no());
			ServiceResult result = null;
			if (savedBoard.getBo_pass().equals(board.getBo_pass())) {
				int rowCnt = boardDAO.updateBoard(board, session);
				int check = rowCnt;
				if (rowCnt > 0) {
					if (board.getPdsList() != null)
						check += board.getPdsList().size();
					if (board.getDelFiles() != null)
						check += board.getDelFiles().length;
					rowCnt += processFiles(board, session);
				}
				if (rowCnt >= check) {
					session.commit();
					result = ServiceResult.OK;
				} else {
					result = ServiceResult.FAILED;
				} // rowCnt 체크 if end
			} else {
				result = ServiceResult.INVALIDPASSWORD;
			} // 비번 체크 if end
			return result;
		}
	}

	@Override
	public ServiceResult removeBoard(BoardVO board) {
		ServiceResult result = ServiceResult.INVALIDPASSWORD;
		try (SqlSession session = CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession(false);

		) {
			BoardVO boardVO = retriveBoard(board.getBo_no());
			if (boardVO != null && (board.getBo_pass().equals(boardVO.getBo_pass()))) {
				int cnt = boardDAO.deleteBoard(board.getBo_no(), session);
				if (cnt > 0) {
					List<PdsVO> pdsList = boardVO.getPdsList();
					if (pdsList != null) {
						for (PdsVO pds : pdsList) {

							FileUtils.deleteQuietly(new File(saveFolder, pds.getPds_savename()));

						}
					}
					result = ServiceResult.OK;
					session.commit();
				} else {
					result = ServiceResult.FAILED;
				}

			}
		}
		return result;

	}

	@Override
	public PdsVO downloadPds(long pds_no) {
		PdsVO pds = pdsDAO.selectPds(pds_no);
		if (pds == null) {
			throw new BoardException(pds_no + "에 해당 파일이 없음.");
		}
		return pds;
	}

	@Override
	public BoardVO incrementRcmd(long bo_no) {
		BoardVO board = retriveBoard(bo_no);
		boardDAO.incrementRcmd(bo_no);
		return board;
	}

}