package com.tiantian.pojo.utils;

import java.io.Serializable;

public class FriendSearchResultBean implements Serializable{
	private  Integer id;
	 public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private String username;
	 private String phone;
	 private String email;
	 private String profilePhoto;
	public String getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
