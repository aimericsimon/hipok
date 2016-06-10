package com.happy.hipok.web.rest.util;

import org.springframework.http.HttpHeaders;

/**
 * Utility class for http header creation.
 *
 */
public class HeaderUtil {

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-hipokApp-alert", message);
        headers.add("X-hipokApp-params", param);
        return headers;
    }

    public static HttpHeaders createFailureAlert(String imageAuth, String idexists, String entityName){
        return createAlert("A new " + entityName + " cannot already have an ID", "");
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("A new " + entityName + " is created with identifier " + param, param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("A " + entityName + " is updated with identifier " + param, param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("A " + entityName + " is deleted with identifier " + param, param);
    }

    public static HttpHeaders createPublicationDeletionAlert(boolean removedPublication) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-hipokApp-publicationRemoved", String.valueOf(removedPublication));
        return headers;
    }
}
