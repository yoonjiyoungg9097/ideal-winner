package kr.or.ddit.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * totalRecord 와 currentPage를 결정하면, 나머지 속성들이 연산됨
 * setTotalRecord / setCurrentPage 호출 필요.
 */

@Alias("pagingVO")
@Data
@NoArgsConstructor
public class PagingInfoVO<T> {
	
	public PagingInfoVO(int screenSize, int blockSize) {
		super();
		this.screenSize = screenSize;
		this.blockSize = blockSize;
	}
	private long totalRecord;
	private int screenSize = 10;
	private int blockSize = 5;  
	private int currentPage;
	private long totalPage;
	private long startPage;
	private long endPage;
	private long startRow;
	private long endRow;
	private List<T> dataList; //T는 generic type 변수
	private T searchVO; //타입이 결정되어 있지 않다
	private String searchWord;
	private String searchType;
	private String funcName = "paging";
	
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
		totalPage = totalRecord%screenSize==0?totalRecord/screenSize:totalRecord/screenSize+1;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		endRow = currentPage * screenSize;
		startRow = endRow - (screenSize-1);
		startPage = (currentPage-1)/blockSize * blockSize + 1;
		endPage = startPage + (blockSize -1);
	}
	public String getPagingHTML() {

	      StringBuffer html= new StringBuffer();
//	      String pattern="<li class='page-item %s'><a class='page-link' href='?page=%d'>%s</a></li>";
	      String pattern="<li class='page-item %s'><a class='page-link' href='javascript:"+funcName+"(%d)'>%s</a></li>";
	      html.append("<ul class='pagination'>");
	      if(startPage>1) {
	         
	         html.append(String.format(pattern,"" ,(startPage-1),"이전"));
	         
	      }
	      if(endPage>totalPage) {
	         endPage=totalPage;
	      }
	      for (long page=startPage; page<=endPage; page++) {
	         String active="";
	         String pageStr=page+"";
	         if(page==currentPage) {
	            active="active";
	            pageStr+=" <span class='sr-only'>(current)</span>";
	         }
	         html.append(String.format(pattern, active,(page),pageStr));
	      }
	      if(endPage<totalPage) {
	         html.append(String.format(pattern, "",(endPage+1),"다음"));
	      }
	      html.append("</ul>");
	      return html.toString();
	   
		
	}
}
