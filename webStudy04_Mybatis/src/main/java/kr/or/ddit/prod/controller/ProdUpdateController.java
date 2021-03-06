package kr.or.ddit.prod.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.ProdVO;

@CommandHandler
public class ProdUpdateController {
	IProdService service = new ProdServiceImpl();
	IOtherDAO otherDAO = new OtherDAOImpl();

	public void makeLprodList(HttpServletRequest req) {
		List<Map<String, Object>> lprodList = otherDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
	}
	
	
	@URIMapping(value="/prod/prodUpdate.do", method=HttpMethod.GET)
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		makeLprodList(req);
		//수정하기 위해 기존의 상품정보가 담겨있어야 한다 
		String prod_id = req.getParameter("what");
		//어떤 상품인지 가져와야 되서 what이라는 파라미터로 가져온다
		if(StringUtils.isBlank(prod_id)) {//prod_id가 null인지 검증
			//만약 null이라면 에러 메세지를 보내준다
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		ProdVO prod = service.retrieveProd(prod_id);
		req.setAttribute("prod", prod);
		return "prod/prodForm";
	}
	
	@URIMapping(value="/prod/prodUpdate.do", method=HttpMethod.POST)
	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		makeLprodList(req);
		ProdVO prod = new ProdVO();//commandObject
		req.setAttribute("prod", prod);//form에서 같은 이름으로 가져오고 있다
		try {
			BeanUtils.populate(prod, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		
		Map<String, String>errors = new HashMap<>();
		req.setAttribute("errors", errors);//객체일때만 setattribute가능하고 값 즉 객체가 아닐땐 불가능
		boolean valid = validate(prod,errors);//callbyreference
		String view = null;
		if(valid) {
			if(req instanceof FileUploadRequestWrapper) {
				String prodImagesUrl = "/prodImages";
				String prodImagesPath = req.getServletContext().getRealPath(prodImagesUrl);
				File prodImagesFolder = new File(prodImagesPath);
				if(!prodImagesFolder.exists()) {
					prodImagesFolder.mkdirs();
				}
				FileItem fileItem = ((FileUploadRequestWrapper) req).getFileItem("prod_image");
				if(fileItem!=null) {
					String savename=UUID.randomUUID().toString();
					File	saveFile=new File(prodImagesFolder,savename);
					try(
							InputStream in= fileItem.getInputStream();
							){
							FileUtils.copyInputStreamToFile(in, saveFile);
					}
					prod.setProd_img(savename);
				}
			}
			ServiceResult result = service.modifyProd(prod);//callbyreference로 인해 pk가 채워져 있다
			if(ServiceResult.OK.equals(result)) {
				view = "redirect:/prod/prodView.do?what=" + prod.getProd_id();
			}else {
				req.setAttribute("message", "서버 오류이니 잠시 후 다시 시도해주세요");
				view = "prod/prodForm";
			}
		}else {
			view = "prod/prodForm";
		}
		return view;
	}
			
	
	private boolean validate(ProdVO prod, Map<String, String> errors) {
		boolean valid = true;
		
		if (StringUtils.isBlank(prod.getProd_id())) {
			valid = false;
			errors.put("prod_id", "상품코드 누락");
		}
		if (StringUtils.isBlank(prod.getProd_name())) {
			valid = false;
			errors.put("prod_name", "상품명 누락");
		}
		if (prod.getProd_cost() == null) {
			valid = false;
			errors.put("prod_cost", "매입가 누락");
		}
		if (prod.getProd_price() == null) {
			valid = false;
			errors.put("prod_price", "판매가 누락");
		}
		if (prod.getProd_sale() == null) {
			valid = false;
			errors.put("prod_sale", "특가 누락");
		}
		if (StringUtils.isBlank(prod.getProd_outline())) {
			valid = false;
			errors.put("prod_outline", "상품정보 누락");
		}
		if (prod.getProd_totalstock() == null) {
			valid = false;
			errors.put("prod_totalstock", "토탈스톡 누락");
		}
		if (prod.getProd_properstock() == null) {
			valid = false;
			errors.put("prod_properstock", "프롭스톡 누락");
		}

		return valid;
	}

}
