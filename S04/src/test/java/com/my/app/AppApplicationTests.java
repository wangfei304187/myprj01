package com.my.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.my.app.dao.UserAccountDao;

@SpringBootTest
class AppApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserAccountDao userAccountDao;

//    @Before
//    public void setUp() {
//    }

    @Test
    public void test() throws Exception {
//    	userAccountDao.printAll();
//    	userAccountDao.printAllUserAccount();
    	userAccountDao.findAllUserAccount();
    }
}
