package kr.co.iei.book.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.iei.book.common.JDBCTemplate;
import kr.co.iei.book.model.vo.Member;

public class MemberDao {

	public int insertMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "insert into member_tbl values(member_tbl_seq.nextval,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setInt(4, member.getMemberGrade());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public Member loginMember(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member member = null;
		String qeury = "select * from member_tbl where member_id = ? and member_pw=?";
		try {
			pstmt = conn.prepareStatement(qeury);
			pstmt.setString(1, m.getMemberId());
			pstmt.setString(2, m.getMemberPw());
			rset = pstmt.executeQuery();
			if(rset.next()) {
				member = new Member();
				member.setMemberGrade(rset.getInt("member_grade"));
				member.setMemberId(rset.getString("member_id"));
				member.setMemberName(rset.getString("member_name"));
				member.setMemberPw(rset.getString("member_pw"));
				member.setNemberNo(rset.getInt("member_no"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return member;
	}

}
