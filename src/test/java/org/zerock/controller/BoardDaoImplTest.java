package org.zerock.controller;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ruegue.project.dao.BoardDao;
import org.ruegue.project.domain.BoardDto;
import org.ruegue.project.domain.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BoardDaoImplTest {
	
	@Autowired
	private BoardDao boardDao;
	
	@Test
	public void searchSelectPageTest() throws Exception{
		boardDao.deleteAll();
		for(int i=1; i <= 20 ; i++) {
			BoardDto boardDto = new BoardDto("title"+i, "asdfasdfasdf", "asdf"+i);
			boardDao.insert(boardDto);
		}
		
		SearchCondition sc = new SearchCondition(1 , 10, "title2", "T");
		List<BoardDto> list = boardDao.searchSelectPage(sc);
		System.out.println("list=" + list);
		assertTrue(list.size()==2);
		
		sc = new SearchCondition(1 , 10, "asdf2", "W");
		list = boardDao.searchSelectPage(sc);
		System.out.println("list = " + list);
		assertTrue(list.size()==2);
	}
	
	@Test
	public void searchResultCntTest() throws Exception{
		 boardDao.deleteAll();
	        for (int i = 1; i <=20 ; i++){
	            BoardDto boardDto = new BoardDto("title"+i , "asfadfsdf", "asdf"+i);
	            boardDao.insert(boardDto);
	        }

	        SearchCondition sc = new SearchCondition(1,10,"title2","T");
	        int cnt = boardDao.searchResultCnt(sc);
	        System.out.println("cnt="+ cnt);
	        assertTrue(cnt==2);

	        sc = new SearchCondition(1,10,"asdf2","W");
	        cnt = boardDao.searchResultCnt(sc);
	        System.out.println("cnt="+ cnt);
	        assertTrue(cnt==2);
	}
}
