package com.brijframework.authorization.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.authorization.account.entities.EOUserAccount;

@Repository
@Transactional
public interface UserAccountRepository  extends CustomRepository<EOUserAccount, Long>{

	
	@Query(nativeQuery = true,  value="select * from USER_ACCOUNT UA where UA.USERNAME = :username OR UA.ACCOUNT_MOBILE = :username OR UA.ACCOUNT_EMAIL = :username and UA.RECORD_STATUS in (:statusList)")
	Optional<EOUserAccount> findByUsername(@Param("username")String username, @Param("statusList")List<String> statusList);

	@Query(nativeQuery = true,  value="select * from USER_ACCOUNT UA where UA.OWNER_ID = :ownerId")
	List<EOUserAccount> findAllByOwnerId(Long ownerId);
	
	@Query(nativeQuery = true,  value="select * from USER_ACCOUNT UA INNER JOIN USER_ROLE UR ON UR.ID=UA.ROLE_ID  where UA.OWNER_ID = :ownerId AND UR.ROLE_TYPE = :roleType and UA.RECORD_STATUS in (:statusList)")
	List<EOUserAccount> findAllByOwnerIdAndRoleType(Long ownerId, String roleType ,@Param("statusList") List<String> statusIds);

	Long countByUserRoleRoleNameInAndRecordStateIn(List<String> roleNames ,List<String> statusIds);

}
