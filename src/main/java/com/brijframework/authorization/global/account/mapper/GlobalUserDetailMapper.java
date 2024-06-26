package com.brijframework.authorization.global.account.mapper;

import static com.brijframework.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframework.authorization.contants.Constants.SPRING;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.entities.EOUserProfile;
import com.brijframework.authorization.account.entities.EOUserRole;
import com.brijframework.authorization.account.entities.headers.EOUserRoleHeaderItem;
import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuGroup;
import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuItem;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingBilling;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingMenu;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingQuestion;
import com.brijframework.authorization.account.model.UIUserAccount;
import com.brijframework.authorization.account.model.UIUserDetail;
import com.brijframework.authorization.account.model.UIUserProfile;
import com.brijframework.authorization.account.model.UserDetailResponse;
import com.brijframework.authorization.account.model.UserRoleResponse;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoarding;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingBilling;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingQuestion;
import com.brijframework.authorization.constant.Authority;
import com.brijframework.authorization.view.entities.headers.EOViewHeaderItem;
import com.brijframework.authorization.view.entities.menus.EOViewMenuGroup;
import com.brijframework.authorization.view.entities.menus.EOViewMenuItem;
import com.brijframework.authorization.view.model.menus.UIViewHeaderItem;
import com.brijframework.authorization.view.model.menus.UIViewMenuGroup;
import com.brijframework.authorization.view.model.menus.UIViewMenuItem;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface GlobalUserDetailMapper extends GenericMapper<EOUserAccount, UserDetailResponse> {
	
	UIUserAccount mapToUI(EOUserAccount eoUserAccount);
	
	EOUserAccount mapToDBO(UIUserAccount uiUserAccount);

	@Override
	default UserDetailResponse mapToDTO(EOUserAccount eoUserAccount) {
		if ( eoUserAccount == null ) {
            return null;
        }

        UserDetailResponse userDetailResponse = new UserDetailResponse();

        if ( eoUserAccount.getId() != null ) {
            userDetailResponse.setId( eoUserAccount.getId() );
        }
        userDetailResponse.setUsername( eoUserAccount.getUsername() );
        userDetailResponse.setAccountName( eoUserAccount.getAccountName() );
        userDetailResponse.setRegisteredEmail( eoUserAccount.getRegisteredEmail() );
        userDetailResponse.setRegisteredMobile( eoUserAccount.getRegisteredMobile() );
        userDetailResponse.setType( eoUserAccount.getType() );
        userDetailResponse.setOwnerId( eoUserAccount.getOwnerId() );
        userDetailResponse.setOnBoarding( eoUserAccount.getOnBoarding() );
        userDetailResponse.setUserRole( userRole( eoUserAccount.getUserRole() ) );
        userDetailResponse.setUserProfile( mapUserProfileToUserProfileDTO( eoUserAccount.getUserProfile() ) );
        userDetailResponse.setOnBoardingList( mapUserOnBoardingListToUserOnBoardingListDTO( eoUserAccount.getOnBoardingMenuList() ) );
        
        List<UIUserOnBoarding> onBoardingList = userDetailResponse.getOnBoardingList();
        
        Map<String, UIUserOnBoarding> onBoardingMenuIdenNoMap = onBoardingList.stream().collect(Collectors.toMap((uiUserOnBoarding)->uiUserOnBoarding.getRoleMenuItem().getIdenNo(), (uiUserOnBoarding)->uiUserOnBoarding));
        
        Map<Integer, Boolean> onBoardingLevelStatusMap = onBoardingList.stream().collect(Collectors.toMap(UIUserOnBoarding::getOnBoardingLevel, UIUserOnBoarding::getOnBoardingStatus));
        if(userDetailResponse.getOnBoarding()) {
            List<UIViewMenuGroup> uiMenuGroups = userDetailResponse.getUserRole().getRoleMenuGroups();
	        for(UIViewMenuGroup uiMenuGroup : uiMenuGroups) {
	        	List<UIViewMenuItem> menuItems = uiMenuGroup.getMenuItems();
	        	checkOnBoardingStatus(onBoardingMenuIdenNoMap, onBoardingLevelStatusMap, menuItems);
	        	//uiMenuGroup.setDisabled(menuItems.stream().filter(menuItem->!menuItem.getDisabled()).count()==0);
	        }
	        List<UIViewMenuItem> menuItems = userDetailResponse.getUserRole().getRoleMenuItems();
	        checkOnBoardingStatus(onBoardingMenuIdenNoMap, onBoardingLevelStatusMap, menuItems);
        }
        return userDetailResponse;
	}

	default void checkOnBoardingStatus(Map<String, UIUserOnBoarding> onBoardingMenuIdenNoMap,
			Map<Integer, Boolean> onBoardingLevelStatusMap, List<UIViewMenuItem> menuItems) {
		for(UIViewMenuItem uiMenuItem: menuItems) {
			if(uiMenuItem.getDisabled()) {
				continue;
			}
			//uiMenuItem.setDisabled(true);
			if(uiMenuItem.getOnBoarding()) {
				uiMenuItem.setDisabled(false);
				UIUserOnBoarding uiUserOnBoarding = onBoardingMenuIdenNoMap.get(uiMenuItem.getIdenNo());
				if(uiUserOnBoarding==null) {
					continue;
				}
				Boolean preOnBoardingStatus = onBoardingLevelStatusMap.get(uiUserOnBoarding.getOnBoardingLevel()-1);
				if(preOnBoardingStatus!=null) {
					if(!preOnBoardingStatus) {
						//uiMenuItem.setDisabled(true);
					} 
				}
			} 
		}
	}

    public default List<UIUserOnBoarding> mapUserOnBoardingListToUserOnBoardingListDTO(List<EOUserOnBoardingMenu> onBoardingList) {
        if ( onBoardingList == null ) {
            return null;
        }

        List<UIUserOnBoarding> list = new ArrayList<UIUserOnBoarding>( onBoardingList.size() );
        for ( EOUserOnBoardingMenu eOUserOnBoarding : onBoardingList ) {
            list.add( mapUserOnBoardingToUserOnBoardingDTO( eOUserOnBoarding ) );
        }

        list.sort((b1,b2)->b1.getOnBoardingLevel().compareTo(b2.getOnBoardingLevel()));
        
        for(UIUserOnBoarding uiUserOnBoarding: list) {
        	uiUserOnBoarding.setOnBoardingActive(false);
        	if(!uiUserOnBoarding.getOnBoardingStatus()) {
        		uiUserOnBoarding.setOnBoardingActive(true);
        	}
        }
        return list;
    }
    
	public UIUserOnBoarding mapUserOnBoardingToUserOnBoardingDTO(EOUserOnBoardingMenu eOUserOnBoarding);

	UIUserProfile mapUserProfileToUserProfileDTO(EOUserProfile userProfile);

	UserRoleResponse userRole(EOUserRole eoUserRole);
	
	default List<UIViewMenuGroup> mapRoleMenuGroupsToMenuGroupDTOs(List<EOUserRoleMenuGroup> eoRoleMenuGroupList) {
		List<UIViewMenuGroup> uiMenuGroups=new ArrayList<UIViewMenuGroup>();
		for(EOUserRoleMenuGroup eoRoleMenuGroup: eoRoleMenuGroupList) {
			uiMenuGroups.add(mapRoleMenuGroupToMenuGroupDTO(eoRoleMenuGroup));
		}
		uiMenuGroups.sort((m1,m2)->m1.getOrderSequence().compareTo(m2.getOrderSequence()));
		return uiMenuGroups;
	}
	
	default UIViewMenuGroup mapRoleMenuGroupToMenuGroupDTO(EOUserRoleMenuGroup eoRoleMenuGroup) {
		UIViewMenuGroup uiMenuGroup = mapToMenuGroupDTO(eoRoleMenuGroup.getMenuGroup());
		uiMenuGroup.setIdenNo(eoRoleMenuGroup.getIdenNo());
		uiMenuGroup.setId(eoRoleMenuGroup.getId());
		uiMenuGroup.setMenuItems(mapRoleMenuItemToMenuItemDTOs(eoRoleMenuGroup.getRoleMenuItems()));
		return uiMenuGroup;
	}
	
	UIViewMenuGroup mapToMenuGroupDTO(EOViewMenuGroup eoMenuGroup);
	
	default List<UIViewMenuItem> mapRoleMenuItemToMenuItemDTOs(List<EOUserRoleMenuItem> eoRoleMenuItemList) {
		List<UIViewMenuItem> uiMenuItems=new ArrayList<UIViewMenuItem>();
		for(EOUserRoleMenuItem eoRoleMenuItem: eoRoleMenuItemList) {
			uiMenuItems.add(mapRoleMenuItemToMenuItemDTO(eoRoleMenuItem));
		}
		uiMenuItems.sort((m1,m2)->m1.getOrderSequence().compareTo(m2.getOrderSequence()));
		return uiMenuItems;
	}
	
	default UIViewMenuItem mapRoleMenuItemToMenuItemDTO(EOUserRoleMenuItem eoRoleMenuItem) {
		UIViewMenuItem uiMenuItem = mapToMenuItemDTO(eoRoleMenuItem.getMenuItem());
		uiMenuItem.setIdenNo(eoRoleMenuItem.getIdenNo());
		uiMenuItem.setId(eoRoleMenuItem.getId());
		uiMenuItem.setHomePage(eoRoleMenuItem.isHomePage());
		uiMenuItem.setOnBoarding(eoRoleMenuItem.getOnBoarding());	
		return uiMenuItem;
	}
	
	UIViewMenuItem mapToMenuItemDTO(EOViewMenuItem eoMenuItem);
	
	default List<UIViewHeaderItem> mapRoleHeaderItemToHeaderItemDTOs(List<EOUserRoleHeaderItem> eoRoleHeaderItemList) {
		List<UIViewHeaderItem> uiHeaderItems=new ArrayList<UIViewHeaderItem>();
		for(EOUserRoleHeaderItem eoRoleHeaderItem: eoRoleHeaderItemList) {
			uiHeaderItems.add(mapRoleHeaderItemToHeaderItemDTO(eoRoleHeaderItem));
		}
		uiHeaderItems.sort((m1,m2)->m1.getOrderSequence().compareTo(m2.getOrderSequence()));
		return uiHeaderItems;
	}
	
	default UIViewHeaderItem mapRoleHeaderItemToHeaderItemDTO(EOUserRoleHeaderItem eoRoleHeaderItem) {
		UIViewHeaderItem headerItem = mapRoleHeaderItemToHeaderItemDTO(eoRoleHeaderItem.getHeaderItem());
		return headerItem;
	}

	UIViewHeaderItem mapRoleHeaderItemToHeaderItemDTO(EOViewHeaderItem eoHeaderItem);

	@Mapping(target = "authority", source = "userRole")
	UIUserDetail mapToDetailDTO(EOUserAccount eoUserAccount);
	
	default Authority authority(EOUserRole eoUserRole) {
		return Authority.valueOf(eoUserRole.getRoleId());
	}
	
	EOUserOnBoardingQuestion mapToUserOnBoardingQuestionDAO(UIUserOnBoardingQuestion uiUserOnBoardingQuestion);

	UIUserOnBoardingQuestion mapToUserOnBoardingQuestionDTO(EOUserOnBoardingQuestion eoUserOnBoardingQuestion);

	EOUserOnBoardingBilling mapToUserOnBoardingBillingDAO(UIUserOnBoardingBilling userOnBoardingBilling);

	UIUserOnBoardingBilling mapToUserOnBoardingBillingDTO(EOUserOnBoardingBilling eoUserOnBoardingBilling);
	
}
