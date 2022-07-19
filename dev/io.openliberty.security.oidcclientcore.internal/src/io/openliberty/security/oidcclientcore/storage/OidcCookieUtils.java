/*******************************************************************************
 * Copyright (c) 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package io.openliberty.security.oidcclientcore.storage;

import com.ibm.websphere.ras.annotation.Sensitive;
import com.ibm.websphere.ras.annotation.Trivial;
import com.ibm.ws.security.common.crypto.HashUtils;

import io.openliberty.security.oidcclientcore.utils.Utils;

public class OidcCookieUtils {

    @Sensitive
    @Trivial
    public static String createStateCookieValue(String clientSecret, String state) {
        String timestamp = state.substring(0, Utils.TIMESTAMP_LENGTH);
        String newValue = state + clientSecret; // state already has a timestamp in it
        String value = HashUtils.digest(newValue);
        return timestamp + value;
    }

    @Sensitive
    @Trivial
    public static String getCookieName(String prefix, String configId, String state) {
        String newValue = state + configId;
        String newName = Utils.getStrHashCode(newValue);
        return prefix + newName;
    }

//    @Trivial
//    public static void createNonceCookie(HttpServletRequest request, HttpServletResponse response, String nonceValue, String state, ConvergedClientConfig clientConfig) {
//        String cookieName = getCookieName(ClientConstants.WAS_OIDC_NONCE, clientConfig, state);
//        String cookieValue = createNonceCookieValue(nonceValue, state, clientConfig);
//        Cookie cookie = OidcClientUtil.createCookie(cookieName, cookieValue, request);
//        response.addCookie(cookie);
//    }
//
//    public static String createNonceCookieValue(String nonceValue, String state, String clientSecret) {
//        return HashUtils.digest(nonceValue + state + clientSecret);
//    }

}
