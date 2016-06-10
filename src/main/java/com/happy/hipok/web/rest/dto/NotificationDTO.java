package com.happy.hipok.web.rest.dto;

import com.happy.hipok.domain.enumeration.NotificationType;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the Notification entity.
 */
public class NotificationDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private Boolean read;

    private NotificationType type;

    private Long itemId;

    private String data;

    private Long receiverProfileId;
    private Long emitterProfileId;
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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotificationDTO notificationDTO = (NotificationDTO) o;

        if ( ! Objects.equals(id, notificationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
            "id=" + id +
            ", creationDate='" + creationDate + "'" +
            ", read='" + read + "'" +
            ", type='" + type + "'" +
            ", itemId='" + itemId + "'" +
            ", data='" + data + "'" +
            '}';
    }
}
