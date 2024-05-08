/**
 * 
 */
package com.brijframework.rest.crud.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.brijframework.rest.crud.beans.PageDetail;
import com.brijframework.rest.crud.service.CrudService;

/**
 *  @author ram kishor
 */
public abstract class CrudController<DT, EN, ID> {
	
	public abstract CrudService<DT, EN, ID> getService();

	@PostMapping
	public DT addr(@RequestBody DT dto){
		return getService().add(dto);
	}
	
	@PutMapping
	public DT update(@RequestBody DT dto){
		return getService().update(dto);
	}
	
	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable ID id){
		return getService().delete(id);
	}
	
	@GetMapping("/{id}")
	public DT find(@PathVariable ID id){
		return getService().findById(id);
	}
	
	@GetMapping
	public List<DT> findAll(){
		return getService().findAll();
	}
	
	@GetMapping("/page/data/{pageNumber}/count/{count}")
	public PageDetail fetchPageObject(@PathVariable int pageNumber,@PathVariable int count){
		return getService().fetchPageObject(pageNumber, count);
	}
	
	@GetMapping("/page/list/{pageNumber}/count/{count}")
	public List<DT> fetchPageList(@PathVariable int pageNumber,@PathVariable int count){
		return getService().fetchPageList(pageNumber, count);
	}
	
	@GetMapping("/findAll/page/data/{pageNumber}/count/{count}/sort/{sort}")
	public PageDetail fetchPageObject(@PathVariable int pageNumber,@PathVariable int count, @PathVariable String sort){
		return getService().fetchPageObject(pageNumber, count, Sort.by(sort));
	}
	
	@GetMapping("/findAll/page/list/{pageNumber}/count/{count}/sort/{sort}")
	public List<DT> fetchPageList(@PathVariable int pageNumber,@PathVariable int count, @PathVariable String sort){
		return getService().fetchPageList(pageNumber, count, Sort.by(sort));
	}
}
