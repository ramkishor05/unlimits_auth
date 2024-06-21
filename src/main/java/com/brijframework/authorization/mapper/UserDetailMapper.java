package com.brijframework.authorization.mapper;

import static com.brijframework.authorization.contants.Constants.COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL;
import static com.brijframework.authorization.contants.Constants.SPRING;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.authorization.constant.Authority;
import com.brijframework.authorization.global.beans.UIHeaderItem;
import com.brijframework.authorization.global.beans.UIMenuGroup;
import com.brijframework.authorization.global.beans.UIMenuItem;
import com.brijframework.authorization.global.beans.UIUserAccount;
import com.brijframework.authorization.global.beans.UIUserDetail;
import com.brijframework.authorization.global.beans.UIUserOnBoarding;
import com.brijframework.authorization.global.beans.UIUserOnBoardingQuestion;
import com.brijframework.authorization.global.beans.UIUserProfile;
import com.brijframework.authorization.global.beans.UserDetailResponse;
import com.brijframework.authorization.global.beans.UserRoleResponse;
import com.brijframework.authorization.model.EOUserAccount;
import com.brijframework.authorization.model.EOUserProfile;
import com.brijframework.authorization.model.EOUserRole;
import com.brijframework.authorization.model.headers.EOHeaderItem;
import com.brijframework.authorization.model.headers.EORoleHeaderItem;
import com.brijframework.authorization.model.menus.EOMenuGroup;
import com.brijframework.authorization.model.menus.EOMenuItem;
import com.brijframework.authorization.model.menus.EORoleMenuGroup;
import com.brijframework.authorization.model.menus.EORoleMenuItem;
import com.brijframework.authorization.model.onboarding.EOUserOnBoarding;
import com.brijframework.authorization.model.onboarding.EOUserOnBoardingQuestion;

@Mapper(componentModel = SPRING, implementationPackage = COM_BRIJFRAMEWORK_AUTHORIZATION_MAPPER_IMPL)
public interface UserDetailMapper extends GenericMapper<EOUserAccount, UserDetailResponse> {
	
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
        userDetailResponse.setOnBoardingList( mapUserOnBoardingListToUserOnBoardingListDTO( eoUserAccount.getOnBoardingList() ) );
        
        List<UIUserOnBoarding> onBoardingList = userDetailResponse.getOnBoardingList();
        
        Map<String, UIUserOnBoarding> onBoardingMenuIdenNoMap = onBoardingList.stream().collect(Collectors.toMap((uiUserOnBoarding)->uiUserOnBoarding.getRoleMenuItem().getIdenNo(), (uiUserOnBoarding)->uiUserOnBoarding));
        
        Map<Integer, Boolean> onBoardingLevelStatusMap = onBoardingList.stream().collect(Collectors.toMap(UIUserOnBoarding::getOnBoardingLevel, UIUserOnBoarding::getOnBoardingStatus));
        if(userDetailResponse.getOnBoarding()) {
            List<UIMenuGroup> uiMenuGroups = userDetailResponse.getUserRole().getRoleMenuGroups();
	        for(UIMenuGroup uiMenuGroup : uiMenuGroups) {
	        	List<UIMenuItem> menuItems = uiMenuGroup.getMenuItems();
	        	checkOnBoardingStatus(onBoardingMenuIdenNoMap, onBoardingLevelStatusMap, menuItems);
	        	//uiMenuGroup.setDisabled(menuItems.stream().filter(menuItem->!menuItem.getDisabled()).count()==0);
	        }
	        List<UIMenuItem> menuItems = userDetailResponse.getUserRole().getRoleMenuItems();
	        checkOnBoardingStatus(onBoardingMenuIdenNoMap, onBoardingLevelStatusMap, menuItems);
        }
        return userDetailResponse;
	}

	default void checkOnBoardingStatus(Map<String, UIUserOnBoarding> onBoardingMenuIdenNoMap,
			Map<Integer, Boolean> onBoardingLevelStatusMap, List<UIMenuItem> menuItems) {
		for(UIMenuItem uiMenuItem: menuItems) {
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

    public default List<UIUserOnBoarding> mapUserOnBoardingListToUserOnBoardingListDTO(List<EOUserOnBoarding> onBoardingList) {
        if ( onBoardingList == null ) {
            return null;
        }

        List<UIUserOnBoarding> list = new ArrayList<UIUserOnBoarding>( onBoardingList.size() );
        for ( EOUserOnBoarding eOUserOnBoarding : onBoardingList ) {
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
    
	public UIUserOnBoarding mapUserOnBoardingToUserOnBoardingDTO(EOUserOnBoarding eOUserOnBoarding);

	UIUserProfile mapUserProfileToUserProfileDTO(EOUserProfile userProfile);

	UserRoleResponse userRole(EOUserRole eoUserRole);
	
	default List<UIMenuGroup> mapRoleMenuGroupsToMenuGroupDTOs(List<EORoleMenuGroup> eoRoleMenuGroupList) {
		List<UIMenuGroup> uiMenuGroups=new ArrayList<UIMenuGroup>();
		for(EORoleMenuGroup eoRoleMenuGroup: eoRoleMenuGroupList) {
			uiMenuGroups.add(mapRoleMenuGroupToMenuGroupDTO(eoRoleMenuGroup));
		}
		uiMenuGroups.sort((m1,m2)->m1.getOrderSequence().compareTo(m2.getOrderSequence()));
		return uiMenuGroups;
	}
	
	default UIMenuGroup mapRoleMenuGroupToMenuGroupDTO(EORoleMenuGroup eoRoleMenuGroup) {
		UIMenuGroup uiMenuGroup = mapToMenuGroupDTO(eoRoleMenuGroup.getMenuGroup());
		uiMenuGroup.setIdenNo(eoRoleMenuGroup.getIdenNo());
		uiMenuGroup.setId(eoRoleMenuGroup.getId());
		uiMenuGroup.setMenuItems(mapRoleMenuItemToMenuItemDTOs(eoRoleMenuGroup.getRoleMenuItems()));
		return uiMenuGroup;
	}
	
	UIMenuGroup mapToMenuGroupDTO(EOMenuGroup eoMenuGroup);
	
	default List<UIMenuItem> mapRoleMenuItemToMenuItemDTOs(List<EORoleMenuItem> eoRoleMenuItemList) {
		List<UIMenuItem> uiMenuItems=new ArrayList<UIMenuItem>();
		for(EORoleMenuItem eoRoleMenuItem: eoRoleMenuItemList) {
			uiMenuItems.add(mapRoleMenuItemToMenuItemDTO(eoRoleMenuItem));
		}
		uiMenuItems.sort((m1,m2)->m1.getOrderSequence().compareTo(m2.getOrderSequence()));
		return uiMenuItems;
	}
	
	default UIMenuItem mapRoleMenuItemToMenuItemDTO(EORoleMenuItem eoRoleMenuItem) {
		UIMenuItem uiMenuItem = mapToMenuItemDTO(eoRoleMenuItem.getMenuItem());
		uiMenuItem.setIdenNo(eoRoleMenuItem.getIdenNo());
		uiMenuItem.setId(eoRoleMenuItem.getId());
		uiMenuItem.setHomePage(eoRoleMenuItem.isHomePage());
		uiMenuItem.setOnBoarding(eoRoleMenuItem.getOnBoarding());	
		return uiMenuItem;
	}
	
	UIMenuItem mapToMenuItemDTO(EOMenuItem eoMenuItem);
	
	default List<UIHeaderItem> mapRoleHeaderItemToHeaderItemDTOs(List<EORoleHeaderItem> eoRoleHeaderItemList) {
		List<UIHeaderItem> uiHeaderItems=new ArrayList<UIHeaderItem>();
		for(EORoleHeaderItem eoRoleHeaderItem: eoRoleHeaderItemList) {
			uiHeaderItems.add(mapRoleHeaderItemToHeaderItemDTO(eoRoleHeaderItem));
		}
		uiHeaderItems.sort((m1,m2)->m1.getOrderSequence().compareTo(m2.getOrderSequence()));
		return uiHeaderItems;
	}
	
	default UIHeaderItem mapRoleHeaderItemToHeaderItemDTO(EORoleHeaderItem eoRoleHeaderItem) {
		UIHeaderItem headerItem = mapRoleHeaderItemToHeaderItemDTO(eoRoleHeaderItem.getHeaderItem());
		return headerItem;
	}

	UIHeaderItem mapRoleHeaderItemToHeaderItemDTO(EOHeaderItem eoHeaderItem);

	@Mapping(target = "authority", source = "userRole")
	UIUserDetail mapToDetailDTO(EOUserAccount eoUserAccount);
	
	default Authority authority(EOUserRole eoUserRole) {
		return Authority.valueOf(eoUserRole.getRoleId());
	}
	
	EOUserOnBoardingQuestion mapToUserOnBoardingQuestionDAO(UIUserOnBoardingQuestion uiUserOnBoardingQuestion);

	UIUserOnBoardingQuestion mapToUserOnBoardingQuestionDTO(EOUserOnBoardingQuestion eoUserOnBoardingQuestion);
	
}
