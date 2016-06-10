package com.happy.hipok.web.rest.app.dto;

/**
 * Created by Dahwoud on 31/03/2016.
 */
public class AppChangePasswordDTO {

    private String oldPassword;

    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
