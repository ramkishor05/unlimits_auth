package com.brijframework.authorization.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.model.menus.EORoleMenuGroup;

@Repository
@Transactional
public interface RoleMenuGroupRepository  extends JpaRepository<EORoleMenuGroup, Long>{

	@Query(nativeQuery = true,  value="select * from ROLE_MENU_GROUP URE where URE.USER_ROLE_ID =?1")
	List<EORoleMenuGroup>  findAllByRoleId(Long roleId);

	@Query(nativeQuery = true,  value="select * from ROLE_MENU_GROUP URE where URE.USER_ROLE_ID = :roleId and URE.MENU_GROUP_ID=:userEndpointId ")
	Optional<EORoleMenuGroup> findByRoleIdAndGroupId(@Param("roleId")Long roleId, @Param("userEndpointId") Long userEndpointId);

}
