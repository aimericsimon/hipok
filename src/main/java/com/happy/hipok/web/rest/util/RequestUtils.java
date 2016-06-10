package com.happy.hipok.web.rest.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gni on 01/12/15.
 */
public class RequestUtils {

    public static String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() +                // "http"
            "://" +                                // "://"
            request.getServerName() +              // "myhost"
            ":" +                                  // ":"
            request.getServerPort() +              // "80"
            request.getContextPath();
    }
}
