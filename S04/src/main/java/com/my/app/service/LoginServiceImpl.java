package com.my.app.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.app.dao.UserAccountDao;
import com.my.app.pojo.UserAccount;

// Ref: https://www.jianshu.com/p/7f724bec3dc3

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserAccountDao userAccountDao;
	
    @Override
    public UserAccount getUserAccountByName(String name) {
//        return findUser(name);
    	return userAccountDao.findUserAccountCascadeByName(name);
    }

    //模拟数据库查询
//    private UserAccount findUser(String userName) {
//        Permission permission1 = new Permission("db:read,write", "");
//        Permission permission2 = new Permission("sys:write", "");
//        Permission permission3 = new Permission("sys:read", "");
//        
//        Role role1 = new Role("DBA", "");
//        Role role2 = new Role("Admin", "");
//        Role role3 = new Role("Operator", "");
//        
//        // DBA
//        role1.getPermissions().add(permission1);
//        role1.getPermissions().add(permission2);
//        role1.getPermissions().add(permission3);
//        
//        // Admin
//        role2.getPermissions().add(permission2);
//        role2.getPermissions().add(permission3);
//        
//        // Operator
//        role3.getPermissions().add(permission3);
//        
//        UserAccount user1 = new UserAccount("lili", "123456", false, false);
//        user1.getRoles().add(role1);
//        user1.getRoles().add(role2);
//        
//        UserAccount user2 = new UserAccount("lucy", "123456", false, false);
//        user2.getRoles().add(role3);
//        
//        Map<String, UserAccount> map = new HashMap<>();
//        map.put(user1.getUserName(), user1);
//        map.put(user2.getUserName(), user2);
//        
//        return map.get(userName);
//    }
}