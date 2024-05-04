package com.brijframework.authorization.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.model.headers.EOHeaderItem;

@Repository
@Transactional
public interface HeaderItemRepository  extends JpaRepository<EOHeaderItem, Long>{

	@Query(nativeQuery = true,  value="select * from HEADER_ITEM HI where HI.TITLE = :title")
	Optional<EOHeaderItem> findByTitle(@Param("title")String title);
	
	@Query(nativeQuery = true,  value="select * from HEADER_ITEM HI where HI.URL = :url")
	Optional<EOHeaderItem> findByUrl(@Param("url")String url);

	@Query(nativeQuery = true,  value="select * from HEADER_ITEM HI where HI.TYPE = :type")
	List<EOHeaderItem> findAllByType(@Param("type")String type);

	@Query(nativeQuery = true,  value="select * from HEADER_ITEM HI where HI.URL in (:urls)")
	List<EOHeaderItem> findByUrls(@Param("urls")List<String> urls);

}
