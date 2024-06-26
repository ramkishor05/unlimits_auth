package com.brijframework.authorization.view.repository;
import static com.brijframework.authorization.view.constants.ViewTableConstants.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.view.entities.menus.EOViewMenuItem;

@Repository
@Transactional
public interface ViewMenuItemRepository  extends JpaRepository<EOViewMenuItem, Long>{

	@Query(nativeQuery = true,  value="select * from "+VIEW_MENU_ITEM+" UE where UE.TITLE = :title")
	Optional<EOViewMenuItem> findByTitle(@Param("title")String title);
	
	@Query(nativeQuery = true,  value="select * from "+VIEW_MENU_ITEM+" UE where UE.URL = :url")
	Optional<EOViewMenuItem> findByUrl(@Param("url")String url);

	@Query(nativeQuery = true,  value="select * from "+VIEW_MENU_ITEM+" UE where UE.TYPE = :type")
	List<EOViewMenuItem> findAllByType(@Param("type")String type);

	@Query(nativeQuery = true,  value="select * from "+VIEW_MENU_ITEM+" UE where UE.URL in (:urls)")
	List<EOViewMenuItem> findByUrls(@Param("urls")List<String> urls);


}
