package com.brijframework.authorization.account.repository;

import static com.brijframework.authorization.contants.TableConstants.USER_ROLE_MENU_GROUP;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuGroup;

@Repository
@Transactional
public interface UserRoleMenuGroupRepository  extends JpaRepository<EOUserRoleMenuGroup, Long>{

	@Query(nativeQuery = true,  value="select * from "+USER_ROLE_MENU_GROUP+" URE where URE.USER_ROLE_ID =?1")
	List<EOUserRoleMenuGroup>  findAllByRoleId(Long roleId);

	@Query(nativeQuery = true,  value="select * from "+USER_ROLE_MENU_GROUP+" URE where URE.USER_ROLE_ID = :roleId and URE.MENU_GROUP_ID=:userEndpointId ")
	Optional<EOUserRoleMenuGroup> findByRoleIdAndGroupId(@Param("roleId")Long roleId, @Param("userEndpointId") Long userEndpointId);

}
