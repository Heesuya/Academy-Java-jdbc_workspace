package kr.co.iei.member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import kr.co.iei.member.model.vo.Member;

//DAO(Data Access Object) : 데이터베이스에 접근해서 데이터를 쿼리를 수행하고 결과를 리턴하는 기능을 모아둠
public class MemberDao {
	//dao의 메소드 1개당 1개의 쿼리만 수행하고 결과를 리턴 

	public Member selectMemberId(String searchId) {
		//0-1. 반환할 객체 미리 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		//0.2. 쿼리 수행결과를 저장할 변수 선언 / 컨트롤러로 되돌려줄 객체
		//query : select * from member_tbl where member_id ='아이디';
		Member m = null;//조회 성공하면 회원 1명정보 저장한 Member객체/실패하면 null
		//0-3. 쿼리문 작성
		String query = "select * from member_tbl where member_id = '"+searchId+"'";
		
		try {
			//1. 사용할 클래스 지정
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2. Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc_test", "1234");
			//3. Statement 객체 생성
			stmt = conn.createStatement();
			//4. 쿼리문 수행하고 결과 받아옴
			//5. 받아온 결과를 저장 
			rset = stmt.executeQuery(query);
			if(rset.next()) {
				m = new Member();
				m.setEnrollDate(rset.getDate("enroll_date"));
				m.setMemberAge(rset.getInt("member_age"));
				m.setMemberGender(rset.getString("member_gender"));
				m.setMemberId(rset.getString("member_id"));
				m.setMemberName(rset.getString("member_name"));
				m.setMemberPhone(rset.getString("member_phone"));
				m.setMemberPw(rset.getString("member_pw"));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return m;
	}

	public ArrayList<Member> selectAllMember()  {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		//가끔 순서가 원하는대로 안나올때가 있음 (컴퓨터 메모리사항에 따라 조회가 달라짐) >> oder by로 해결
		String query = "select * from member_tbl";
		
		ArrayList<Member> list = new ArrayList<Member>();
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc_test", "1234");
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
		while(rset.next()) {
			//Member m = new Member();
			//m.setEnrollDate(rset.getString("enroll_date")); 
			//위와 같은 형식으로 넣는게 편함 
				String memberId = rset.getString("member_id");
				String memberPw = rset.getString("member_pw");
				String memberName = rset.getString("member_name");
				String memberPhone = rset.getString("member_phone");
				int memberAge = rset.getInt("member_age");
				String memberGender = rset.getString("member_gender");
				Date enrollDate = rset.getDate("enroll_date");
				Member m = new Member(memberId, memberPw, memberName, memberPhone, memberAge, memberGender, enrollDate);
				list.add(m);
		}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list;
	}

	public static ArrayList<Member> memberSearchName(String searchName) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		String query = "select * from member_tbl where member_name like '%"+searchName+"%'";
		ArrayList<Member> list = new ArrayList<Member>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc_test", "1234");
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			while(rset.next()) {
				Member m = new Member(rset.getString("member_id"), rset.getString("member_pw"), rset.getString("member_name"), rset.getString("member_phone"), rset.getInt("member_age"), 
							rset.getString("member_gender"), rset.getDate("enroll_date"));
				list.add(m);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	public int insertMember(Member m) {
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		String query = "insert into member_tbl values("
				+"'"+m.getMemberId()+"',"
				+"'"+m.getMemberPw()+"',"
				+"'"+m.getMemberName()+"',"
				+"'"+m.getMemberPhone()+"',"
				+"'"+m.getMemberAge()+"',"
				+"'"+m.getMemberGender()+"',"
				+"sysdate)";
		//System.out.println(query);  //쿼리문 오타.. 확인용
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc_test", "1234");
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			if(result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int updateSearchMember(String memberId) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset= null;
		int result = 0;
		
		String query = "select count(*) as cnt from member_tbl where member_id = '"+memberId+"'";
		System.out.println(query);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc_test", "1234");
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			rset.next();
			result = rset.getInt("cnt");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int updateMember(String memberId, String memberPw, String memberName, String memberPhone) {
		Connection conn = null;
		Statement stmt = null;
		//ResultSet rset = null;
		int result = 0;
		
		String query = "update member_tbl set member_pw = '"+memberPw+"', member_name = '"+memberName+"', member_phone = '"
						+memberPhone+"' where member_id ='"+memberId+"'";
		//System.out.println(query);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc_test", "1234");
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateMember2(Member m) {
		Connection conn = null;
		Statement stmt = null;
		//ResultSet rset = null;
		int result = 0;
		
		String query = "update member_tbl set member_pw = '"+m.getMemberPw()+"', member_name = '"+m.getMemberName()+"', member_phone = '"
						+m.getMemberPhone()+"' where member_id ='"+m.getMemberId()+"'";
		//System.out.println(query);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc_test", "1234");
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int deleteMember(String memberId) {
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		
		String query = "delete from member_tbl where member_id ='"+memberId+"'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc_test", "1234");
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	
	
	
	
}
