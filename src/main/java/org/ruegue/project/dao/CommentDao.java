package org.ruegue.project.dao;

import org.ruegue.project.domain.CommentDto;
import org.ruegue.project.domain.CommentPageHandler;


import java.util.List;

public interface CommentDao {
    int count(Integer bno) throws Exception // T selectOne(String statement)
    ;

    int deleteAll(Integer bno) // int delete(String statement)
    ;

    int delete(Integer cno, String commenter) throws Exception // int delete(String statement, Object parameter)
    ;

    int insert(CommentDto dto) throws Exception // int insert(String statement, Object parameter)
    ;

    List<CommentDto> selectAll(Integer bno) throws Exception // List<E> selectList(String statement)
    ;

    CommentDto select(Integer cno) throws Exception // T selectOne(String statement, Object parameter)
    ;

    int update(CommentDto dto) throws Exception // int update(String statement, Object parameter)
    ;

    List<CommentDto> selectPage(CommentPageHandler commentPageHandler) throws Exception
            ;
}
