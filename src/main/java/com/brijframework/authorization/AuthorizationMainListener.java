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

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.entities.EOUserProfile;
import com.brijframework.authorization.account.entities.EOUserRole;
import com.brijframework.authorization.account.entities.headers.EOUserRoleHeaderItem;
import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuGroup;
import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuItem;
import com.brijframework.authorization.account.repository.UserAccountRepository;
import com.brijframework.authorization.account.repository.UserOnBoardingRepository;
import com.brijframework.authorization.account.repository.UserProfileRepository;
import com.brijframework.authorization.account.repository.UserRoleHeaderItemRepository;
import com.brijframework.authorization.account.repository.UserRoleMenuGroupRepository;
import com.brijframework.authorization.account.repository.UserRoleMenuItemRepository;
import com.brijframework.authorization.account.repository.UserRoleRepository;
import com.brijframework.authorization.constant.Authority;
import com.brijframework.authorization.constant.BillingType;
import com.brijframework.authorization.global.account.service.UserOnBoardingQuestionService;
import com.brijframework.authorization.view.entities.headers.EOViewHeaderItem;
import com.brijframework.authorization.view.entities.menus.EOViewMenuGroup;
import com.brijframework.authorization.view.entities.menus.EOViewMenuItem;
import com.brijframework.authorization.view.entities.onboarding.EOViewOnBoardingBilling;
import com.brijframework.authorization.view.entities.onboarding.EOViewOnBoardingOptions;
import com.brijframework.authorization.view.entities.onboarding.EOViewOnBoardingQuestion;
import com.brijframework.authorization.view.repository.ViewHeaderItemRepository;
import com.brijframework.authorization.view.repository.ViewMenuGroupRepository;
import com.brijframework.authorization.view.repository.ViewMenuItemRepository;
import com.brijframework.authorization.view.repository.ViewOnBoardingBillingRepository;
import com.brijframework.authorization.view.repository.ViewOnBoardingOptionsRepository;
import com.brijframework.authorization.view.repository.ViewOnBoardingQuestionRepository;

@Component
public class AuthorizationMainListener implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private ViewMenuItemRepository globalMenuItemRepository;
	
	@Autowired
	private ViewMenuGroupRepository globalMenuGroupRepository;
	
	@Autowired
	private UserRoleMenuGroupRepository roleMenuGroupRepository;
	
	@Autowired
	private UserRoleMenuItemRepository roleMenuItemRepository;
	
	@Autowired
	private ViewHeaderItemRepository headerItemRepository;
	
	@Autowired
	private UserRoleHeaderItemRepository roleHeaderItemRepository;
	
	@Autowired
	private UserOnBoardingRepository userOnBoardingRepository;
	
	@Autowired
	private ViewOnBoardingQuestionRepository onBoardingQuestionRepository;
	
	@Autowired
	private ViewOnBoardingOptionsRepository onBoardingOptionsRepository;
	
	@Autowired
	private ViewOnBoardingBillingRepository onBoardingBillingRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserOnBoardingQuestionService userOnBoardingQuestionService;
	
	public static void main(String[] args) {
    	JsonSchemaDataFactory instance = JsonSchemaDataFactory.getInstance();

		List<EOViewOnBoardingBilling> userRoleList = instance.getAll(EOViewOnBoardingBilling.class);
		System.out.println(userRoleList);
	}
	
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
	    	List<EOViewMenuGroup> globalMenuGroupList = instance.getAll(EOViewMenuGroup.class);
	    	Map<String, EOViewMenuGroup> globalMenuGroupMap = globalMenuGroupRepository.findAll().stream().collect(Collectors.toMap(EOViewMenuGroup::getIdenNo, Function.identity()));
	    	for (EOViewMenuGroup globalMenuGroup : globalMenuGroupList) {
	    		try {	
		    		EOViewMenuGroup eoUserEndpoint = globalMenuGroupMap.getOrDefault(globalMenuGroup.getIdenNo(),globalMenuGroup);
		    		BeanUtils.copyProperties(globalMenuGroup, eoUserEndpoint, "id");
		    		EOViewMenuGroup saveGlobalMenuGroup = globalMenuGroupRepository.saveAndFlush(eoUserEndpoint);
		    		globalMenuGroup.setId(saveGlobalMenuGroup.getId());
		    		globalMenuGroupMap.remove(globalMenuGroup.getIdenNo());
	    		}catch (Exception e) {
					System.out.println("globalMenuGroup="+globalMenuGroup);
					e.printStackTrace();
				}
			}
	    	List<EOViewMenuItem> globalMenuItemList = instance.getAll(EOViewMenuItem.class);
	    	Map<String, EOViewMenuItem> globalMenuItemMap = globalMenuItemRepository.findAll()
	    			.stream().collect(Collectors.toMap(EOViewMenuItem::getIdenNo, Function.identity()));
	    	for (EOViewMenuItem globalMenuItem : globalMenuItemList) {
	    		try {	
	    			EOViewMenuItem eoGlobalMenuItem = globalMenuItemMap.getOrDefault(globalMenuItem.getIdenNo(),globalMenuItem);
		    		BeanUtils.copyProperties(globalMenuItem, eoGlobalMenuItem, "id");
		    		EOViewMenuItem saveGlobalMenuItem = globalMenuItemRepository.saveAndFlush(eoGlobalMenuItem);
		    		globalMenuItem.setId(saveGlobalMenuItem.getId());
		    		globalMenuItemMap.remove(globalMenuItem.getIdenNo());
		    	}catch (Exception e) {
					System.out.println("globalMenuItem="+globalMenuItem);
					e.printStackTrace();
				}
			}
	    	Map<String, EOUserRoleMenuGroup> roleMenuGroupMap = roleMenuGroupRepository.findAll().parallelStream().collect(Collectors.toMap((userRoleMenuGroup)->getRoleMenuGroupKey(userRoleMenuGroup), Function.identity()));
	    	List<EOUserRoleMenuGroup> roleMenuGroups = instance.getAll(EOUserRoleMenuGroup.class);
	    	for(EOUserRoleMenuGroup roleMenuGroup: roleMenuGroups) {
	    		try {
	    			String roleMenuGroupKey= getRoleMenuGroupKey(roleMenuGroup);
					EOUserRoleMenuGroup eoRoleMenuGroup = roleMenuGroupMap.getOrDefault(roleMenuGroupKey,roleMenuGroup);
					BeanUtils.copyProperties(roleMenuGroup, eoRoleMenuGroup, "id");
		    		EOUserRoleMenuGroup saveRoleMenuGroup = roleMenuGroupRepository.saveAndFlush(eoRoleMenuGroup);
		    		roleMenuGroup.setId(saveRoleMenuGroup.getId());
		    		eoRoleMenuGroup.setId(saveRoleMenuGroup.getId());
		    		roleMenuGroupMap.remove(roleMenuGroupKey);
	    		}catch (Exception e) {
					System.out.println("roleMenuGroup="+roleMenuGroup);
					e.printStackTrace();
				}
			}
	    	
	    	Map<String, EOUserRoleMenuItem> deleteRoleMenuItemMap = roleMenuItemRepository.findAll().parallelStream().collect(Collectors.toMap((roleMenuItem)->getRoleMenuItemKey(roleMenuItem), Function.identity()));
	    	Map<String, EOUserRoleMenuItem> checkRoleMenuItemMap = roleMenuItemRepository.findAll().parallelStream().collect(Collectors.toMap((roleMenuItem)->getRoleMenuItemKey(roleMenuItem), Function.identity()));
	    	List<EOUserRoleMenuItem> roleEndpointList = instance.getAll(EOUserRoleMenuItem.class);
	    	for(EOUserRoleMenuItem roleMenuItem: roleEndpointList) {
	    		try {
	    			String roleMenuItemKey= getRoleMenuItemKey(roleMenuItem);
					EOUserRoleMenuItem eoRoleEndpoint = checkRoleMenuItemMap.getOrDefault(roleMenuItemKey,roleMenuItem);
					BeanUtils.copyProperties(roleMenuItem, eoRoleEndpoint, "id");
		    		EOUserRoleMenuItem saveRoleEndpoint = roleMenuItemRepository.saveAndFlush(eoRoleEndpoint);
		    		roleMenuItem.setId(saveRoleEndpoint.getId());
		    		deleteRoleMenuItemMap.remove(roleMenuItemKey);
	    		}catch (Exception e) {
					System.out.println("roleEndpoint="+roleMenuItem);
					e.printStackTrace();
				}
			}
	    	
	    	Map<String, EOViewHeaderItem> headerItemMap = headerItemRepository.findAll().parallelStream().collect(Collectors.toMap((headerItem)->headerItem.getIdenNo(), Function.identity()));
	    	List<EOViewHeaderItem> headerItemList = instance.getAll(EOViewHeaderItem.class);
	    	for(EOViewHeaderItem headerItem: headerItemList) {
	    		try {
	    			EOViewHeaderItem eoHeaderItem = headerItemMap.getOrDefault(headerItem.getIdenNo(),headerItem);
					BeanUtils.copyProperties(headerItem, eoHeaderItem, "id");
					EOViewHeaderItem saveHeaderItem = headerItemRepository.saveAndFlush(eoHeaderItem);
		    		headerItem.setId(saveHeaderItem.getId());
		    		headerItemMap.remove(headerItem.getIdenNo());
	    		}catch (Exception e) {
					System.out.println("headerItem="+headerItem);
					e.printStackTrace();
				}
			}
	    	
	    	Map<String, EOUserRoleHeaderItem> roleHeaderItemMap = roleHeaderItemRepository.findAll().parallelStream().collect(Collectors.toMap((roleHeaderItem)->getRoleHeaderItemKey(roleHeaderItem), Function.identity()));
	    	List<EOUserRoleHeaderItem> userRoleHeaderItemList = instance.getAll(EOUserRoleHeaderItem.class);
	    	for(EOUserRoleHeaderItem roleHeaderItem: userRoleHeaderItemList) {
	    		try {
	    			String roleHeaderItemKey=getRoleHeaderItemKey(roleHeaderItem);
	    			EOUserRoleHeaderItem eoRoleHeaderItem = roleHeaderItemMap.getOrDefault(roleHeaderItemKey,roleHeaderItem);
					BeanUtils.copyProperties(roleHeaderItem, eoRoleHeaderItem, "id");
					EOUserRoleHeaderItem saveRoleHeaderItem = roleHeaderItemRepository.saveAndFlush(eoRoleHeaderItem);
		    		roleHeaderItem.setId(saveRoleHeaderItem.getId());
		    		roleHeaderItemMap.remove(roleHeaderItemKey);
	    		}catch (Exception e) {
					System.out.println("roleHeaderItem="+roleHeaderItem);
					e.printStackTrace();
				}
			}
	    	Map<String, EOViewOnBoardingQuestion> onBoardingQuestionMap = onBoardingQuestionRepository.findAll().parallelStream().collect(Collectors.toMap((onBoardingQuestion)->onBoardingQuestion.getQuestion(), Function.identity()));
	    	List<EOViewOnBoardingQuestion> onBoardingQuestionList = instance.getAll(EOViewOnBoardingQuestion.class);
	    	
	    	for(EOViewOnBoardingQuestion onBoardingQuestion: onBoardingQuestionList) {
	    		try {
	    			EOViewOnBoardingQuestion eoOnBoardingQuestion = onBoardingQuestionMap.getOrDefault(onBoardingQuestion.getQuestion(),onBoardingQuestion);
					BeanUtils.copyProperties(onBoardingQuestion, eoOnBoardingQuestion, "id");
					EOViewOnBoardingQuestion saveOnBoardingQuestion = onBoardingQuestionRepository.saveAndFlush(eoOnBoardingQuestion);
					onBoardingQuestion.setId(saveOnBoardingQuestion.getId());
		    		onBoardingQuestionMap.remove(onBoardingQuestion.getQuestion());
	    		}catch (Exception e) {
					System.out.println("onBoardingQuestion="+onBoardingQuestion);
					e.printStackTrace();
				}
			}
	    	
	    	Map<String, EOViewOnBoardingOptions> onBoardingOptionsMap = onBoardingOptionsRepository.findAll().parallelStream().collect(Collectors.toMap((onBoardingOptions)->onBoardingOptions.getValue(), Function.identity()));
	    	List<EOViewOnBoardingOptions> onBoardingOptionsList = instance.getAll(EOViewOnBoardingOptions.class);
	    	
	    	for(EOViewOnBoardingOptions onBoardingOptions: onBoardingOptionsList) {
	    		try {
	    			EOViewOnBoardingOptions eoOnBoardingOptions = onBoardingOptionsMap.getOrDefault(onBoardingOptions.getValue(),onBoardingOptions);
					BeanUtils.copyProperties(onBoardingOptions, eoOnBoardingOptions, "id");
					EOViewOnBoardingOptions saveOnBoardingOptions = onBoardingOptionsRepository.saveAndFlush(eoOnBoardingOptions);
					onBoardingOptions.setId(saveOnBoardingOptions.getId());
		    		onBoardingOptionsMap.remove(onBoardingOptions.getValue());
	    		}catch (Exception e) {
					System.out.println("onBoardingQuestion="+onBoardingOptions);
					e.printStackTrace();
				}
			}
	    	
	    	Map<BillingType, EOViewOnBoardingBilling> onBoardingBillingMap = onBoardingBillingRepository.findAll().parallelStream().collect(Collectors.toMap((onBoardingOptions)->onBoardingOptions.getType(), Function.identity()));
	    	List<EOViewOnBoardingBilling> onBoardingBillingList = instance.getAll(EOViewOnBoardingBilling.class);
	    	
	    	for(EOViewOnBoardingBilling onBoardingBilling: onBoardingBillingList) {
	    		try {
	    			EOViewOnBoardingBilling eoOnBoardingBilling = onBoardingBillingMap.getOrDefault(onBoardingBilling.getType(),onBoardingBilling);
					BeanUtils.copyProperties(onBoardingBilling, eoOnBoardingBilling, "id");
					EOViewOnBoardingBilling saveOnBoardingBilling = onBoardingBillingRepository.saveAndFlush(eoOnBoardingBilling);
					onBoardingBilling.setId(saveOnBoardingBilling.getId());
		    		onBoardingBillingMap.remove(onBoardingBilling.getType());
	    		}catch (Exception e) {
					System.out.println("onBoardingBilling="+onBoardingBilling);
					e.printStackTrace();
				}
			}
	    	
	    	if(!onBoardingBillingMap.isEmpty()) {
	    		onBoardingBillingRepository.deleteAll(onBoardingBillingMap.values());
	    	}
	    	
	    	if(!onBoardingOptionsMap.isEmpty()) {
	    		onBoardingOptionsRepository.deleteAll(onBoardingOptionsMap.values());
	    	}
	    	
	    	if(!onBoardingQuestionMap.isEmpty()) {
	    		onBoardingQuestionRepository.deleteAll(onBoardingQuestionMap.values());
	    	}
	    	if(!roleHeaderItemMap.isEmpty()) {
	    		roleHeaderItemRepository.deleteAll(roleHeaderItemMap.values());
	    	}
	    	if(!headerItemMap.isEmpty()) {
	    		headerItemRepository.deleteAll(headerItemMap.values());
	    	}
	    	if(!deleteRoleMenuItemMap.isEmpty()) {
	    		Collection<EOUserRoleMenuItem> values = deleteRoleMenuItemMap.values();
	    		for(EOUserRoleMenuItem eoRoleMenuItem :  values) {
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
	    	
	    	userAccountRepository.findAll().forEach(userAccount->{
	    		userOnBoardingQuestionService.initOnBoarding(userAccount);
	    	});
    	}
    }

	private String getRoleMenuItemKey(EOUserRoleMenuItem userRoleMenuItem) {
		if(userRoleMenuItem.getMenuItem()==null || userRoleMenuItem.getUserRole()==null) {
			return "";
		}
		return userRoleMenuItem.getUserRole().getId()+"_"+ userRoleMenuItem.getMenuItem().getId();
	}

	private String getRoleMenuGroupKey(EOUserRoleMenuGroup userRoleMenuGroup) {
		if(userRoleMenuGroup.getMenuGroup()==null || userRoleMenuGroup.getUserRole()==null) {
			return "";
		}
		return userRoleMenuGroup.getUserRole().getId()+"_"+ userRoleMenuGroup.getMenuGroup().getId();
	}
    
    public static String getRoleHeaderItemKey(EOUserRoleHeaderItem eoRoleHeaderItem) {
    	if(eoRoleHeaderItem.getHeaderItem()==null || eoRoleHeaderItem.getUserRole()==null) {
			return "";
		}
    	return eoRoleHeaderItem.getUserRole().getId()+"_"+eoRoleHeaderItem.getHeaderItem().getId();
    }
}