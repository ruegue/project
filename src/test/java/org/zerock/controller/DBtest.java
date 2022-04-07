package org.zerock.controller;


import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ruegue.project.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBtest {
	
	@Autowired
	DataSource ds;
	
	
//	@Test
//	public void insetUserTest() throws Exception {
//		User user = new User("asdf2", "1234", "abc", "aaaa@aaa.com", new Date(0), "fb", new Date(0));
//		deleteAll();
//		int rowCnt = insertUser(user);
//		
//		assertTrue(rowCnt==1);
//	}
//	
//	@Test
//	public void selectUserTest() throws Exception {
//		deleteAll();
//		User user = new User("asdf2", "1234", "abc", "aaaa@aaa.com", new Date(0), "fb", new Date(0));
//		int rowCnt = insertUser(user);
//		User user2 = selectUser("asdf2");
//		
//		assertTrue(user2.getId().equals("asdf2"));
//	}
//	
//	@Test
//	public void deleteUserTest() throws Exception {
//		deleteAll();
//		int rowCnt = deleteUser("asdf");
//		
//		assertTrue(rowCnt==0);
//		
//		User user = new User("asdf2", "1234", "abc", "aaaa@aaa.com", new Date(0), "fb", new Date(0));
//		rowCnt = insertUser(user);
//		assertTrue(rowCnt==1);
//		
//		rowCnt = deleteUser(user.getId());
//		assertTrue(rowCnt==1);
//		
//		assertTrue(selectUser(user.getId())==null);
//	}
//	
//	@Test
//	public void UqdateUserTest() throws Exception {
//		deleteAll();
//		User user = new User("asdf2", "1234", "abc", "aaaa@aaa.com", new Date(0), "fb", new Date(0));
//		int rowCnt = insertUser(user);
//		
//		assertTrue(rowCnt==1);
//		
//		User user2 = new User("asdf2", "4321", "abc", "aaaa@aaa.com", new Date(0), "fb", new Date(0));
//		rowCnt = UpdateUser(user2);
//		
//		assertTrue(rowCnt==1);
//	}
	
	
	public int UpdateUser(UserDto user) throws Exception {
		
		Connection conn = ds.getConnection();
		
		String sql = "update user_info set pwd = ? , name = ?, email = ?, birth = ?, sns = ?, reg_date = ?" + "where id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, user.getPwd());
		pstmt.setString(2, user.getName());
		pstmt.setString(3, user.getEmail());
		pstmt.setDate(4, new java.sql.Date(user.getBirth().getTime()));
		pstmt.setString(5, user.getId());
		pstmt.setTimestamp(6, new java.sql.Timestamp(user.getReg_date().getTime()));
		pstmt.setString(7, user.getId());
		
		int rowCnt = pstmt.executeUpdate();
		
		return rowCnt;
	}
	
	public int deleteUser(String id) throws Exception {
		
		Connection conn = ds.getConnection();
		
		String sql = "delete from user_info where id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		
		return pstmt.executeUpdate();
		
		
	}
	
	public UserDto selectUser(String id) throws Exception {
		
		Connection conn = ds.getConnection();
		
		String sql = "select * from user_info where id=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			UserDto user = new UserDto();
			user.setId(rs.getString(1));
			user.setPwd(rs.getString(2));
			user.setName(rs.getString(3));
			user.setEmail(rs.getString(4));
			user.setBirth(new Date(rs.getDate(5).getTime()));
			user.setSns(rs.getString(6));
			user.setReg_date(new Date(rs.getDate(7).getTime()));
			
			return user;
		}
		
		return null;
	}
	
	private void deleteAll() throws Exception {
		Connection conn = ds.getConnection();
		
		String sql = "delete from user_info";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		int rowCnt = pstmt.executeUpdate();
	}

	public int insertUser(UserDto user) throws Exception {
		
		Connection conn = ds.getConnection();
	   
		String sql = "insert into user_info values(?,?,?,?,?,?,now())";
	    
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1,user.getId());
	    pstmt.setString(2,user.getPwd());
	    pstmt.setString(3,user.getName());
	    pstmt.setString(4,user.getEmail());
	    pstmt.setDate(5,new java.sql.Date(user.getBirth().getTime()));
	    pstmt.setString(6,user.getSns());
		
		int rowCnt = pstmt.executeUpdate();
	    
	    return rowCnt;
	}
	
	@Test
	public void tansactionTest() throws Exception {
		
		Connection conn = null; 
		try {
			deleteAll();
			conn = ds.getConnection();
			conn.setAutoCommit(false);
   
			String sql = "insert into user_info values(?,?,?,?,?,?,now())";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,"asdf");
			pstmt.setString(2,"1234");
			pstmt.setString(3,"abc");
			pstmt.setString(4,"aaa@aaa.com");
			pstmt.setDate(5,new java.sql.Date(new Date(0).getTime()));
			pstmt.setString(6,"fb");
			
			int rowCnt = pstmt.executeUpdate();
			
			pstmt.setString(1, "asdf");
			rowCnt = pstmt.executeUpdate();
			
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}
	    
	}
	
	@Test
	public void main() throws Exception{
		  
//		ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//	    DataSource ds = ac.getBean(DataSource.class);

	    Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.
	    
	    
	    System.out.println("conn = " + conn);
	    assertTrue(conn!=null);
	}

}
