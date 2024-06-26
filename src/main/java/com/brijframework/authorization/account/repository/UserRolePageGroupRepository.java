package com.brijframework.authorization.account.repository;

import static com.brijframework.authorization.contants.TableConstants.USER_ROLE_PAGE_GROUP;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.account.entities.pages.EOUserRolePageGroup;

@Repository
@Transactional
public interface UserRolePageGroupRepository  extends JpaRepository<EOUserRolePageGroup, Long>{

	@Query(nativeQuery = true,  value="select * from "+USER_ROLE_PAGE_GROUP+" URE where URE.USER_ROLE_ID =?1")
	List<EOUserRolePageGroup>  findAllByRoleId(Long roleId);

	@Query(nativeQuery = true,  value="select * from "+USER_ROLE_PAGE_GROUP+" URE where URE.USER_ROLE_ID = :roleId and URE.PAGE_GROUP_ID=:userEndpointId ")
	Optional<EOUserRolePageGroup> findByRoleIdAndGroupId(@Param("roleId")Long roleId, @Param("userEndpointId") Long userEndpointId);

}
