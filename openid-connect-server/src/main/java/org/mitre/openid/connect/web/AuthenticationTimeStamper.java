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
/**
 * 
 */
package org.mitre.openid.connect.web;

import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mitre.openid.connect.filter.AuthorizationRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * This class sets a timestamp on the current HttpSession
 * when someone successfully authenticates.
 * 
 * @author jricher
 *
 */
@Component("authenticationTimeStamper")
public class AuthenticationTimeStamper extends SavedRequestAwareAuthenticationSuccessHandler {
    
    @Value("${openid.server.login.defaultUrl:/admin/index}") 
    private String defaultRedirectUrl;
    
    /**
     * Dfault constructor
     */
    public AuthenticationTimeStamper() {
        // dfault constructor needed by spring framework
    }
    
    public AuthenticationTimeStamper(@Value("${openid.server.login.defaultUrl:/admin/index}") String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }
    
    @PostConstruct
    public void afterPropertiesSet () { 
        if (!StringUtils.isEmpty(defaultRedirectUrl) && defaultRedirectUrl.startsWith("/") &&
            !super.getDefaultTargetUrl().equalsIgnoreCase(defaultRedirectUrl)) {
            super.setDefaultTargetUrl(defaultRedirectUrl);
        }
    }

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationTimeStamper.class);

	public static final String AUTH_TIMESTAMP = "AUTH_TIMESTAMP";

	/**
	 * Set the timestamp on the session to mark when the authentication happened,
	 * useful for calculating authentication age. This gets stored in the sesion
	 * and can get pulled out by other components.
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		Date authTimestamp = new Date();

		HttpSession session = request.getSession();

		session.setAttribute(AUTH_TIMESTAMP, authTimestamp);

		if (session.getAttribute(AuthorizationRequestFilter.PROMPT_REQUESTED) != null) {
			session.setAttribute(AuthorizationRequestFilter.PROMPTED, Boolean.TRUE);
			session.removeAttribute(AuthorizationRequestFilter.PROMPT_REQUESTED);
		}

		logger.info("Successful Authentication of " + authentication.getName() + " at " + authTimestamp.toString());

		super.onAuthenticationSuccess(request, response, authentication);

	}

}
