package com.smartvillage.repository;

import org.springframework.data.repository.CrudRepository;
import com.smartvillage.domain.Users;

public interface UsersRepository extends CrudRepository<Users, Long>{
	public Users findByMobile(String mobile);
}
