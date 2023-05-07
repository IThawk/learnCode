package org.springframework.demo.model;


import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = -1840831686851699943L;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public User() {
	}

	public User(Long id, String name, String password, String salt, String email, String phoneNumber, Integer status, Date createTime, Date lastLoginTime, Date lastUpdateTime) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.salt = salt;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.createTime = createTime;
		this.lastLoginTime = lastLoginTime;
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 用户名
	 */
	private String name;

	/**
	 * 加密后的密码
	 */
	private String password;

	/**
	 * 加密使用的盐
	 */
	private String salt;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号码
	 */
	private String phoneNumber;

	/**
	 * 状态，-1：逻辑删除，0：禁用，1：启用
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 上次登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 上次更新时间
	 */
	private Date lastUpdateTime;
}
