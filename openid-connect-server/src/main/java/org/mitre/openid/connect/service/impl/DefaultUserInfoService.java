/*******************************************************************************
 * Copyright 2016 The MITRE Corporation
 *   and the MIT Internet Trust Consortium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.mitre.openid.connect.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.mitre.oauth2.model.ClientDetailsEntity;
import org.mitre.oauth2.model.ClientDetailsEntity.SubjectType;
import org.mitre.oauth2.service.ClientDetailsEntityService;
import org.mitre.openid.connect.model.DefaultUserInfoRealmDetails;
import org.mitre.openid.connect.model.UserDetailsEntity;
import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.model.UserInfoRealmDetails;
import org.mitre.openid.connect.repository.UserDetailsRepository;
import org.mitre.openid.connect.repository.UserInfoClientAuthorityRepository;
import org.mitre.openid.connect.repository.UserInfoClientDetailsRepository;
import org.mitre.openid.connect.repository.UserInfoClientPropertyRepository;
import org.mitre.openid.connect.repository.UserInfoRealmPropertyRepository;
import org.mitre.openid.connect.repository.UserInfoRealmRepository;
import org.mitre.openid.connect.repository.UserInfoRepository;
import org.mitre.openid.connect.service.PairwiseIdentiferService;
import org.mitre.openid.connect.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the UserInfoService
 * 
 * @author Michael Joseph Walsh, jricher
 * 
 */
@Service
public class DefaultUserInfoService implements UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private UserInfoClientDetailsRepository userInfoDetailsRepository;

	@Autowired
	private UserInfoClientPropertyRepository userInfoPropertyRepository;
	
	@Autowired
	private UserInfoClientAuthorityRepository userInfoAuthorityRepository;
	
	@Autowired
	private UserInfoRealmRepository userInfoRealmRepository;
	
	@Autowired
	private UserInfoRealmPropertyRepository userInfoRealmPropertyRepository;

	@Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
	private ClientDetailsEntityService clientService;

	@Autowired
	private PairwiseIdentiferService pairwiseIdentifierService;

	@Override
	public UserInfo getByUsername(String username) {
	    UserInfo userInfo = userInfoRepository.getByUsername(username);
	    addUserInfoClientRelations(userInfo);
	    addUserInfoRealmRelations(userInfo);
        addUserDetailsProperties(userInfo);
	    
		return userInfo;
	}

    @Override
	public UserInfo getByUsernameAndClientId(String username, String clientId) {

		ClientDetailsEntity client = clientService.loadClientByClientId(clientId);

		UserInfo userInfo = getByUsername(username);

		if (client == null || userInfo == null) {
			return null;
		}

	    addUserInfoClientRelations(userInfo);
		if (SubjectType.PAIRWISE.equals(client.getSubjectType())) {
			String pairwiseSub = pairwiseIdentifierService.getIdentifier(userInfo, client);
			userInfo.setSub(pairwiseSub);
		}

		return userInfo;

	}

	@Override
	public UserInfo getByEmailAddress(String email) {
	    UserInfo userInfo = userInfoRepository.getByEmailAddress(email);
	    addUserInfoClientRelations(userInfo);
		return userInfo;
	}
	
	/**
	 * Adds the relations between client and user_info.
	 * @param userInfo
	 */
    private void addUserInfoClientRelations(final UserInfo userInfo) {
        if (null==userInfo) {
            return;
        }
        userInfo.setAccountDetails(userInfoDetailsRepository.getByUserInfo(userInfo));
        userInfo.setAccountProperties(userInfoPropertyRepository.getByUserInfo(userInfo));
        userInfo.setAccountAuthorities(userInfoAuthorityRepository.getByUserInfo(userInfo));
        
        
    }
    
    /**
     * Adds the relations between realm and user_info.
     * @param userInfo
     */
    private void addUserInfoRealmRelations(UserInfo userInfo) {
        if (null==userInfo) {
            return;
        }
        
        Set<UserInfoRealmDetails> realms = userInfoRealmRepository.getByUserInfo(userInfo);
        if (null!=realms) {
            for (UserInfoRealmDetails userRealm : realms) {
                if (userRealm instanceof DefaultUserInfoRealmDetails) {
                    DefaultUserInfoRealmDetails defaultRealmDetails = DefaultUserInfoRealmDetails.class.cast(userRealm);
                    defaultRealmDetails.setRealmProperties(userInfoRealmPropertyRepository.getByUserInfoAndRealm(userInfo, userRealm.getRealm()));
                }
            }
        }
        userInfo.setAccountRealms(realms);
    }

    /**
     * Adds the user properties to userInfo object.
     * 
     * @param userInfo
     */
    private void addUserDetailsProperties (UserInfo userInfo) {
        if (null == userInfo) {
            return;
        }

        Map<String, String> properties = new HashMap<>();
        UserDetailsEntity user = userDetailsRepository.getByUsername(userInfo.getPreferredUsername());
        if (null != user && user.isEnrollment() && null != user.getUserProperties() && user.getUserProperties()
                .isEmpty()) {
            properties.putAll(user.getUserProperties());
        }
        userInfo.setUserProperties(properties);
    }

}
