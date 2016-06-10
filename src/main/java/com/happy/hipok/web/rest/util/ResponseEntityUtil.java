package com.happy.hipok.web.rest.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Dahwoud on 31/03/2016.
 */
public class ResponseEntityUtil {

    /**
     *
     * @param message
     * @param statusCode
     * @return
     */
    public static ResponseEntity<String> getResponse(String message, HttpStatus statusCode) {
        String json = "\"" + message + "\"";

        return new ResponseEntity<>(json, statusCode);
    }
}
