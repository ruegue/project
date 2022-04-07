package org.ruegue.project.service;

import java.util.List;
import java.util.Map;

import org.ruegue.project.dao.BoardDao;
import org.ruegue.project.domain.BoardDto;
import org.ruegue.project.domain.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDao boardDao;
	
	public int getCount() throws Exception {
		return boardDao.count();
	}
	
	@Override
    public int remove(Integer bno, String writer) throws Exception {
        return boardDao.delete(bno, writer);
    }

    @Override
    public int write(BoardDto boardDto) throws Exception {
        return boardDao.insert(boardDto);
    }

    @Override
    public List<BoardDto> getList() throws Exception {
        return boardDao.selectAll();
    }

    @Override
    public BoardDto read(Integer bno) throws Exception {
        BoardDto boardDto = boardDao.select(bno);
        boardDao.increaseViewCnt(bno);

        return boardDto;
    }

    @Override
    public List<BoardDto> getPage(Map map) throws Exception {
        return boardDao.selectPage(map);
    }

    @Override
    public int modify(BoardDto boardDto) throws Exception {
        return boardDao.update(boardDto);
    };
    
    @Override
    public List<BoardDto> getSearchResultPage(SearchCondition sc) throws Exception{
    	return boardDao.searchSelectPage(sc);
    };
    
    @Override
    public int getSearchResultCnt(SearchCondition sc) throws Exception{
    	return boardDao.searchResultCnt(sc);
    };
}
