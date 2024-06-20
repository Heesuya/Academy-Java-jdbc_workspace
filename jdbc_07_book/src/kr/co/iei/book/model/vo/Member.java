package kr.co.iei.book.model.vo;

public class Member {
	private int nemberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private int memberGrade;
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Member(int nemberNo, String memberId, String memberPw, String memberName, int memberGrade) {
		super();
		this.nemberNo = nemberNo;
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.memberGrade = memberGrade;
	}
	public int getNemberNo() {
		return nemberNo;
	}
	public void setNemberNo(int nemberNo) {
		this.nemberNo = nemberNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getMemberGrade() {
		return memberGrade;
	}
	public void setMemberGrade(int memberGrade) {
		this.memberGrade = memberGrade;
	}
	@Override
	public String toString() {
		return "Member [nemberNo=" + nemberNo + ", memberId=" + memberId + ", memberPw=" + memberPw + ", memberName="
				+ memberName + ", memberGrade=" + memberGrade + "]";
	}
	
	
	
}