package com.smartvillage.repository;

import org.springframework.data.repository.CrudRepository;

import com.smartvillage.domain.UsersCredential;

/**
 * User类的CRUD操作
 * @see com.UserAuths.domain.User
 * 
 */
public interface UsersCredentialRepository extends CrudRepository<UsersCredential, Long> {

}
