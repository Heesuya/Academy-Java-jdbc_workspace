package kr.co.iei.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.iei.board.common.JDBCTemplate;
import kr.co.iei.board.model.vo.Member;

public class MemberDao {
	public Member getMember(ResultSet rset) {
		Member m = new Member();
		try {
			m.setMemberId(rset.getString("member_id"));
			m.setMemberName(rset.getString("member_name"));
			m.setMemberNo(rset.getInt("member_no"));
			m.setMemberPhone(rset.getString("member_phone"));
			m.setMemberPw(rset.getString("member_pw"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}

	public int insertMember(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		int result = 0;
		String qeury = "insert into exam_member values(exam_member_seq.nextval,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(qeury);
			pstmt.setString(1, m.getMemberId());
			pstmt.setString(2, m.getMemberPw());
			pstmt.setString(3, m.getMemberName());
			pstmt.setString(4, m.getMemberPhone());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public Member selectMemberNamePhone(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member member = null;
		String qeury = "select * from exam_member where member_name = ? and member_phone = ?";
		try {
			pstmt = conn.prepareStatement(qeury);
			pstmt.setString(1, m.getMemberName());
			pstmt.setString(2, m.getMemberPhone());
			rset = pstmt.executeQuery();
			member = new Member();
			while (rset.next()) {
				member.setMemberId(rset.getString("member_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return member;
	}

	public Member selectMemberSearch(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member member = new Member();
		String qeury = "select * from exam_member where member_id = ?";
		try {
			pstmt = conn.prepareStatement(qeury);
			pstmt.setString(1, m.getMemberId());
			rset = pstmt.executeQuery();
			while (rset.next()) {
				member.setMemberId(rset.getString("member_id"));
				member.setMemberName(rset.getString("member_name"));
				member.setMemberNo(rset.getInt("member_no"));
				member.setMemberPhone(rset.getString("member_phone"));
				member.setMemberPw(rset.getString("member_pw"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return member;
	}

	public Member login(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member memeber = null;
		String qeury = "select * from exam_member where member_id = ? and member_pw = ?";
		try {
			pstmt = conn.prepareStatement(qeury);
			pstmt.setString(1, m.getMemberId());
			pstmt.setString(2, m.getMemberPw());
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				memeber = getMember(rset);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memeber;
	}

	public String searchId(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String memberId = null;

		String qeury = "select member_id from exam_member where member_name = ? and member_phone = ?";

		try {
			pstmt = conn.prepareStatement(qeury);
			pstmt.setString(1, m.getMemberName());
			pstmt.setString(2, m.getMemberPhone());
			rset = pstmt.executeQuery();
			if (rset.next()) {
				memberId = rset.getString("member_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return memberId;
	}

//	public int updateMember(Connection conn, Member loginMember) {
//		PreparedStatement pstmt = null;
//		int result = 0;
//		
//		String query = "update exam_board set member_pw =?,member_phone=? where member_no=?";
//		try {
//			pstmt = conn.prepareStatement(query);
//			pstmt.setString(1, loginMember.getMemberPw());
//			pstmt.setString(2, loginMember.getMemberPhone());
//			pstmt.setInt(3, loginMember.getMemberNo());
//			result = pstmt.executeUpdate();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			JDBCTemplate.close(pstmt);
//		}
//		
//		return result;
//	}

}
