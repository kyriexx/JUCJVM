package com.luban.implproxy;

import com.luban.dao.UserDao;

/**
 * @author Kyrie
 * @create 2020-01-09 17:15
 */
public class UserDaoAndTimeImpl implements UserDao {

    private UserDao userDao;

    public UserDaoAndTimeImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public void add(int i, int j) {
        System.out.println("****time****");
        userDao.add(i,j);
    }

    @Override
    public String query() {
        return null;
    }
}
