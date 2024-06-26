package kr.co.iei.member.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.iei.member.model.dao.MemberDao;
import kr.co.iei.member.model.model.vo.Member;

public class MemberService {

	MemberDao memberDao;

	public MemberService() {
		super();
		memberDao = new MemberDao();
	}
	// service는 Connection 객체를 답당
	// Connection 관리 : 생성, 닫기, commit, rollback

	public Member selectMemberId(String memberId) {
		// 01-1. 반환할 객체 생성
		Connection conn = null;
		// 02-2. 리턴할 객체 생성
		Member m = null;
		try {
			// 1. Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc_test", "1234");
			m = memberDao.selectMemberId(conn, memberId);
			// DB작업이 다 끝난 상태인지 체크
			// 추가적인 DB작업이 있으면 -> conn을 재사용해서 DB 작업 수행
			// 추가적인 DB작업이 없으면 -> commit, rollback 필요여부 확인
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return m;
	}// selecetMemberId();

	public int updateMember(Member member) {
		Connection conn = null;
		int result = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc_test", "1234");
			result = memberDao.updateMember(conn, member);
			if (result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}// updateMember();

	public ArrayList<Member> selectAllMember() {
		Connection conn = null;
		ArrayList<Member> list = new ArrayList<Member>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc_test", "1234");
			list = memberDao.selectAllMember(conn);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	public ArrayList<Member> selectMemberName(String next) {
		Connection conn = null;
		ArrayList<Member> list = new ArrayList<Member>();
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc_test","1234");
			list = memberDao.selectMemberName(conn, next);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
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
		int result = 0;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc_test","1234");
			result = memberDao.insertMember(conn, m);
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
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int deleteMember(String id) {
		Connection conn = null;
		int result = -1;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc_test","1234");
			conn.setAutoCommit(false);//Connection 객체가 쿼리를 수행하면 자동으로 commit 기능 해제
			Member m = memberDao.selectMemberId(conn, id);
			if(m != null ) {
				result = memberDao.deleteMember(conn, id);
				if(result > 0) {
					result = memberDao.insertDelMember(conn,m);
					if(result > 0) {
						conn.commit();											
					}else {
						conn.rollback();
					}
				}else {
					conn.rollback();
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public ArrayList<Member> selectMemberGender(String memberGender) {
		Connection conn = null;
		ArrayList<Member> list = new ArrayList<Member>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc_test","1234");
			list = memberDao.selectMemberGender(conn,memberGender);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
}// MemberServic();
