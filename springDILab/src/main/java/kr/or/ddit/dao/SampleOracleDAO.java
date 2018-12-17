package kr.or.ddit.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository("oracleDAO")
public class SampleOracleDAO implements ISampleDAO{
	private static final Logger logger = LoggerFactory.getLogger(SampleOracleDAO.class);
	
	public SampleOracleDAO() {
		super();
		logger.info("{} 생성됐음", getClass().getSimpleName());
	}

	public void init(){
		logger.info("{}가 초기화되었음", getClass().getSimpleName());
	}
	
	public void destroy(){
		logger.info("{}가 소멸되었음", getClass().getSimpleName());
	}


	public String selectSampleData(String pk){
		return String.format("%s를 받아서 %s에서 데이터를 조회한다", pk, getClass().getSimpleName());
	}
}
