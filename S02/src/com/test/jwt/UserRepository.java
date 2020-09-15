package com.test.jwt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UserRepository {

    public static int ERR_NUM_OK = 0;
    public static int ERR_NUM_PWD_ERR = 1;
    public static int ERR_NUM_SERVER_ERR = 2;

    private static Map<String, User> userRepo = new HashMap<>();

    static {
        User u1 = new User();
        u1.setUserId("1");
        u1.setUserName("User1");

        User u2 = new User();
        u2.setUserId("2");
        u2.setUserName("User2");

        userRepo.put(u1.getUserId(), u1);
        userRepo.put(u2.getUserId(), u2);
    }

    public User save(User u) // insertOrUpdate
    {
        userRepo.put(u.getUserId(), u);
        return u;
    }

    public User findByUserName(String userName)
    {
        Set<Map.Entry<String, User>> set = userRepo.entrySet();
        Iterator<Map.Entry<String, User>> iter = set.iterator();
        while(iter.hasNext())
        {
            Map.Entry<String, User> entry = iter.next();
            User u = entry.getValue();
            if (u.getUserName().equals(userName))
            {
                return u;
            }
        }
        return null;
    }

    public void updateLastLoginTime(User u)
    {
        update(u);
    }

    public void update(User u)
    {
        User user = findByUserName(u.getUserName());
        user.setLastLoginTime(u.getLastLoginTime());
        save(user);
    }
}
