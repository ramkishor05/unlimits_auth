package com.brijframework.authorization.view.repository;
import static com.brijframework.authorization.view.constants.ViewTableConstants.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.view.entities.pages.EOViewPageItem;

@Repository
@Transactional
public interface ViewPageItemRepository  extends JpaRepository<EOViewPageItem, Long>{

	@Query(nativeQuery = true,  value="select * from "+VIEW_PAGE_ITEM+" UE where UE.TITLE = :title")
	Optional<EOViewPageItem> findByTitle(@Param("title")String title);
	
	@Query(nativeQuery = true,  value="select * from "+VIEW_PAGE_ITEM+" UE where UE.URL = :url")
	Optional<EOViewPageItem> findByUrl(@Param("url")String url);

	@Query(nativeQuery = true,  value="select * from "+VIEW_PAGE_ITEM+" UE where UE.TYPE = :type")
	List<EOViewPageItem> findAllByType(@Param("type")String type);

	@Query(nativeQuery = true,  value="select * from "+VIEW_PAGE_ITEM+" UE where UE.URL in (:urls)")
	List<EOViewPageItem> findByUrls(@Param("urls")List<String> urls);

}
