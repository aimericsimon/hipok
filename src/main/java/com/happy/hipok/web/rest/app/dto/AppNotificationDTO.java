package com.happy.hipok.web.rest.app.dto;

import com.happy.hipok.domain.enumeration.NotificationType;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the Notification entity.
 */
public class AppNotificationDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private Boolean read;

    private NotificationType type;

    private Long receiverProfileId;
    private Long emitterProfileId;

    private Long itemId;

    private String emitterTitleAbbreviation;

    private String emitterFirstName;

    private String emitterLastName;

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Long getReceiverProfileId() {
        return receiverProfileId;
    }

    public void setReceiverProfileId(Long profileId) {
        this.receiverProfileId = profileId;
    }
    public Long getEmitterProfileId() {
        return emitterProfileId;
    }

    public void setEmitterProfileId(Long profileId) {
        this.emitterProfileId = profileId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getEmitterTitleAbbreviation() {
        return emitterTitleAbbreviation;
    }

    public void setEmitterTitleAbbreviation(String emitterTitleAbbreviation) {
        this.emitterTitleAbbreviation = emitterTitleAbbreviation;
    }

    public String getEmitterFirstName() {
        return emitterFirstName;
    }

    public void setEmitterFirstName(String emitterFirstName) {
        this.emitterFirstName = emitterFirstName;
    }

    public String getEmitterLastName() {
        return emitterLastName;
    }

    public void setEmitterLastName(String emitterLastName) {
        this.emitterLastName = emitterLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppNotificationDTO notificationDTO = (AppNotificationDTO) o;

        if ( ! Objects.equals(id, notificationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AppNotificationDTO{" +
            "id=" + id +
            ", creationDate=" + creationDate +
            ", read=" + read +
            ", type=" + type +
            ", receiverProfileId=" + receiverProfileId +
            ", emitterProfileId=" + emitterProfileId +
            ", itemId=" + itemId +
            ", emitterTitleAbbreviation='" + emitterTitleAbbreviation + '\'' +
            ", emitterFirstName='" + emitterFirstName + '\'' +
            ", emitterLastName='" + emitterLastName + '\'' +
            '}';
    }
}
