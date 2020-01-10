package com.luban.extendsproxy;

import com.luban.dao.UserDaoImplExtends;

/**
 * @author Kyrie
 * @create 2020-01-09 17:04
 */
public class UserDaoAndLogImpl extends UserDaoImplExtends {

    @Override
    public void add(int i, int j) {
        System.out.println("****log****");
        super.add(i, j);
    }
}
