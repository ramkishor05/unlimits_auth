package com.brijframework.authorization.view.repository;
import static com.brijframework.authorization.view.constants.ViewTableConstants.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.view.entities.menus.EOViewMenuGroup;

@Repository
@Transactional
public interface ViewMenuGroupRepository  extends JpaRepository<EOViewMenuGroup, Long>{


	@Query(nativeQuery = true,  value="select * from "+VIEW_MENU_GROUP+" UE where UE.TITLE = :title")
	Optional<EOViewMenuGroup> findByTitle(@Param("title")String title);
	
	@Query(nativeQuery = true,  value="select * from "+VIEW_MENU_GROUP+" UE where UE.URL = :url")
	Optional<EOViewMenuGroup> findByUrl(@Param("url")String url);

	@Query(nativeQuery = true,  value="select * from "+VIEW_MENU_GROUP+" UE where UE.TYPE = :type")
	List<EOViewMenuGroup> findAllByType(@Param("type")String type);

	@Query(nativeQuery = true,  value="select * from "+VIEW_MENU_GROUP+" UE where UE.URL in (:urls)")
	List<EOViewMenuGroup> findByUrls(@Param("urls")List<String> urls);


}
