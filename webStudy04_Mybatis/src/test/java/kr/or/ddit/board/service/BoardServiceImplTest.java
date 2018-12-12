package kr.or.ddit.board.service;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.BoardException;
import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.PdsVO;

import static org.hamcrest.Matchers.*;

public class BoardServiceImplTest {
	static Logger logger = LoggerFactory.getLogger(BoardServiceImplTest.class);
	
	IBoardService service;
	PagingInfoVO<BoardVO> pagingVO;
	BoardVO board;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.printf("%s 테스트 클래스 로딩\n", BoardServiceImplTest.class.getSimpleName());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.printf("%s 테스트 클래스 unloading\n", BoardServiceImplTest.class.getSimpleName());
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("단위 테스트 모듈 수행 전!!");
		service = new BoardServiceImpl();
		pagingVO = new PagingInfoVO<>();
		pagingVO.setSearchType("content");
		pagingVO.setSearchWord("은대");
		board = new BoardVO();
		board.setBo_no(new Long(238));
		board.setBo_title("수정할 제목");
		board.setBo_content("수정할 내용");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("단위 테스트 모듈 수행 후!!!!!!!!!!!!");
	}

	@Test
	public void testCreateBoard() {
		board.setBo_writer("김은대");
		board.setBo_pass("1004");
		board.setBo_ip("192.168.205.232");
		board.setBo_mail("pyoedab@lycos.co.kr");
		ServiceResult result = service.createBoard(board);
		assertEquals(ServiceResult.OK, result);
	}

	@Test
	public void testRetriveBoardCount() {
		long boardCount = service.retriveBoardCount(pagingVO);
		assertNotSame(0, boardCount);
	}

	@Test
	public void testRetriveBoardList() {
		pagingVO.setCurrentPage(1);
		List<BoardVO> boardList = service.retriveBoardList(pagingVO);
		assertNotNull(boardList);
		System.out.println(boardList.size());
		assertNotSame(0, boardList.size());
	}

	@Test(expected=BoardException.class)
	public void testRetriveBoard() {
		BoardVO board = service.retriveBoard(238);
		assertNotNull(board);
	}

	@Test(expected=BoardException.class)
	public void testModifyBoard() {
		ServiceResult result = service.modifyBoard(board);
		assertEquals(ServiceResult.OK, result);
	}

	@Test(expected=BoardException.class)
	public void testRemoveBoard() {
		board.setBo_no((long)238);
		board.setBo_pass("8888");
		ServiceResult result = service.removeBoard(board);
		assertEquals(result, ServiceResult.INVALIDPASSWORD);
	}

	@Test
	public void testDownloadPds() {
		PdsVO pds = service.downloadPds(23);
		assertNotNull(pds);
	}

	@Test
	public void testIncrementRcmd() {
		service.incrementRcmd(300);
	}

}
