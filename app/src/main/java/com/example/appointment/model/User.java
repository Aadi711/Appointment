package com.example.appointment.model;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("code")
	private int code;

	@SerializedName("a_code")
	private String aCode;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("f_code")
	private String fCode;
	@SerializedName("image")
	private String image;

	@SerializedName("phone")
	private String phoneNumber;

	@SerializedName("password")
	private String password;

	@SerializedName("user_type")
	private String userType;

	@SerializedName("nationality")
	private String nationality;

	@SerializedName("g_token")
	private String gToken;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;
	@SerializedName("salon_id")
	private int salonId;
	@SerializedName("shop_id")
	private Long shopId;
	@SerializedName("style_id")
	private Long styleId;
	@SerializedName("shop_registered")
	private int shopRegistered;

	@SerializedName("fb_token")
	private String fbToken;

	@SerializedName("user_token")
	private String userToken;
	@SerializedName("token")
	private String token;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private int status;

	@SerializedName("user_profile")
	private Profile userprofile;


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getaCode() {
		return aCode;
	}

	public void setaCode(String aCode) {
		this.aCode = aCode;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getfCode() {
		return fCode;
	}

	public void setfCode(String fCode) {
		this.fCode = fCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getgToken() {
		return gToken;
	}

	public void setgToken(String gToken) {
		this.gToken = gToken;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSalonId() {
		return salonId;
	}

	public void setSalonId(int salonId) {
		this.salonId = salonId;
	}

	public String getFbToken() {
		return fbToken;
	}

	public void setFbToken(String fbToken) {
		this.fbToken = fbToken;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getStyleId() {
		return styleId;
	}

	public void setStyleId(Long styleId) {
		this.styleId = styleId;
	}

	public int getShopRegistered() {
		return shopRegistered;
	}

	public void setShopRegistered(int shopRegistered) {
		this.shopRegistered = shopRegistered;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Profile getUserprofile() {
		return userprofile;
	}

	public void setUserprofile(Profile userprofile) {
		this.userprofile = userprofile;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}