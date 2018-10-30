package com.smartvillage.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Users_Wechat")
@Data
public class UsersWechat {
	@Id
    @Column(name = "id")
	private long id;
	
	@Column(name = "user_id")
	private long userId;
	//wx用户唯一标示
	@Column(name ="open_id")
	private String openid;
	
	@Column(name ="union_id")
	private String unionid;
}
