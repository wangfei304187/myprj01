package com.my.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.my.app.pojo.Permission;
import com.my.app.pojo.Role;
import com.my.app.pojo.UserAccount;

@Service
public class UserAccountDao {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void printAll() {
		String sql = "select * from UserAccount";
//		String sql = "SELECT u.userName, u.password, u.isDeleted, u.isLocked, r.roleName, r.description AS roleDescription, p.permissionName, p.description AS permissionDescription "
//				+ "FROM useraccount u, useraccount_role ur, role_permission rp, role r, permission p "
//				+ "WHERE u.userName=ur.userName AND ur.roleName=r.roleName AND r.roleName=rp.roleName  AND rp.permissionName=p.permissionName";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> map : list) {
			Set<Entry<String, Object>> entries = map.entrySet();
			if (entries != null) {
				Iterator<Entry<String, Object>> iterator = entries.iterator();
				while (iterator.hasNext()) {
					Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
					Object key = entry.getKey();
					Object value = entry.getValue();
					System.out.println(key + ":" + value);
				}
			}
		}
	}
	
	public void printAllUserAccount()
	{
		String sql = "SELECT u.userName, u.password, u.isDeleted, u.isLocked, r.roleName, r.description AS roleDescription, p.permissionName, p.description AS permissionDescription "
				+ "FROM useraccount u, useraccount_role ur, role_permission rp, role r, permission p "
				+ "WHERE u.userName=ur.userName AND ur.roleName=r.roleName AND r.roleName=rp.roleName  AND rp.permissionName=p.permissionName";
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int colNum = rs.getMetaData().getColumnCount();
			int rowNum = 1;
			while(rs.next())
			{
				for(int i=1; i<=colNum; i++)
				{
					System.out.println(rs.getObject(i));
				}
				System.out.println("------------------------rowNum=" + rowNum++);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public UserAccount findUserAccountByName(String name)
	{
		String sql = "select * from UserAccount where userName=?";
		Object[] params = new Object[] {name};
		UserAccount userAccount = jdbcTemplate.queryForObject(sql, params, new RowMapper<UserAccount>() {

			@Override
			public UserAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserAccount obj = new UserAccount();
				obj.setUserName(rs.getString("userName"));
				obj.setPassword(rs.getString("password"));
				obj.setDeleted(rs.getBoolean("isDeleted"));
				obj.setLocked(rs.getBoolean("isLocked"));
				return obj;
			}
			
		});
		
		return userAccount;
	}
	
	public Role findRoleByName(String name)
	{
		String sql = "select * from Role where roleName=?";
		Object[] params = new Object[] {name};
		Role role = jdbcTemplate.queryForObject(sql, params, new RowMapper<Role>() {

			@Override
			public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
				Role obj = new Role();
				obj.setRoleName(rs.getString("roleName"));
				obj.setDescription(rs.getString("description"));
				return obj;
			}
			
		});
		
		return role;
	}
	
	public Permission findPermissionByName(String name)
	{
		String sql = "select * from Permission where permissionName=?";
		Object[] params = new Object[] {name};
		Permission p = jdbcTemplate.queryForObject(sql, params, new RowMapper<Permission>() {

			@Override
			public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
				Permission obj = new Permission();
				obj.setPermissionName(rs.getString("roleName"));
				obj.setDescription(rs.getString("description"));
				return obj;
			}
			
		});
		
		return p;
	}
	
	public List<UserAccount> findAllUserAccount()
	{
		String sql = "SELECT u.userName, u.password, u.isDeleted, u.isLocked, r.roleName, r.description AS roleDescription, p.permissionName, p.description AS permissionDescription "
				+ "FROM useraccount u, useraccount_role ur, role_permission rp, role r, permission p "
				+ "WHERE u.userName=ur.userName AND ur.roleName=r.roleName AND r.roleName=rp.roleName  AND rp.permissionName=p.permissionName";
		
		List<UserAccount> uList = new ArrayList<>();
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> map : list) {
			
			UserAccount u = new UserAccount();
			u.setUserName((String)map.get("userName"));
			u.setPassword((String)map.get("password"));
			u.setDeleted((Boolean)map.get("isDeleted"));
			u.setLocked((Boolean)map.get("isLocked"));
			
			if (!uList.contains(u))
			{
				uList.add(u);
			}
			else
			{
				int idx = uList.indexOf(u);
				u = uList.get(idx);
			}
			
			Role r = new Role();
			r.setRoleName((String)map.get("roleName"));
			r.setDescription((String)map.get("roleDescription"));
			
			if (!u.getRoles().contains(r))
			{
				u.getRoles().add(r);
			}
			else
			{
				r = u.getRole(r);
			}
			
			Permission p = new Permission();
			p.setPermissionName((String)map.get("permissionName"));
			p.setDescription((String)map.get("permissionDescription"));
			
			if (!r.getPermissions().contains(p))
			{
				r.getPermissions().add(p);
			}
			else
			{
				// ignore
			}
		}
		
		// -->print
//		for (UserAccount u: uList) {
//			System.out.println(u);
//		}
		//<--
		
		return uList;
	}
	
	public UserAccount findUserAccountCascadeByName(String userName)
	{
		List<UserAccount> list = findAllUserAccount();
		for (UserAccount userAccount : list) {
			if (userAccount.getUserName().equals(userName))
			{
				System.out.println(userAccount);
				
				return userAccount;
			}
		}
		
		return null;
	}
}
