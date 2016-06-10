package com.happy.hipok.web.rest.app.dto;


/**
 * Item appearing in the search screen.
 */
public class AppSearchItemDTO {

    private Long publicationId;
    private String imageThumbnailUrl;


    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
    }

    @Override
    public String toString() {
        return "AppSearchItemDTO{" +
            "publicationId=" + publicationId +
            ", imageThumbnailUrl='" + imageThumbnailUrl + '\'' +
            '}';
    }
}
