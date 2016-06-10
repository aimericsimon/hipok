package com.happy.hipok.web.rest.app.dto;

import com.happy.hipok.domain.enumeration.Visibility;
import com.happy.hipok.validation.annotations.ValidAnatomicZoneRefId;
import com.happy.hipok.validation.annotations.ValidImageId;
import com.happy.hipok.validation.annotations.ValidSpecialtyRefId;
import com.happy.hipok.web.rest.dto.HashtagDTO;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Publication entity.
 */
public class AppPublicationDTO implements Serializable {

    @Null(message="{com.happy.hipok.publication.nullid}")
    private Long id;

    @Size(max = 1000)
    private String description;

    private String processedDescription;

    @Size(max = 200)
    private String location;

    private Visibility visibility;

    private ZonedDateTime publicationDate;

    private Integer nbVizualisations;

    private String imageBinaryContentType;

    private Long authorProfileId;

    @ValidAnatomicZoneRefId
    private Long anatomicZoneRefId;

    @ValidSpecialtyRefId
    private Long specialtyRefId;

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    private String imageUrl;

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

    private String imageThumbnailUrl;

    @ValidImageId
    private Long imageId;

    private Set<HashtagDTO> hashtags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public ZonedDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(ZonedDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getNbVizualisations() {
        return nbVizualisations;
    }

    public void setNbVizualisations(Integer nbVizualisations) {
        this.nbVizualisations = nbVizualisations;
    }

    public String getImageBinaryContentType() {
        return imageBinaryContentType;
    }

    public void setImageBinaryContentType(String imageBinaryContentType) {
        this.imageBinaryContentType = imageBinaryContentType;
    }

    public Long getAuthorProfileId() {
        return authorProfileId;
    }

    public void setAuthorProfileId(Long profileId) {
        this.authorProfileId = profileId;
    }

    public Long getAnatomicZoneRefId() {
        return anatomicZoneRefId;
    }

    public void setAnatomicZoneRefId(Long anatomicZoneRefId) {
        this.anatomicZoneRefId = anatomicZoneRefId;
    }

    public Long getSpecialtyRefId() {
        return specialtyRefId;
    }

    public void setSpecialtyRefId(Long specialtyRefId) {
        this.specialtyRefId = specialtyRefId;
    }

    public Set<HashtagDTO> getHashtags() {
        return hashtags;
    }

    public void setHashtags(Set<HashtagDTO> hashtags) {
        this.hashtags = hashtags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppPublicationDTO appPublicationDTO = (AppPublicationDTO) o;

        if ( ! Objects.equals(id, appPublicationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AppPublicationDTO{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", processedDescription='" + processedDescription + '\'' +
            ", location='" + location + '\'' +
            ", visibility=" + visibility +
            ", publicationDate=" + publicationDate +
            ", nbVizualisations=" + nbVizualisations +
            ", imageBinaryContentType='" + imageBinaryContentType + '\'' +
            ", authorProfileId=" + authorProfileId +
            ", anatomicZoneRefId=" + anatomicZoneRefId +
            ", specialtyRefId=" + specialtyRefId +
            ", hashtags=" + hashtags +
            '}';
    }
}
