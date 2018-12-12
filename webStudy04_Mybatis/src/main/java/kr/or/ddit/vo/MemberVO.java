package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.codec.binary.Base64;
import org.apache.ibatis.type.Alias;

import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.validator.rules.constraints.Length;
import kr.or.ddit.validator.rules.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Alias("memberVO")
@Data
@ToString(exclude= {"mem_img"})
@NoArgsConstructor
@EqualsAndHashCode(of= {"mem_id", "mem_regno1", "mem_regno2"})
public class MemberVO implements Serializable, HttpSessionBindingListener{
	
	
	public MemberVO(String mem_id, String mem_pass) {
		super();
		this.mem_id = mem_id;
		this.mem_pass = mem_pass;
	}
	
	@NotBlank(message="아이디 필수")
	private String mem_id;
	@NotBlank(message="비번 필수")
	@Length(min=4, max=8)
	private String mem_pass;
	@NotBlank(message="이름 필수", groups=InsertGroup.class)
	private String mem_name;
	@NotBlank(groups=InsertGroup.class)
	private String mem_regno1;
	@NotBlank(groups=InsertGroup.class)
	private String mem_regno2;
	private String mem_bir;
	@NotBlank
	private String mem_zip;
	@NotBlank
	private String mem_add1;
	@NotBlank
	private String mem_add2;
	private String mem_hometel;
	private String mem_comtel;
	@NotBlank
	private String mem_hp;
	private String mem_mail;
	private String mem_job;
	private String mem_like;
	private String mem_memorial;
	private String mem_memorialday;
	private Long mem_mileage;
	private String mem_delete;
	private String mem_auth;
	private byte[] mem_img;
	public String getMem_imgToBase64(){
		if(mem_img==null) {
			return null;
		}else {
			return Base64.encodeBase64String(mem_img);
		}
	}
	//구매 상품 목록
	private List<ProdVO> prodList;
	
	public String getAddress() {
		return Objects.toString(mem_add1,"")+" "+Objects.toString(mem_add2,"");
	}
	

	
	public String getAddressTest() {
		return "테스트";
	}


	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		if("authMember".equals(event.getName())){
			ServletContext application = event.getSession().getServletContext();
			Set<MemberVO> applicationUsers = (Set<MemberVO>)application.getAttribute("applicationUsers");
			applicationUsers.add(this);
		}
	}



	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		if("authMember".equals(event.getName())){
			ServletContext application = event.getSession().getServletContext();
			Set<MemberVO> applicationUsers = (Set<MemberVO>)application.getAttribute("applicationUsers");
			applicationUsers.remove(this);
		}
	}
	
}
