package kr.or.ddit.service;

import kr.or.ddit.dao.ISampleDAO;

public interface ISampleService {

	void setSampleDAO(ISampleDAO sampleDAO);

	String retrieveSampleContent(String pk);

}