package com.tiantian.portal.pojo;

import org.springframework.web.multipart.MultipartFile;

public class RegisterBeanVo {
	private String name;
	private String password1 ;
	private String password2 ;
	private String phone ;
	private String email;
	private String verifyCode;
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public MultipartFile getHeadIcon() {
		return headIcon;
	}
	public void setHeadIcon(MultipartFile headIcon) {
		this.headIcon = headIcon;
	}
	//头像
	private MultipartFile headIcon;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword1() {
		return password1;
	}
	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	public String getPassword2() {
		return password2;
	}
	@Override
	public String toString() {
		return "RegisterBeanVo [name=" + name + ", password1=" + password1 + ", password2=" + password2 + ", phone="
				+ phone + ", email=" + email + ", headIcon=" + headIcon + "]";
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
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
