package com.my.app.pojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UserAccount {

    private String userName;
    private String password;
    private boolean isDeleted;
    private boolean isLocked;

    private Set<Role> roles = new HashSet<Role>();
    
    public UserAccount() {
    	
    }
    
	public UserAccount(String userName, String password, boolean isDeleted, boolean isLocked) {
		this.userName = userName;
		this.password = password;
		this.isDeleted = isDeleted;
		this.isLocked = isLocked;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Set<Role> getRoles() {
		return roles;
	}
	
	public Role getRole(Role r)
	{
		Iterator<Role> iter = roles.iterator();
		while(iter.hasNext())
		{
			Role role = iter.next();
			if (role.equals(r))
			{
				return role;
			}
		}
		
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(100);
		sb.append("UserAccount [userName=" + userName + ", password=" + password + ", isDeleted=" + isDeleted
		        + ", isLocked=" + isLocked + "]");
		for (Role role : roles) {
			sb.append("\n\t").append(role);
		}
		return sb.toString();
	}

}