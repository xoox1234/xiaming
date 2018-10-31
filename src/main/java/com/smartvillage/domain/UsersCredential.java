package com.smartvillage.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Users_Credential")
@Data
@SequenceGenerator(name = "ucredential_sqn", sequenceName = "Users_cer_sqn", allocationSize = 1)
public class UsersCredential {
	@Id
    @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ucredential_sqn")
    private long id;
	
	//外键关联userAuths的ID
	@Column(name = "user_id")
    private long userId;
	//用户密码
	@Column(name = "pass_word")
	private String password;
	//id+pwd加盐
	@Column(name = "salt")
	private String salt;
	
	/**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
        return this.userId+this.salt;
    }
}
