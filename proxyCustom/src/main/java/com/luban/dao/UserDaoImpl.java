package com.luban.dao;

/**
 * @author Kyrie
 * @create 2020-01-09 17:13
 */
public class UserDaoImpl implements UserDao {
    @Override
    public void add(int i, int j) {
        System.out.println(i+j);
    }

    @Override
    public String query() {
        return "测试有返回值的";
    }
}
