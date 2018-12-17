package kr.or.ddit.dao;

public class SampleDAOFactory {
	public ISampleDAO getSampleDAO(){
//		return new SampleOracleDAO();
		return new SampleMySqlDAO();
	}
}
