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
			File pds_file = new File(folder, pdsVO.getPds_savename());
			resp.setContentType("application/octet-stream");
			String agent = req.getHeader("User-Agent");
			if(StringUtils.containsIgnoreCase(agent, "msie")||StringUtils.containsIgnoreCase(agent, "trident")) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}else {
				fileName=new String(fileName.getBytes("UTF-8"),"ISO-8859-1");//비ms계열??
			}
			resp.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\"");
			//생성한 파일을 읽어와서 내보줘야함
			FileUtils.copyFile(pds_file, resp.getOutputStream());
		}
		return null;
	}

}
