package com.happy.hipok.web.rest.app.dto;

import com.happy.hipok.validation.annotations.AuthorizedPublicationId;
import com.happy.hipok.validation.annotations.ValidPublicationId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for comments
 */
public class AppCommentDTO implements Serializable{

    @Null(message="{com.happy.hipok.publication.nullid}")
    private Long id;

    @NotNull(message="{com.happy.hipok.notnullpublicationid}")
    @ValidPublicationId
    @AuthorizedPublicationId
    private Long publicationId;

    @NotNull(message="{com.happy.hipok.text.nullid}")
    @Size(min = 0, max = 1000)
    private String text;

    private String processedText;

    private Long profileId;
    private String titleAbbreviation;
    private String firstName;
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getProcessedText() {
        return processedText;
    }

    public void setProcessedText(String processedText) {
        this.processedText = processedText;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getTitleAbbreviation() {
        return titleAbbreviation;
    }

    public void setTitleAbbreviation(String titleAbbreviation) {
        this.titleAbbreviation = titleAbbreviation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "AppCommentDTO{" +
            "id=" + id +
            ", publicationId=" + publicationId +
            ", text='" + text + '\'' +
            ", processedText='" + processedText + '\'' +
            ", profileId=" + profileId +
            ", titleAbbreviation='" + titleAbbreviation + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
    }
}
