/**
 * 
 */
package com.brijframework.rest.crud.controller;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.brijframework.authorization.beans.Response;
import com.brijframework.rest.crud.service.CrudService;

/**
 *  @author ram kishor
 */
public abstract class CrudController<DT, EN, ID> {
	
	public abstract CrudService<DT, EN, ID> getService();

	@PostMapping
	public Response addr(@RequestBody DT dto){
		Response response=new Response();
		try {
			response.setData(getService().add(dto));
			response.setSuccess("0");
			response.setMessage("Successfully procceed");
			return response;
		}catch (Exception e) {
			response.setSuccess("0");
			response.setMessage(e.getMessage());
			return response;
		}
		
	}
	
	@PutMapping
	public Response update(@RequestBody DT dto){
		Response response=new Response();
		try {
			response.setData(getService().update(dto));
			response.setSuccess("0");
			response.setMessage("Successfully procceed");
			return response;
		}catch (Exception e) {
			response.setSuccess("0");
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@DeleteMapping("/{id}")
	public Response delete(@PathVariable ID id){
		Response response=new Response();
		try {
			response.setData(getService().delete(id));
			response.setSuccess("0");
			response.setMessage("Successfully procceed");
			return response;
		}catch (Exception e) {
			response.setSuccess("0");
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GetMapping("/{id}")
	public Response find(@PathVariable ID id){
		Response response=new Response();
		try {
			response.setData(getService().findById(id));
			response.setSuccess("0");
			response.setMessage("Successfully procceed");
			return response;
		}catch (Exception e) {
			response.setSuccess("0");
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GetMapping
	public Response findAll(){
		Response response=new Response();
		try {
			response.setData(getService().findAll());
			response.setSuccess("0");
			response.setMessage("Successfully procceed");
			return response;
		}catch (Exception e) {
			response.setSuccess("0");
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GetMapping("/page/data/{pageNumber}/count/{count}")
	public Response fetchPageObject(@PathVariable int pageNumber,@PathVariable int count){
		Response response=new Response();
		try {
			response.setData(getService().fetchPageObject(pageNumber, count));
			response.setSuccess("0");
			response.setMessage("Successfully procceed");
			return response;
		}catch (Exception e) {
			response.setSuccess("0");
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GetMapping("/page/list/{pageNumber}/count/{count}")
	public Response fetchPageList(@PathVariable int pageNumber,@PathVariable int count){
		Response response=new Response();
		try {
			response.setData(getService().fetchPageList(pageNumber, count));
			response.setSuccess("0");
			response.setMessage("Successfully procceed");
			return response;
		}catch (Exception e) {
			response.setSuccess("0");
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GetMapping("/findAll/page/data/{pageNumber}/count/{count}/sort/{sort}")
	public Response fetchPageObject(@PathVariable int pageNumber,@PathVariable int count, @PathVariable String sort){
		Response response=new Response();
		try {
			response.setData(getService().fetchPageObject(pageNumber, count, Sort.by(sort)));
			response.setSuccess("0");
			response.setMessage("Successfully procceed");
			return response;
		}catch (Exception e) {
			response.setSuccess("0");
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GetMapping("/findAll/page/list/{pageNumber}/count/{count}/sort/{sort}")
	public Response fetchPageList(@PathVariable int pageNumber,@PathVariable int count, @PathVariable String sort){
		Response response=new Response();
		try {
			response.setData(getService().fetchPageList(pageNumber, count, Sort.by(sort)));
			response.setSuccess("0");
			response.setMessage("Successfully procceed");
			return response;
		}catch (Exception e) {
			response.setSuccess("0");
			response.setMessage(e.getMessage());
			return response;
		}
	}
}
