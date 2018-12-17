package kr.or.ddit.service;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import kr.or.ddit.dao.ISampleDAO;

@Service
public class SampleService implements ISampleService {
	private static final Logger logger = LoggerFactory.getLogger(SampleService.class);
//	SampleOracleDAO sampleDAO = new SampleOracleDAO();
//	ISampleDAO sampleDAO = new SampleOracleDAO();
//	1. Factory method pattern
//	ISampleDAO sampleDAO = new SampleDAOFactory().getSampleDAO();
//	2. Strategy Pattern
	ISampleDAO sampleDAO;
	
	


	@Override
//	@Required
//	@Autowired(required=true)
//	@Resource(name="oracleDAO")
//	@Inject
	public void setSampleDAO(ISampleDAO sampleDAO) {
		this.sampleDAO = sampleDAO;
		logger.info("{}를 기본생성자로 생성한 후 setter 호출, {}를 받았음", getClass().getSimpleName(), sampleDAO.getClass().getSimpleName());
	}
	
	
	
	public SampleService() {
		super();
		logger.info("{} 생성, 기본생성자로.", getClass().getSimpleName());
	}



	public SampleService(ISampleDAO sampleDAO) {//기본생성자 없어짐
		super();
		this.sampleDAO = sampleDAO;
		logger.info("{} 생성, {}를 받았음.", getClass().getSimpleName(), sampleDAO.getClass().getSimpleName());
	}



	/* (non-Javadoc)
	 * @see kr.or.ddit.service.ISampleService#retrieveSampleContent(java.lang.String)
	 */
	@Override
	public String retrieveSampleContent(String pk){
		CharSequence rawData = sampleDAO.selectSampleData(pk);
		return rawData + "	를 다시 재가공한다";
	}
}
