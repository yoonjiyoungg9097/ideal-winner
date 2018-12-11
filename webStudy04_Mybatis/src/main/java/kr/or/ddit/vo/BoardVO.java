package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Alias("boardVO")
public class BoardVO implements Serializable {
	
	public BoardVO(Long bo_no, String bo_pass) {
		super();
		this.bo_no = bo_no;
		this.bo_pass = bo_pass;
	}
	private Long rnum;
	private Long bo_no;
	private Integer bo_level;
	private Long bo_parent;
	private String bo_writer;
	private String bo_pass;
	private String bo_ip;
	private String bo_mail;
	private String bo_title;
	private String bo_content;
	private String bo_date;
	private Long bo_hit;
	private Long bo_rcmd;
	
	private List<PdsVO> pdsList;
	private List<ReplyVO> replyList;
	private Long[] delFiles;
	
	private List<FileItem> itemList;
	public void setItemList(List<FileItem> fileItems) {
		this.itemList = itemList;
		List<PdsVO>pdsList = null;
		if(fileItems!=null) {
			pdsList = new ArrayList<>();
			for (FileItem item : fileItems) {
				pdsList.add(new PdsVO(item));
		
			}
			this.pdsList = pdsList;
		}
	}
	
	
	
	
}
