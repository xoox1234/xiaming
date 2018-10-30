package com.smartvillage.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Users")
@Data
public class Users {
	@Id
    @Column(name = "id")
    private long id;
	
	@Column(name = "mobile")
    private String mobile;
	
	//昵称
    @Column(name = "nick_name")
    private String nickname;
    
    //登录类型
    @Column(name = "login_token")
    private String loginType;
    private String token;
    private Date expire;
    private String createdby;
    private Date createat;
    private String updatedby;
    private Date updateat;
}
