package com.happy.hipok.web.rest.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.happy.hipok.domain.enumeration.Visibility;

/**
 * A DTO for the Publication entity.
 */
public class PublicationDTO implements Serializable {

    private Long id;

    @Size(max = 1000)
    private String description;


    @Size(max = 200)
    private String location;


    private Visibility visibility;


    private ZonedDateTime publicationDate;


    private Integer nbVizualisations;


    @Size(max = 3000)
    private String processedDescription;


    private Long authorProfileId;

    private String authorProfileDescription;

    private Set<HashtagDTO> hashtags = new HashSet<>();

    private Long anatomicZoneRefId;

    private String anatomicZoneRefLabel;

    private Long specialtyRefId;

    private String specialtyRefLabel;

    private Long imageId;
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
    public String getProcessedDescription() {
        return processedDescription;
    }

    public void setProcessedDescription(String processedDescription) {
        this.processedDescription = processedDescription;
    }

    public Long getAuthorProfileId() {
        return authorProfileId;
    }

    public void setAuthorProfileId(Long profileId) {
        this.authorProfileId = profileId;
    }

    public String getAuthorProfileDescription() {
        return authorProfileDescription;
    }

    public void setAuthorProfileDescription(String profileDescription) {
        this.authorProfileDescription = profileDescription;
    }

    public Set<HashtagDTO> getHashtags() {
        return hashtags;
    }

    public void setHashtags(Set<HashtagDTO> hashtags) {
        this.hashtags = hashtags;
    }

    public Long getAnatomicZoneRefId() {
        return anatomicZoneRefId;
    }

    public void setAnatomicZoneRefId(Long anatomicZoneRefId) {
        this.anatomicZoneRefId = anatomicZoneRefId;
    }

    public String getAnatomicZoneRefLabel() {
        return anatomicZoneRefLabel;
    }

    public void setAnatomicZoneRefLabel(String anatomicZoneRefLabel) {
        this.anatomicZoneRefLabel = anatomicZoneRefLabel;
    }

    public Long getSpecialtyRefId() {
        return specialtyRefId;
    }

    public void setSpecialtyRefId(Long specialtyRefId) {
        this.specialtyRefId = specialtyRefId;
    }

    public String getSpecialtyRefLabel() {
        return specialtyRefLabel;
    }

    public void setSpecialtyRefLabel(String specialtyRefLabel) {
        this.specialtyRefLabel = specialtyRefLabel;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PublicationDTO publicationDTO = (PublicationDTO) o;

        if ( ! Objects.equals(id, publicationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PublicationDTO{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", location='" + location + "'" +
            ", visibility='" + visibility + "'" +
            ", publicationDate='" + publicationDate + "'" +
            ", nbVizualisations='" + nbVizualisations + "'" +
            ", processedDescription='" + processedDescription + "'" +
            '}';
    }
}
