/**
 * 
 */
package com.brijframework.rest.crud.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.brijframework.authorization.mapper.GenericMapper;
import com.brijframework.rest.crud.beans.PageDetail;

/**
 *  @author ram kishor
 */
public interface CrudService<DT, EN, ID> {
	/**
	 * 
	 * @return
	 */
	JpaRepository<EN, ID> getRepository();
	/***
	 * 
	 * @return
	 */
	GenericMapper<EN, DT>  getMapper();
	/***
	 * 
	 * @param data
	 * @return
	 */
	DT add(DT data);
	/***
	 * 
	 * @param data
	 * @return
	 */
	DT update(DT data);
	/***
	 * 
	 * @param uuid
	 * @return
	 */
	Boolean delete(ID uuid);
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	DT findById(ID uuid);
	/**
	 * 
	 * @return
	 */
	List<DT> findAll();
	
	/**
	 * 
	 * @return
	 */
	List<DT> findAll(Sort sort);
	
	/**
	 * @param pageNumber
	 * @param count
	 * @return
	 */
	PageDetail fetchPageObject(int pageNumber, int count);

	/**
	 * @param pageNumber
	 * @param count
	 * @return
	 */
	List<DT> fetchPageList(int pageNumber, int count);

	/**
	 * @param pageNumber
	 * @param count
	 * @param sort
	 * @return
	 */
	PageDetail fetchPageObject(int pageNumber, int count, Sort sort);

	/**
	 * @param pageNumber
	 * @param count
	 * @param sort
	 * @return
	 */
	List<DT> fetchPageList(int pageNumber, int count, Sort sort);

}
