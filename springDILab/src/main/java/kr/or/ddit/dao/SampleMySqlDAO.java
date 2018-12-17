package kr.or.ddit.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

//@Repository("mySqlDAO")
public class SampleMySqlDAO implements ISampleDAO {

	private static final Logger logger = LoggerFactory.getLogger(SampleMySqlDAO.class);

	public SampleMySqlDAO() {
		super();
		logger.info("{} 생성됐음", getClass().getSimpleName());
	}

	public StringBuffer selectSampleData(String pk) {
		StringBuffer result = new StringBuffer();
		result.append(String.format("%s를 받아 %s에서 조회한 데이터.", pk, getClass().getSimpleName()));
		return result;
	}
}
