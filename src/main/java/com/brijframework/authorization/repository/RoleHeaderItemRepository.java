package com.brijframework.authorization.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.model.headers.EORoleHeaderItem;

@Repository
@Transactional
public interface RoleHeaderItemRepository  extends JpaRepository<EORoleHeaderItem, Long>{

	@Query(nativeQuery = true,  value="select * from ROLE_HEADER_ITEM URE where URE.ROLE_ID = :roleId and URE.HEADER_ITEM_ID=:headerId ")
	Optional<EORoleHeaderItem> findByRoleIdAndEndpointId(@Param("roleId")Long roleId, @Param("headerId") Long headerId);

	@Query(nativeQuery = true,  value="select * from ROLE_HEADER_ITEM URE where URE.ROLE_ID+'_'+URE.HEADER_ITEM_ID in (:roleheaders) ")
	List<EORoleHeaderItem> findByRoleEndpoints(@Param("roleheaders") List<String> roleheaders);

	@Query(nativeQuery = true,  value="select * from ROLE_HEADER_ITEM URE where URE.ROLE_ID =?1")
	List<EORoleHeaderItem>  findAllByRoleId(Long roleId);

	@Query(nativeQuery = true,  value="select * from ROLE_HEADER_ITEM URE where URE.TYPE_ID =?1")
	List<EORoleHeaderItem>  findAllByType(String roleId);
}
