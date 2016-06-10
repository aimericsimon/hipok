package com.happy.hipok.web.rest.app.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * AppExtendedUserDTO à la création de l'ExtendedUser
 */
public class RegisterAppExtendedUserDTO extends AppExtendedUserDTO {

    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 100;

    //@Null(message="{com.happy.hipok.user.nullid}")
    //private Long id;

    @Size(min = 5, max = 100)
    private String login;

    @NotNull(message="{com.happy.hipok.password.nullid}")
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @NotNull(message="{com.happy.hipok.email.nullid}")
    @Email
    @Size(min = 5, max = 100)
    private String email;

    private boolean activated = false;

    private Set<String> authorities;

    public RegisterAppExtendedUserDTO(){

    }

    /**
     * Constructeur pour test unitaire
     * @param login
     * @param password
     * @param firstName
     * @param lastName
     * @param email
     * @param activated
     * @param langKey
     * @param authorities
     */
    public RegisterAppExtendedUserDTO(String login, String password, String firstName, String lastName, String email, boolean activated, String langKey, Set<String> authorities) {
        this.login = login; this.password = password; this.firstName = firstName; this.lastName = lastName; this.email = email; this.activated = activated; this.langKey = langKey; this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegisterAppExtendedUserDTO registerAppExtendedUserDTO = (RegisterAppExtendedUserDTO) o;

        if ( ! Objects.equals(this.getId(), registerAppExtendedUserDTO.getId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }*/
}
