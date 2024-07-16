package com.brijframework.authorization.view.repository;
import static com.brijframework.authorization.view.constants.ViewTableConstants.VIEW_HEADER_ITEM;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.authorization.view.entities.headers.EOViewHeaderItem;

@Repository
@Transactional
public interface ViewHeaderItemRepository  extends CustomRepository<EOViewHeaderItem, Long>{

	
	@Query(nativeQuery = true,  value="select * from "+VIEW_HEADER_ITEM+" HI where HI.TITLE = :title")
	Optional<EOViewHeaderItem> findByTitle(@Param("title")String title);
	
	@Query(nativeQuery = true,  value="select * from "+VIEW_HEADER_ITEM+" HI where HI.URL = :url")
	Optional<EOViewHeaderItem> findByUrl(@Param("url")String url);

	@Query(nativeQuery = true,  value="select * from "+VIEW_HEADER_ITEM+" HI where HI.TYPE = :type")
	List<EOViewHeaderItem> findAllByType(@Param("type")String type);

	@Query(nativeQuery = true,  value="select * from "+VIEW_HEADER_ITEM+" HI where HI.URL in (:urls)")
	List<EOViewHeaderItem> findByUrls(@Param("urls")List<String> urls);

}
