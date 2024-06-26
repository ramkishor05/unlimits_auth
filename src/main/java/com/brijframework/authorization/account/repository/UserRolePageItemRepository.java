package com.brijframework.authorization.account.repository;
import static com.brijframework.authorization.contants.TableConstants.USER_ROLE_PAGE_ITEM;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.account.entities.pages.EOUserRolePageItem;

@Repository
@Transactional
public interface UserRolePageItemRepository  extends JpaRepository<EOUserRolePageItem, Long>{

	@Query(nativeQuery = true,  value="select * from "+USER_ROLE_PAGE_ITEM+" where URE.ROLE_ID = :roleId and URE.PAGE_ITEM_ID=:userEndpointId ")
	Optional<EOUserRolePageItem> findByRoleIdAndEndpointId(@Param("roleId")Long roleId, @Param("userEndpointId") Long userEndpointId);

	@Query(nativeQuery = true,  value="select * from "+USER_ROLE_PAGE_ITEM+" URE where URE.ROLE_ID+'_'+URE.PAGE_ITEM_ID in (:roleEndpoints) ")
	List<EOUserRolePageItem> findByRoleEndpoints(@Param("roleEndpoints") List<String> roleEndpoints);

	@Query(nativeQuery = true,  value="select * from "+USER_ROLE_PAGE_ITEM+" URE where URE.ROLE_ID =?1")
	List<EOUserRolePageItem>  findAllByRoleId(Long roleId);

	@Query(nativeQuery = true,  value="select * from "+USER_ROLE_PAGE_ITEM+" URE where URE.TYPE_ID =?1")
	List<EOUserRolePageItem>  findAllByType(String roleId);
}
