package com.happy.hipok.web.rest.app.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by Dahwoud on 17/05/2016.
 */
public class AppDeviceDTO {

    @NotNull(message="{com.happy.hipok.token.nullid}")
    private String token;

    @NotNull(message="{com.happy.hipok.type.nullid}")
    private String type;

    private boolean enabled;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
