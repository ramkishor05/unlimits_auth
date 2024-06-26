package com.brijframework.authorization.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.account.entities.EOUserAccount;

@Repository
@Transactional
public interface UserAccountRepository  extends JpaRepository<EOUserAccount, Long>{

	@Query(nativeQuery = true,  value="select * from USER_ACCOUNT UA where UA.USERNAME = :username")
	Optional<EOUserAccount> findByUsername(@Param("username")String username);

	@Query(nativeQuery = true,  value="select * from USER_ACCOUNT UA where UA.OWNER_ID = :ownerId")
	List<EOUserAccount> findAllByOwnerId(Long ownerId);
	
	@Query(nativeQuery = true,  value="select * from USER_ACCOUNT UA INNER JOIN USER_ROLE UR ON UR.ID=UA.ROLE_ID  where UA.OWNER_ID = :ownerId AND UR.ROLE_TYPE = :roleType ")
	List<EOUserAccount> findAllByOwnerIdAndRoleType(Long ownerId, String roleType);

}
