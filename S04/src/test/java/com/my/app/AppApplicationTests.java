package com.my.app;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.my.app.dao.UserAccountDao;
import com.my.app.pojo.UserAccount;

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
		List<UserAccount> uList = userAccountDao.findAllUserAccount();
		for (UserAccount u : uList) {
			System.out.println(u);
		}
	}
}
