package com.brijframework.authorization.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.model.menus.EORoleMenuItem;

@Repository
@Transactional
public interface RoleMenuItemRepository  extends JpaRepository<EORoleMenuItem, Long>{

	@Query(nativeQuery = true,  value="select * from ROLE_MENU_ITEM URE where URE.ROLE_ID = :roleId and URE.MENU_ITEM_ID=:userEndpointId ")
	Optional<EORoleMenuItem> findByRoleIdAndEndpointId(@Param("roleId")Long roleId, @Param("userEndpointId") Long userEndpointId);

	@Query(nativeQuery = true,  value="select * from ROLE_MENU_ITEM URE where URE.ROLE_ID+'_'+URE.MENU_ITEM_ID in (:roleEndpoints) ")
	List<EORoleMenuItem> findByRoleEndpoints(@Param("roleEndpoints") List<String> roleEndpoints);

	@Query(nativeQuery = true,  value="select * from ROLE_MENU_ITEM URE where URE.ROLE_ID =?1")
	List<EORoleMenuItem>  findAllByRoleId(Long roleId);

	@Query(nativeQuery = true,  value="select * from ROLE_MENU_ITEM URE where URE.TYPE_ID =?1")
	List<EORoleMenuItem>  findAllByType(String roleId);
}
