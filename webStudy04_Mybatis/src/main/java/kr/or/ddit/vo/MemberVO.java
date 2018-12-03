package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Alias("memberVO")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of= {"mem_id", "mem_regno1", "mem_regno2"})
public class MemberVO implements Serializable{
	
	
	public MemberVO(String mem_id, String mem_pass) {
		super();
		this.mem_id = mem_id;
		this.mem_pass = mem_pass;
	}
	private String mem_id;
	private String mem_pass;
	private String mem_name;
	private String mem_regno1;
	private String mem_regno2;
	private String mem_bir;
	private String mem_zip;
	private String mem_add1;
	private String mem_add2;
	private String mem_hometel;
	private String mem_comtel;
	private String mem_hp;
	private String mem_mail;
	private String mem_job;
	private String mem_like;
	private String mem_memorial;
	private String mem_memorialday;
	private Long mem_mileage;
	private String mem_delete;
	private String mem_auth;
	//구매 상품 목록
	private List<ProdVO> prodList;
	
	public String getAddress() {
		return Objects.toString(mem_add1,"")+" "+Objects.toString(mem_add2,"");
	}
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((mem_id == null) ? 0 : mem_id.hashCode());
//		result = prime * result + ((mem_regno1 == null) ? 0 : mem_regno1.hashCode());
//		result = prime * result + ((mem_regno2 == null) ? 0 : mem_regno2.hashCode());
//		return result;
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		MemberVO other = (MemberVO) obj;
//		if (mem_id == null) {
//			if (other.mem_id != null)
//				return false;
//		} else if (!mem_id.equals(other.mem_id))
//			return false;
//		if (mem_regno1 == null) {
//			if (other.mem_regno1 != null)
//				return false;
//		} else if (!mem_regno1.equals(other.mem_regno1))
//			return false;
//		if (mem_regno2 == null) {
//			if (other.mem_regno2 != null)
//				return false;
//		} else if (!mem_regno2.equals(other.mem_regno2))
//			return false;
//		return true;
//	}
//	@Override
//	public String toString() {
//		return "MemberVO [mem_id=" + mem_id + ", mem_pass=" + mem_pass + ", mem_name=" + mem_name + ", mem_regno1="
//				+ mem_regno1 + ", mem_regno2=" + mem_regno2 + ", mem_bir=" + mem_bir + ", mem_zip=" + mem_zip
//				+ ", mem_add1=" + mem_add1 + ", mem_add2=" + mem_add2 + ", mem_hometel=" + mem_hometel + ", mem_comtel="
//				+ mem_comtel + ", mem_hp=" + mem_hp + ", mem_mail=" + mem_mail + ", mem_job=" + mem_job + ", mem_like="
//				+ mem_like + ", mem_memorial=" + mem_memorial + ", mem_memorialday=" + mem_memorialday
//				+ ", mem_mileage=" + mem_mileage + ", mem_delete=" + mem_delete + "]";
//	}
	
	public String getAddressTest() {
		return "테스트";
	}
	
}
