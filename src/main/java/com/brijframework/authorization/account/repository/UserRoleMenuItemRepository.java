package com.brijframework.authorization.account.repository;
import static com.brijframework.authorization.contants.TableConstants.USER_ROLE_MENU_ITEM;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuItem;

@Repository
@Transactional
public interface UserRoleMenuItemRepository  extends CustomRepository<EOUserRoleMenuItem, Long>{

	@Query(nativeQuery = true,  value="select * from "+USER_ROLE_MENU_ITEM+" where URE.ROLE_ID = :roleId and URE.MENU_ITEM_ID=:userEndpointId ")
	Optional<EOUserRoleMenuItem> findByRoleIdAndEndpointId(@Param("roleId")Long roleId, @Param("userEndpointId") Long userEndpointId);

	@Query(nativeQuery = true,  value="select * from "+USER_ROLE_MENU_ITEM+" URE where URE.ROLE_ID+'_'+URE.MENU_ITEM_ID in (:roleEndpoints) ")
	List<EOUserRoleMenuItem> findByRoleEndpoints(@Param("roleEndpoints") List<String> roleEndpoints);

	@Query(nativeQuery = true,  value="select * from "+USER_ROLE_MENU_ITEM+" URE where URE.ROLE_ID =?1")
	List<EOUserRoleMenuItem>  findAllByRoleId(Long roleId);

	@Query(nativeQuery = true,  value="select * from "+USER_ROLE_MENU_ITEM+" URE where URE.TYPE_ID =?1")
	List<EOUserRoleMenuItem>  findAllByType(String roleId);
}
