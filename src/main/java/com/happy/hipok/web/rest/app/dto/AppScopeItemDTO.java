package com.happy.hipok.web.rest.app.dto;


import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

public class AppScopeItemDTO {

	private Long publicationId;
    private Long authorProfileId;
    private String avatarThumbnailUrl;
    private String titleAbbreviation;
    private String firstName;
    private String lastName;
    private String practiceLocation;
    private String medicalTypeRefLabel;
    private ZonedDateTime publicationDate;
    private String imageUrl;
    private String imageThumbnailUrl;
    private Integer nbVizualisations;
    private long nbShares;
    private long nbComments;
    @Size(max = 1000)
    private String description;
    @Size(max = 3000)
    private String processedDescription;

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public Long getAuthorProfileId() {
        return authorProfileId;
    }

    public void setAuthorProfileId(Long authorProfileId) {
        this.authorProfileId = authorProfileId;
    }

    public String getAvatarThumbnailUrl() {
        return avatarThumbnailUrl;
    }

    public void setAvatarThumbnailUrl(String avatarThumbnailUrl) {
        this.avatarThumbnailUrl = avatarThumbnailUrl;
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

    public ZonedDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(ZonedDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
    }

    public Integer getNbVizualisations() {
        return nbVizualisations;
    }

    public void setNbVizualisations(Integer nbVizualisations) {
        this.nbVizualisations = nbVizualisations;
    }

    public long getNbShares() {
        return nbShares;
    }

    public void setNbShares(long nbShares) {
        this.nbShares = nbShares;
    }

    public long getNbComments() {
        return nbComments;
    }

    public void setNbComments(long nbComments) {
        this.nbComments = nbComments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProcessedDescription() {
        return processedDescription;
    }

    public void setProcessedDescription(String processedDescription) {
        this.processedDescription = processedDescription;
    }

    public String getPracticeLocation() {
        return practiceLocation;
    }

    public void setPracticeLocation(String practiceLocation) {
        this.practiceLocation = practiceLocation;
    }

    public String getMedicalTypeRefLabel() {
        return medicalTypeRefLabel;
    }

    public void setMedicalTypeRefLabel(String medicalTypeRefLabel) {
        this.medicalTypeRefLabel = medicalTypeRefLabel;
    }

    @Override
    public String toString() {
        return "AppScopeItemDTO{" +
            "publicationId=" + publicationId +
            ", authorProfileId=" + authorProfileId +
            ", avatarThumbnailUrl='" + avatarThumbnailUrl + '\'' +
            ", titleAbbreviation='" + titleAbbreviation + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", practiceLocation='" + practiceLocation + '\'' +
            ", medicalTypeRefLabel='" + medicalTypeRefLabel + '\'' +
            ", publicationDate=" + publicationDate +
            ", imageUrl='" + imageUrl + '\'' +
            ", imageThumbnailUrl='" + imageThumbnailUrl + '\'' +
            ", nbVizualisations=" + nbVizualisations +
            ", nbShares=" + nbShares +
            ", nbComments=" + nbComments +
            ", description='" + description + '\'' +
            ", processedDescription='" + processedDescription + '\'' +
            '}';
    }
}
