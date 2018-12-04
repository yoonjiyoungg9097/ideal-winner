package kr.or.ddit.prod.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.ProdVO;

public class ProdInsertController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String method = req.getMethod();
		IOtherDAO otherDAO = new OtherDAOImpl();
		List<Map<String, Object>>lprodList = otherDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
		
		
		if("get".equalsIgnoreCase(method)) {//처음에 실행될때 get메서드가 실행되어서 이곳으로 오기때문에 prodForm으로 이동한다
			return "prod/prodForm";
		}else if("post".equalsIgnoreCase(method)) {
			
			ProdVO prod = new ProdVO();
			req.setAttribute("prod", prod);
			try {//wrapper-req
				BeanUtils.populate(prod, req.getParameterMap());
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			System.out.println(prod.getLprod_nm()+"dsadasd");
			Map<String, String>errors = new HashMap<>();
			req.setAttribute("errors", errors);//객체일때만 setattribute가능하고 값 즉 객체가 아닐땐 불가능
			boolean valid = validate(prod,errors);//callbyreference
			System.out.println(valid+"ddddddddddddddd");
			String view = null;
			if(valid) {
				if(req instanceof FileUploadRequestWrapper) {
					String prodImagesUrl = "/prodImages";
					String prodImagesPath = req.getServletContext().getRealPath(prodImagesUrl);
					File prodImagesFolder = new File(prodImagesPath);
					
					FileItem fileItem = ((FileUploadRequestWrapper) req).getFileItem("prod_image");
					if(fileItem!=null) {
						String savename = UUID.randomUUID().toString();
						File saveFile = new File(prodImagesFolder, savename);
						try(
							InputStream in = fileItem.getInputStream();
						){
							FileUtils.copyInputStreamToFile(in, saveFile);
							prod.setProd_img(savename);
						}
					}
				}
				
				IProdService service = new ProdServiceImpl();
				ServiceResult result = service.createProd(prod);//callbyreference로 인해 pk가 채워져 있다
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
		}else {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private boolean validate(ProdVO prod, Map<String, String> errors) {
		boolean valid = true;
		
//		if (StringUtils.isBlank(prod.getProd_id())) {
//			valid = false;
//			errors.put("prod_id", "상품코드 누락");
//		}
		if (StringUtils.isBlank(prod.getProd_name())) {
			valid = false;
			errors.put("prod_name", "상품명 누락");
		}
		if (StringUtils.isBlank(prod.getProd_lgu())) {
			valid = false;
			errors.put("prod_lgu", "품번 누락");
		}
		if (StringUtils.isBlank(prod.getProd_buyer())) {
			valid = false;
			errors.put("prod_buyer", "판매자 누락");
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
//		if (StringUtils.isBlank(prod.getProd_img())) {
//			valid = false;
//			errors.put("prod_img", "이미지 누락");
//		}
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
