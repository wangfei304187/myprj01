package com.my.app.service;

import com.my.app.pojo.UserAccount;

public interface LoginService {

	public UserAccount getUserByName(String name);
}
