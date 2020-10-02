package com.my.app.pojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Role {

    private String roleName;
    private String description;

    private Set<Permission> permissions = new HashSet<>();
    
    public Role() {
    	
    }
    
	public Role(String roleName, String description) {
		this.roleName = roleName;
		this.description = description;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}
	
	public Permission getPermission(Permission r)
	{
		Iterator<Permission> iter = permissions.iterator();
		while(iter.hasNext())
		{
			Permission p = iter.next();
			if (p.equals(r))
			{
				return p;
			}
		}
		
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
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
		Role other = (Role) obj;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(100);
		sb.append("Role [roleName=" + roleName + ", description=" + description + "]");
		for (Permission permission : permissions) {
			sb.append("\n\t\t").append(permission);
		}
		return sb.toString();
	}
	
}