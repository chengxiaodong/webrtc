package com.tiantian.pojo;

import java.io.Serializable;

public class TbSysuser implements Serializable{
    @Override
	public String toString() {
		return "TbSysuser [uid=" + uid + ", username=" + username + ", password=" + password + ", level=" + level
				+ ", getUid()=" + getUid() + ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword()
				+ ", getLevel()=" + getLevel() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer uid;

    private String username;

    private String password;

    private Integer level;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}