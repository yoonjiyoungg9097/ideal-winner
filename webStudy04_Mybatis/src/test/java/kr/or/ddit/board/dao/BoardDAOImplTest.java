package kr.or.ddit.board.dao;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BoardDAOImplTest {
	static Logger logger = LoggerFactory.getLogger(BoardDAOImplTest.class);
	
	IBoardDAO boardDAO;
	PagingInfoVO<BoardVO> pagingVO;
	BoardVO board;
	SqlSession session;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
//		logger.debug("{} 테스트 클래스 로딩", BoardDAOImplTest.class.getSimpleName());
		System.out.printf("%s 테스트 클래스 로딩\n", BoardDAOImplTest.class.getSimpleName());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
//		logger.debug("{} 테스트 클래스 unloading", BoardDAOImplTest.class.getSimpleName());
		System.out.printf("%s 테스트 클래스 unloading\n", BoardDAOImplTest.class.getSimpleName());
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("단위 테스트 모듈 수행 전!!");
		boardDAO = new BoardDAOImpl();
		pagingVO = new PagingInfoVO<>();
		pagingVO.setSearchType("content");
		pagingVO.setSearchWord("은대");
		board = new BoardVO();
		board.setBo_no(new Long(238));
		board.setBo_title("수정할 제목");
		board.setBo_content("수정할 내용");
		session = CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession(false);
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("단위 테스트 모듈 수행 후!!!!!!!!!!!!");
		session.close();
	}

	@Test
	public void testInsertBoard() {
		board.setBo_writer("김은대");
		board.setBo_pass("1004");
		board.setBo_ip("192.168.205.232");
		board.setBo_mail("pyoedab@lycos.co.kr");
		int rowCnt = boardDAO.insertBoard(board, session);
		assertEquals(1, rowCnt);
	}

	@Test
	public void testSelectTotalRecord() {
		long totalRecord = boardDAO.selectTotalRecord(pagingVO);
		assertNotSame(0, totalRecord);
	}

	@Test
	public void testSelectBoardList() {
		pagingVO.setCurrentPage(1);
		List<BoardVO> boardList = boardDAO.selectBoardList(pagingVO);
		assertNotNull(boardList);
		System.out.println(boardList.size());
		assertNotSame(0, boardList.size());
		assertThat(boardList.size(), greaterThan(0));
		BoardVO test = new BoardVO();
		test.setBo_no((long)238);
		test.setBo_writer("김은대");
		assertThat(boardList, hasItem(test));
		
	}

	@Test
	public void testSelectBoard() {
		BoardVO board = boardDAO.selectBoard(238);
		assertNotNull(board);
//		assertNull("조회된 글은 null 이 아닌것 같다", board);
		assertThat(board, instanceOf(BoardVO.class));
		assertThat(board, notNullValue());
	}

//	@Test(timeout=1000, expected=SQLException.class)
	@Test(timeout=1000)
	public void testIncrementHit() {
		boardDAO.incrementHit(238); 
	}

	@Test
	public void testUpdateBoard() {
		int rowCnt = boardDAO.updateBoard(board, session);
		assertEquals(1, rowCnt);
	}

	@Test
	public void testDeleteBoard() {
		int rowCnt = boardDAO.deleteBoard(238, session);
		assertEquals(1, rowCnt);
	}

}
