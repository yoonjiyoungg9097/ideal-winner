package kr.or.ddit.vo;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionDIVO {
	private String[] sampleArray;
	private List<?> sampleList; //private List<Object>
	private Map<?, ?> sampleMap;
	private Set<?> sampleSet;
	private Properties sampleProps;
}
