package org.ruegue.project.dao;

import org.apache.ibatis.session.SqlSession;
import org.ruegue.project.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


//@Component
@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
    private SqlSession session;
    private static String namespace = "org.ruegue.project.dao.UserMapper.";

    @Override
    public int deleteUser(String id) throws Exception {
       return session.delete(namespace+"delete",id);
    }

    @Override
    public UserDto selectUser(String id) throws Exception {
       return session.selectOne(namespace+"select", id);
    }

    // 사용자 정보를 user_info테이블에 저장하는 메서드
    @Override
    public int insertUser(UserDto user) throws Exception {
        return session.insert(namespace+"insert",user);
    }

    @Override
    public int updateUser(UserDto user) throws Exception {
       return session.update(namespace+"update",user);
    }

    @Override
    public int count() throws Exception {
       return session.selectOne(namespace+"count");
    }

    @Override
    public int deleteAll() throws Exception {
       return session.delete(namespace+ "deleteAll");
    }
}