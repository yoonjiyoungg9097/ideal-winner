package kr.or.ddit.board.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.vo.PdsVO;

@CommandHandler
public class DownloadController{

	@URIMapping("/board/download.do")
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String what = req.getParameter("what");
		
		//what이라는 파라미터명으로 첨부파일번호 값이 잘들어왔나 검증해준다
		long pds_no = 0;
		if(StringUtils.isNumeric(what)) {
			pds_no = Long.parseLong(what);
		}
		PdsVO pdsVO = new PdsVO();
		IBoardService service = new BoardServiceImpl();
		pdsVO = service.downloadPds(pds_no);
		
		
		String fileName= pdsVO.getPds_filename();
		if(pdsVO!=null) {
			//파일 객체 생성...<
			File folder = new File("d:/boardFiles");
			resp.setContentType("application/octet-stream");
			String agent = req.getHeader("User-Agent");
			if(StringUtils.containsIgnoreCase(agent, "msie")||StringUtils.containsIgnoreCase(agent, "trident")) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}else {
				fileName=new String(fileName.getBytes("UTF-8"),"ISO-8859-1");//비ms계열??
			}
			resp.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\"");
			//파일 가져오는거
			File pds_file = new File(folder, pdsVO.getPds_savename());//UUID로 생성해준 파일을 가져온다
			//다른 모든 경우를 위한 기본값입니다. 알려지지 않은 파일 타입은 이 타입을 사용해야 합니다.
			//확장자를 지원?
		      //원본파일명으로 셋팅해주고 다운로드 될 수 있게 한다
			//생성한 파일을 읽어와서 내보줘야함
			FileUtils.copyFile(pds_file, resp.getOutputStream());
		}
		return null;
	}

}
