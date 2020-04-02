package com.example.dto;

public class MemberVO {

	private String id;
	private String pw;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private String mbrid ;
	private String mbrpw ;
	private String mbrEmail ;
	private String mbrName ;
	private String mbrMobile ;
	private String regDate ;
	
	private String cpPw ;
	private String naverId;
	private String googleId;
	

	public String getNaverId() {
		return naverId;
	}

	public void setNaverId(String naverId) {
		this.naverId = naverId;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public String getCpPw() {
		return cpPw;
	}

	public void setCpPw(String cpPw) {
		this.cpPw = cpPw;
	}

	public String getMbrid() {
		return mbrid;
	}

	public void setMbrid(String mbrid) {
		this.mbrid = mbrid;
	}

	public String getMbrpw() {
		return mbrpw;
	}

	public void setMbrpw(String mbrpw) {
		this.mbrpw = mbrpw;
	}

	public String getMbrEmail() {
		return mbrEmail;
	}

	public void setMbrEmail(String mbrEmail) {
		this.mbrEmail = mbrEmail;
	}

	public String getMbrName() {
		return mbrName;
	}

	public void setMbrName(String mbrName) {
		this.mbrName = mbrName;
	}

	public String getMbrMobile() {
		return mbrMobile;
	}

	public void setMbrMobile(String mbrMobile) {
		this.mbrMobile = mbrMobile;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "MemberVO [mbrid=" + mbrid + ", mbrpw=" + mbrpw + ", mbrEmail=" + mbrEmail + ", mbrName=" + mbrName
				+ ", mbrMobile=" + mbrMobile + ", regDate=" + regDate + ", cpPw=" + cpPw + "]";
	}

}
