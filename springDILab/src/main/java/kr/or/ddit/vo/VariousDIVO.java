package kr.or.ddit.vo;

import java.io.File;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariousDIVO implements Serializable{
	private int intNumber;
	private double dblNumber;
	private char charVar;
	private String str;
	
	private File sampleFile;
}
