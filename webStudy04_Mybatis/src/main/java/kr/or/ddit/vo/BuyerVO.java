package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("buyerVO")
@Data 
@NoArgsConstructor 
public class BuyerVO implements Serializable{
	private String buyer_delete;
	private String buyer_id;
	private String buyer_name;
	private String buyer_lgu;
	private String buyer_bank;
	private String buyer_bankno;
	private String buyer_bankname;
	private String buyer_zip;
	private String buyer_add1;
	private String buyer_add2;
	private String buyer_comtel;
	private String buyer_fax;
	private String buyer_mail;
	private String buyer_charger;
	private String buyer_telext;
}
