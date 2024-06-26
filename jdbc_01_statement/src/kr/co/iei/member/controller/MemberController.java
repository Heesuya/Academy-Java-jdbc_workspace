package kr.co.iei.member.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import kr.co.iei.member.model.vo.Member;


//프로젝트 만들때마다 ojdbc 라이브러리에 추가해야함 
public class MemberController {
	private Scanner sc;

	public MemberController() {
		super();
		sc = new Scanner(System.in);
	}

	public void main() {
		while(true) {
			System.out.println("\n-------- 회원 관리 프로그램 v1 --------\n");
			System.out.println("1. 전체 회원 조회");		//select
			System.out.println("2. 아이디로 회원 조회");		//select	일치
			System.out.println("3. 이름으로 회원 조회");		//select	포함된
			System.out.println("4. 회원 가입");			//insert
			System.out.println("5. 회원 정보 수정");		//update
			System.out.println("6. 회원 탈퇴");			//delete
			System.out.println("0. 프로그램 종료");
			System.out.print("선택 >> ");
			int select = sc.nextInt();
			
			switch(select) {
			case 1:
				selectAllMember();
				break;
			case 2:
				selectMemberId();
				break;
			case 3:
				selectMemberName();
				break;
			case 4:
				insertMember();
				break;
			case 5:
				updateMember();
				break;
			case 6:
				deleteMember();
				break;
			case 0:
				return;
			default : 
				System.out.println("잘못입력하셨습니다.");
				 break;
			}//switch  종료
		}//전체메뉴 while 종료
	}//main() 종료
	
	//전체 회원 정보를 출력하는 메소드
	public void selectAllMember() {
		//1. 이 메소드를 구현하려면 DB작업이 필요한지 ? O
		//2. 이 메소드 구현을 위해 필요한 쿼리문 작성 : select * from member_tbl;
		//3. 2번에서 작성한 쿼리문에 사용자가 입력한 값이 들어가는지 ? X
		//3번에서 사용자에게 입력받아야하는 데이터가 있으면 입력 
		//								  없으면 DB작업 실행 
		//4. DB작업 수행결과로 어떤타입데이터가 필요한지 
		// 지급하려는 작업은 회원정보 여러개를 저장 
		// 회원1명은 저장할꺼면 Member / 회원이 여러명 Member[] 
		//배열을 사용하려면 회원이 몇명인지를 먼저 조회한 후 그 길이에 맞춰서 사용
		//길이제한이 없는 ArrayList<Member>로 데이터를 저장 
		
		//0-1. 반환할 객체 미리 선언 
		Connection conn = null; //2번에서 사용할 객체
		Statement stmt = null;  //3번에서 사용할 객체
		ResultSet rset = null;  //4,5에서 사용할 객체(수행할 쿼리문 종류가 select인 경우에만) 
		//0-2. 쿼리문 수행 결과를 처리할 객체 생성 
		ArrayList<Member> list = new ArrayList<Member>(); //결과 저장할 변수 만든거~
		//0-3. 쿼리문 작성(쿼리문을 문자열 형식으로 작성) : 쿼리문 작성 시 ;은 반드시 제거 
		String query = "select * from member_tbl";
		try {
			//1. 드라이버등록(데이터베이스와 연결할 때 사용할 클래스를 등록 -> 해당 클래스는 ojdbc6.jar이 제공)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2. Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.01:1521:xe","jdbc_test","1234");  //thin이라는 방식으로 접속하겠다. 127.0.01:1521 == localhost 
			//3. 쿼리문을 수행하고 결과를 받아올 객체를 생성(Statement)
			stmt = conn.createStatement();
			//4. 쿼리문 수행하고 결과 받아옴 
			//stmt.execute(query); : 매개변수로 전달한 쿼리를 수행하고 결과를 받아옴(select 전용메소드)
			//5. 4번에서 수행한 결과를 저장
			rset = stmt.executeQuery(query);
			//select쿼리의 수행 결과가 rset에 저장되어있음 
			//ResultSet객체 타입으로 데이터가 있으면 사용이 불편 
			//사용하기 좋은 형태로 변환 -> ArrayList<Member>타입으로 데이터를 편집 
			//ResultSet에는 커서라는 객체가 있고 커서를 통해서 데이터를 편집 
			//rest.next(); : 커서를 아래로 한줄 내려서 데이터가 있으면 true / 없으면 false 
			
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String memberPw = rset.getString("member_pw");
				String memberName = rset.getString("member_name");
				String memberPhone = rset.getString("member_phone");
				int memberAge = rset.getInt("member_age");
				char memberGender = rset.getString("member_gender").charAt(0); //문자로 변환해서 써야함. 
				Date enrollDate = rset.getDate("enroll_date");
				Member m = new Member(memberId, memberPw, memberName, memberPhone, memberAge, memberGender, enrollDate);
				list.add(m);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				//6. 자원반환 
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("\n---------- 전체 회원 조회 ----------\n");
		System.out.println("아이디\t비밀번호\t이름\t전화번호\t\t나이\t성별\t가입일");
		System.out.println("-------------------------------------------------------");
		for(Member m : list) {
			System.out.println(m.getMemberId()+"\t"+m.getMemberPw()+"\t"+m.getMemberName()+"\t"+m.getMemberPhone()+"\t"
					+m.getMemberAge()+"\t"+m.getMemberGender()+"\t"+m.getenrollDate() );
		}
	}//selecetAllMember() 종료
	
	//아이디로 회원을 조회해서 정보를 출력하는 메소드
	public void selectMemberId() {
		//1. 이 메소드 구현을 위해서 DB 작업이 필요한지? O
		//2. 사용할 쿼리 : select * from member_tbl where member_id='입력한 아이디';
		//3. 쿼리문 완성을 위해서 사용자에게 받아야 할 데이터가 있는지? O
		//필요한 데이터를 입력받은 후 DB작업 수행 
		System.out.println("---------- 아이디로 회원 조회 ----------\n");
		System.out.print("조회할 회원 아이디를 입력하세요 : ");
		String searchId = sc.next();
		//필요한 데이터 보두 받았으니까 DB작업 수행
		
		//0-1. 반환할 객체 미리 선언 
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		//0-2. 데이터를 저장할 객체
		//쿼리문의 조건절에서 member_id를 사용
		//member_id는 member_tbl의 primary key -> 조건으로 검색될 수 있는 최대 인원은 1명
		//Member로 처리 -> 초기값은 조회 실패했을 때로 세팅
		Member m = null;
		//0-3. 쿼리문 작성
		String query = "select * from member_tbl where member_id='"+searchId+"'";
		
		try {
			//1. 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2. Connection 객체 생성

			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","jdbc_test","1234");
			//3. statement 객체 생성
			stmt = conn.createStatement();
			//4. 쿼리문 수행하고 결과 받아옴
			//5. 받아온 결과를 저장 
			rset = stmt.executeQuery(query);
			//5-1. 조회 결과를 우리가 사용하기 쉬운 형태로 변환
			//조건절이 member_id이므로 조회결과는 1row or 0row 밖에없음
			//->커서를 내려서 확인하는 작업을 1번만 수행하면 결과 처리 완료 
			if(rset.next()) {
				String memberId = rset.getString("member_id");
				String memberPw = rset.getString("member_pw");
				String memberName = rset.getString("member_name");
				String memberPhone = rset.getString("member_phone");
				int memberAge = rset.getInt("member_age");
				char memberGender = rset.getString("member_gender").charAt(0);
				Date enrollDate = rset.getDate("enroll_date");
				m = new Member(memberId, memberPw, memberName, memberPhone, memberAge, memberGender, enrollDate);
				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				//6. 자원반환
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		 if(m == null) {
			 System.out.println("회원 정보를 조회 할 수 없습니다.");
		 }else {
			 System.out.println("아이디 : "+m.getMemberId());
			 System.out.println("비밀번호 : "+m.getMemberPw());
			 System.out.println("이름 : "+m.getMemberName());
			 System.out.println("전화번호 : "+m.getMemberPhone());
			 System.out.println("나이 : "+m.getMemberAge());
			 System.out.println("성별 : "+m.getMemberGender());
			 System.out.println("가입일 : "+m.getenrollDate());
		 }
		 
	}//selectMemberId() 종료
	
	//이름을 입력받아서 입력받은 이름이 포함된 회원 모두 조회.
	//단, 조회된 회원이 1명도 없으면 회원 정보를 찾을 수 없습니다. 출력 
	public void selectMemberName() {
		//DB 작업해야하는지 -> O
		//query : select * from member_tbl where member_name like '%입력받은이름%';
		System.out.println("\n---------- 이름으로 회원 조회 ----------\n");
		System.out.print("조회 할 회원 이름 입력 : ");
		String searchName = sc.next();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		ArrayList<Member> list =  new ArrayList<Member>();
		
		String query = "select * from member_tbl where member_name like '%"+searchName+"%'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc_test", "1234");
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			while(rset.next()) {     //컬럼의 순서로도 가져올수있다 (단, 순서를 맞춰야 함) //헷갈려서 이 방식 사용 안함.
				String memberId = rset.getString(1); 
				String memberPw = rset.getString(2);
				String memberName = rset.getString(3);
				String memberPhone = rset.getString(4);
				int memberAge = rset.getInt(5);
				char memberGender = rset.getString(6).charAt(0);
				Date enrollDate = rset.getDate(7);
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
		
		if(list.isEmpty()) {
			System.out.println("회원 정보를 조회 할 수 없습니다.");
		}else {
			System.out.println("아이디\t비밀번호\t이름\t휴대번호\t\t나이\t성별\t가입일");
			for(Member m : list) {
				System.out.println(m.getMemberId()+"\t"+m.getMemberPw()+"\t"+m.getMemberName()
				+"\t"+m.getMemberPhone()+"\t"+m.getMemberAge()+"\t"+m.getMemberGender()+"\t"+m.getenrollDate());
			}
		}
	}//selectMemberName() 종료
	
	
	//회원 정보를 입력받아서 데이터베이스에 insert하는 메소드 
	public void insertMember() {
		//DB 작업 필요한지 -> O
		//query : insert into member_tbl values('아이디','비밀번호','이름','전화번호',나이,'성별',sysdate);
		//쿼리문 수행을 위해서 받아야할 정보 : 아이디,비밀번호,이름,전화번호,나이,성별 / 가입일은 자동으로 입력
		System.out.println("\n---------- 회정 정보 등록 ----------\n");
		System.out.print("아이디 입력 : ");
		String memberId = sc.next();
		System.out.print("비밀번호 입력 : ");
		String memberPw = sc.next();
		System.out.print("이름 입력 : ");
		String memberName = sc.next();
		System.out.print("전화번호 입력[ex. 010-0000-0000] : ");
		String memberPhone = sc.next();
		System.out.print("나이 입력 : ");
		int memberAge = sc.nextInt();
		System.out.print("성별 입력[남/여] : ");
		char memberGender = sc.next().charAt(0);
		
		Connection conn = null;
		Statement stmt = null;
		//ResultSet rset = null;//select를 수행할때만 사용 -> 이번에 사용할 쿼리는 insert
		//insert/update/delete쿼리는 쿼리수행으로 인해 변경된 행의 숫자가 결과로 리턴
		int result = 0;
		String query ="insert into member_tbl values("
				+"'"+memberId+"',"
				+"'"+memberPw+"',"
				+"'"+memberName+"',"
				+"'"+memberPhone+"',"
				+memberAge+","
				+"'"+memberGender+"',"
				+"sysdate)";
		
		try {
			//1.
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2.
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc_test", "1234");
			//3.
			stmt = conn.createStatement();
			//insert/update/delete는 쿼리수행할때 executeUpdate()를 사용
			//executeUpdate()메소드는 insert/update/delete 쿼리를 수행하고 반영된 행의 수를 리턴
			//4.5.
			result = stmt.executeUpdate(query);// executeUpdate >> 테이블에 변화를 줬다고 생각하면 됨. 
			//쿼리를 통해서 반영된 행의 수가 0이 아니다 -> 테이블에 데이터 변경사항이 발생했다
			//-> insert가 성공 -> commit
			//쿼리를 통해서 반영된 행의 수가 0이면 -> 테이블에 데이터 변경사항이 없다
			//-> insert가 실패 -> rollback    // 지금 테이블 한개써서 transjection  하게 되면 삭제된 정보 저장 한것도 rollback 해야함.  
			if(result > 0) { //result == 1 도 가능하지만 공통적으로 1보다 크다. 
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
		if(result > 0) {
			System.out.println("회원 가입 완료!");
		}else {
			System.out.println("회원 가입 실패!");
		}
	}//insertMember() 종료
	
	//아이디를 입력받아서 데이터베이스에서 삭제하는 메소드 
	public void deleteMember() {
		System.out.println("\n---------- 회원 정보 삭제 ----------\n");
		//DB작업필요한지? O
		//query : delte from member_tbl where momber_id = '입력받은아이디';
		//쿼리실행 시 정보입력받아야 하는지 -> 아이디
		System.out.print("삭제할 회원 아이디 입력 : ");
		String deleteId = sc.next();
		
		Connection conn = null;
		Statement stmt = null;
		
		String query = "delete from member_tbl where member_id ='"+deleteId+"'";
		
		int result = 0; 
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
		if(result > 0) {
			System.out.println("회원 삭제 성공!");
		}else{
			System.out.println("회원 삭제 실패!");
		}
	}//deleteMember() 종료
	
	//데이터베이스에 비밀번호, 이름, 전화번호를 수정하는 메소드 
	public void updateMember() {
		System.out.println("\n---------- 회원 정보 수정 ----------\n");
		//DB작업필요한지 -> O
		//query : update member_tbl set member_pw = '비밀번호', member_name='이름', member_phone='전화번호' where member_id = '아이디'; 
		//입력받을 정보 : 아이디, 비밀번호, 이름, 전화번호 
		System.out.print("정보를 수정 할 회원 아이디 입력 : ");
		String memberId = sc.next();
		//해당아이디의 회원이 존재하는지 체크 select * from member_tbl where member_id='아이디';
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		//Member m = null; //rset에 데이터가 있는지 없는지만 확인하면 됨. //이전 비밀번호가 같으면 안바꿔줄랭
		int result = 0;
		
		//String query = "select * from member_tbl where member_id='"+memberId+"'";
		String query = "select count(*) as cnt from member_tbl where member_id='"+memberId+"'";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "jdbc_test", "1234");
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			int cnt = rset.getInt("cnt");//별칭으로 된거를 인식하기 위해서 , 함수가 복작할수록 길어지기에 복잡해지면 별칭으로 꺼낼수 있다. 
			//if(rset.next()) {
				//조회가 된건 해당아이디 회원이 존재 -> 수정로직 진행
			if(cnt == 1) {
				System.out.print("수정 할 비밀번호 입력 : ");
				String memberPw = sc.next();
				System.out.print("수정 할 이름 입력 : ");
				String memberName = sc.next();
				System.out.print("수정 할 전화번호 입력[010-0000-0000] : ");
				String memberPhone = sc.next();
				query = "update  member_tbl set "
						+"member_pw = '"+memberPw+"',"
						+"member_name = '"+memberName+"',"
						+"member_phone = '"+memberPhone+"' "
						+"where member_id = '"+memberId+"'";
				result = stmt.executeUpdate(query);
				if(result > 0) {
					conn.commit();
				}else{
					conn.rollback();
				}
			}else {
				System.out.println("회원 정보를 조회 할 수 없습니다.");
				return;
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
		if(result > 0) {
			System.out.println("변경 성공");
		}else{
			System.err.println("변경 실패");
		}
	}//updateMember() 종료 
	
	
}//클래스 종료
