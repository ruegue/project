package org.zerock.controller;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ruegue.project.dao.UserDao;
import org.ruegue.project.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class UserDaoImplTest {
	@Autowired
	UserDao userDao;
	
	@Test
	public void updateUser() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(2021, 1,1);
		
		userDao.deleteAll();
		UserDto user = new UserDto("asdf4", "1234", "abc", "aaa@aaa.com",new Date(cal.getTimeInMillis()), "fb", new Date(0));
		int rowCnt = userDao.insertUser(user);
		assertTrue(rowCnt==1);
		
		user.setPwd("4321");
		user.setEmail("bbb@bbb.com");
		rowCnt = userDao.updateUser(user);
		
		assertTrue(rowCnt==1);
		
		UserDto user2 = userDao.selectUser(user.getId());
		System.out.println("user = " + user);
		System.out.println("user = " + user2);
		assertTrue(user.equals(user2));
		
	}
}
