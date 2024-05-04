package com.brijframework.authorization;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.brijframework.json.schema.factories.JsonSchemaDataFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.brijframework.authorization.constant.Authority;
import com.brijframework.authorization.model.EOUserAccount;
import com.brijframework.authorization.model.EOUserProfile;
import com.brijframework.authorization.model.EOUserRole;
import com.brijframework.authorization.model.headers.EOHeaderItem;
import com.brijframework.authorization.model.headers.EORoleHeaderItem;
import com.brijframework.authorization.model.menus.EOMenuGroup;
import com.brijframework.authorization.model.menus.EOMenuItem;
import com.brijframework.authorization.model.menus.EORoleMenuGroup;
import com.brijframework.authorization.model.menus.EORoleMenuItem;
import com.brijframework.authorization.repository.HeaderItemRepository;
import com.brijframework.authorization.repository.MenuGroupRepository;
import com.brijframework.authorization.repository.MenuItemRepository;
import com.brijframework.authorization.repository.RoleHeaderItemRepository;
import com.brijframework.authorization.repository.RoleMenuGroupRepository;
import com.brijframework.authorization.repository.RoleMenuItemRepository;
import com.brijframework.authorization.repository.UserAccountRepository;
import com.brijframework.authorization.repository.UserOnBoardingRepository;
import com.brijframework.authorization.repository.UserProfileRepository;
import com.brijframework.authorization.repository.UserRoleRepository;

@Component
public class AuthorizationMainListener implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private MenuItemRepository globalMenuItemRepository;
	
	@Autowired
	private MenuGroupRepository globalMenuGroupRepository;
	
	@Autowired
	private RoleMenuGroupRepository roleMenuGroupRepository;
	
	@Autowired
	private RoleMenuItemRepository roleMenuItemRepository;
	
	@Autowired
	private HeaderItemRepository headerItemRepository;
	
	@Autowired
	private RoleHeaderItemRepository roleHeaderItemRepository;
	
	@Autowired
	private UserOnBoardingRepository userOnBoardingRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value("${spring.db.datajson.upload}")
	boolean upload;
	
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
    	Map<Integer, EOUserRole> userRoleMap = userRoleRepository.findAll().parallelStream().collect(Collectors.toMap(EOUserRole::getPosition, Function.identity()));
    	Map<String, EOUserAccount> userAccountMap = userAccountRepository.findAll().parallelStream().collect(Collectors.toMap(EOUserAccount::getUsername, Function.identity()));
    	if(upload) {
	    	JsonSchemaDataFactory instance = JsonSchemaDataFactory.getInstance();
	    	List<EOUserRole> userRoleList = instance.getAll(EOUserRole.class);
	    	for (EOUserRole userRole : userRoleList) {
	    		EOUserRole eoUserRole = userRoleMap.getOrDefault(userRole.getPosition(),userRole);
	    		BeanUtils.copyProperties(userRole, eoUserRole, "id");
	    		EOUserRole saveUserRole=userRoleRepository.save(eoUserRole);
	    		userRole.setId(saveUserRole.getId());
	    		userRoleMap.put(userRole.getPosition(), userRole);
	    		if(Authority.ADMIN.getRoleType().equalsIgnoreCase(userRole.getRoleType())) {
	    			EOUserAccount eoUserAccount = userAccountMap.getOrDefault(eoUserRole.getRoleName(), new EOUserAccount());
    	    		eoUserAccount.setAccountName(eoUserRole.getRoleName());
    	    		eoUserAccount.setUsername(eoUserRole.getRoleName());
    	    		eoUserAccount.setType(eoUserRole.getRoleName());
    	    		//if(eoUserAccount.getPassword()==null) {
    	    			eoUserAccount.setPassword(passwordEncoder.encode(eoUserRole.getRoleName()));
    	    		//}
    	    			
    	    		eoUserAccount.setUserRole(eoUserRole);
    	    		eoUserAccount=userAccountRepository.save(eoUserAccount);
    	    		if(eoUserAccount.getUserProfile()==null) {
	    	    		EOUserProfile eoUserProfile=   new EOUserProfile();
	    	    		eoUserProfile.setFullName(eoUserRole.getRoleName());
	    	    		eoUserProfile.setUserAccount(eoUserAccount);
	    	    		userProfileRepository.save(eoUserProfile);
    	    		}
	    		}
	    	}
	    	List<EOMenuGroup> globalMenuGroupList = instance.getAll(EOMenuGroup.class);
	    	Map<String, EOMenuGroup> globalMenuGroupMap = globalMenuGroupRepository.findAll().stream().collect(Collectors.toMap(EOMenuGroup::getIdenNo, Function.identity()));
	    	for (EOMenuGroup globalMenuGroup : globalMenuGroupList) {
	    		try {	
		    		EOMenuGroup eoUserEndpoint = globalMenuGroupMap.getOrDefault(globalMenuGroup.getIdenNo(),globalMenuGroup);
		    		BeanUtils.copyProperties(globalMenuGroup, eoUserEndpoint, "id");
		    		EOMenuGroup saveGlobalMenuGroup = globalMenuGroupRepository.saveAndFlush(eoUserEndpoint);
		    		globalMenuGroup.setId(saveGlobalMenuGroup.getId());
		    		globalMenuGroupMap.remove(globalMenuGroup.getIdenNo());
	    		}catch (Exception e) {
					System.out.println("globalMenuGroup="+globalMenuGroup);
					e.printStackTrace();
				}
			}
	    	List<EOMenuItem> globalMenuItemList = instance.getAll(EOMenuItem.class);
	    	Map<String, EOMenuItem> globalMenuItemMap = globalMenuItemRepository.findAll()
	    			.stream().collect(Collectors.toMap(EOMenuItem::getIdenNo, Function.identity()));
	    	for (EOMenuItem globalMenuItem : globalMenuItemList) {
	    		try {	
	    			EOMenuItem eoGlobalMenuItem = globalMenuItemMap.getOrDefault(globalMenuItem.getIdenNo(),globalMenuItem);
		    		BeanUtils.copyProperties(globalMenuItem, eoGlobalMenuItem, "id");
		    		EOMenuItem saveGlobalMenuItem = globalMenuItemRepository.saveAndFlush(eoGlobalMenuItem);
		    		globalMenuItem.setId(saveGlobalMenuItem.getId());
		    		globalMenuItemMap.remove(globalMenuItem.getIdenNo());
		    	}catch (Exception e) {
					System.out.println("globalMenuItem="+globalMenuItem);
					e.printStackTrace();
				}
			}
	    	Map<String, EORoleMenuGroup> roleMenuGroupMap = roleMenuGroupRepository.findAll().parallelStream().collect(Collectors.toMap((userRoleMenuGroup)->getRoleMenuGroupKey(userRoleMenuGroup), Function.identity()));
	    	List<EORoleMenuGroup> roleMenuGroups = instance.getAll(EORoleMenuGroup.class);
	    	for(EORoleMenuGroup roleMenuGroup: roleMenuGroups) {
	    		try {
	    			String roleMenuGroupKey= getRoleMenuGroupKey(roleMenuGroup);
					EORoleMenuGroup eoRoleMenuGroup = roleMenuGroupMap.getOrDefault(roleMenuGroupKey,roleMenuGroup);
					BeanUtils.copyProperties(roleMenuGroup, eoRoleMenuGroup, "id");
		    		EORoleMenuGroup saveRoleMenuGroup = roleMenuGroupRepository.saveAndFlush(eoRoleMenuGroup);
		    		roleMenuGroup.setId(saveRoleMenuGroup.getId());
		    		eoRoleMenuGroup.setId(saveRoleMenuGroup.getId());
		    		roleMenuGroupMap.remove(roleMenuGroupKey);
	    		}catch (Exception e) {
					System.out.println("roleMenuGroup="+roleMenuGroup);
					e.printStackTrace();
				}
			}
	    	
	    	Map<String, EORoleMenuItem> deleteRoleMenuItemMap = roleMenuItemRepository.findAll().parallelStream().collect(Collectors.toMap((roleMenuItem)->getRoleMenuItemKey(roleMenuItem), Function.identity()));
	    	Map<String, EORoleMenuItem> checkRoleMenuItemMap = roleMenuItemRepository.findAll().parallelStream().collect(Collectors.toMap((roleMenuItem)->getRoleMenuItemKey(roleMenuItem), Function.identity()));
	    	List<EORoleMenuItem> roleEndpointList = instance.getAll(EORoleMenuItem.class);
	    	for(EORoleMenuItem roleMenuItem: roleEndpointList) {
	    		try {
	    			String roleMenuItemKey= getRoleMenuItemKey(roleMenuItem);
					EORoleMenuItem eoRoleEndpoint = checkRoleMenuItemMap.getOrDefault(roleMenuItemKey,roleMenuItem);
					BeanUtils.copyProperties(roleMenuItem, eoRoleEndpoint, "id");
		    		EORoleMenuItem saveRoleEndpoint = roleMenuItemRepository.saveAndFlush(eoRoleEndpoint);
		    		roleMenuItem.setId(saveRoleEndpoint.getId());
		    		deleteRoleMenuItemMap.remove(roleMenuItemKey);
	    		}catch (Exception e) {
					System.out.println("roleEndpoint="+roleMenuItem);
					e.printStackTrace();
				}
			}
	    	
	    	Map<String, EOHeaderItem> headerItemMap = headerItemRepository.findAll().parallelStream().collect(Collectors.toMap((headerItem)->headerItem.getIdenNo(), Function.identity()));
	    	List<EOHeaderItem> headerItemList = instance.getAll(EOHeaderItem.class);
	    	for(EOHeaderItem headerItem: headerItemList) {
	    		try {
	    			EOHeaderItem eoHeaderItem = headerItemMap.getOrDefault(headerItem.getIdenNo(),headerItem);
					BeanUtils.copyProperties(headerItem, eoHeaderItem, "id");
					EOHeaderItem saveHeaderItem = headerItemRepository.saveAndFlush(eoHeaderItem);
		    		headerItem.setId(saveHeaderItem.getId());
		    		headerItemMap.remove(headerItem.getIdenNo());
	    		}catch (Exception e) {
					System.out.println("headerItem="+headerItem);
					e.printStackTrace();
				}
			}
	    	
	    	Map<String, EORoleHeaderItem> roleHeaderItemMap = roleHeaderItemRepository.findAll().parallelStream().collect(Collectors.toMap((roleHeaderItem)->getRoleHeaderItemKey(roleHeaderItem), Function.identity()));
	    	System.out.println("roleHeaderItemMap="+roleHeaderItemMap);
	    	List<EORoleHeaderItem> userRoleHeaderItemList = instance.getAll(EORoleHeaderItem.class);
	    	for(EORoleHeaderItem roleHeaderItem: userRoleHeaderItemList) {
	    		try {
	    			String roleHeaderItemKey=getRoleHeaderItemKey(roleHeaderItem);
	    			EORoleHeaderItem eoRoleHeaderItem = roleHeaderItemMap.getOrDefault(roleHeaderItemKey,roleHeaderItem);
					BeanUtils.copyProperties(roleHeaderItem, eoRoleHeaderItem, "id");
					EORoleHeaderItem saveRoleHeaderItem = roleHeaderItemRepository.saveAndFlush(eoRoleHeaderItem);
		    		roleHeaderItem.setId(saveRoleHeaderItem.getId());
		    		roleHeaderItemMap.remove(roleHeaderItemKey);
	    		}catch (Exception e) {
					System.out.println("roleHeaderItem="+roleHeaderItem);
					e.printStackTrace();
				}
			}
	    	
	    	if(!roleHeaderItemMap.isEmpty()) {
	    		roleHeaderItemRepository.deleteAll(roleHeaderItemMap.values());
	    	}
	    	if(!headerItemMap.isEmpty()) {
	    		headerItemRepository.deleteAll(headerItemMap.values());
	    	}
	    	if(!deleteRoleMenuItemMap.isEmpty()) {
	    		Collection<EORoleMenuItem> values = deleteRoleMenuItemMap.values();
	    		for(EORoleMenuItem eoRoleMenuItem :  values) {
	    			userOnBoardingRepository.deleteByRoleMenuItem(eoRoleMenuItem);
	    		}
	    		if(!values.isEmpty())
	    		roleMenuItemRepository.deleteAll(values);
	    	}
	    	if(!roleMenuGroupMap.isEmpty()) {
	    		roleMenuGroupRepository.deleteAll(roleMenuGroupMap.values());
	    	}
	    	if(!globalMenuItemMap.isEmpty()) {
	    		globalMenuItemRepository.deleteAll(globalMenuItemMap.values());
	    	}
	    	if(!globalMenuGroupMap.isEmpty()) {
	    		globalMenuGroupRepository.deleteAll(globalMenuGroupMap.values());
	    	}
    	}
    }

	private String getRoleMenuItemKey(EORoleMenuItem userRoleMenuItem) {
		if(userRoleMenuItem.getMenuItem()==null || userRoleMenuItem.getUserRole()==null) {
			return "";
		}
		return userRoleMenuItem.getUserRole().getId()+"_"+ userRoleMenuItem.getMenuItem().getId();
	}

	private String getRoleMenuGroupKey(EORoleMenuGroup userRoleMenuGroup) {
		if(userRoleMenuGroup.getMenuGroup()==null || userRoleMenuGroup.getUserRole()==null) {
			return "";
		}
		return userRoleMenuGroup.getUserRole().getId()+"_"+ userRoleMenuGroup.getMenuGroup().getId();
	}
    
    public static String getRoleHeaderItemKey(EORoleHeaderItem eoRoleHeaderItem) {
    	if(eoRoleHeaderItem.getHeaderItem()==null || eoRoleHeaderItem.getUserRole()==null) {
			return "";
		}
    	return eoRoleHeaderItem.getUserRole().getId()+"_"+eoRoleHeaderItem.getHeaderItem().getId();
    }
}