package com.happy.hipok.domain;

import com.happy.hipok.domain.enumeration.NotificationType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "read")
    private Boolean read;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private NotificationType type;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "data")
    private String data;

    @ManyToOne
    @JoinColumn(name = "receiver_profile_id")
    private Profile receiverProfile;

    @ManyToOne
    @JoinColumn(name = "emitter_profile_id")
    private Profile emitterProfile;

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

    public Profile getReceiverProfile() {
        return receiverProfile;
    }

    public void setReceiverProfile(Profile profile) {
        this.receiverProfile = profile;
    }

    public Profile getEmitterProfile() {
        return emitterProfile;
    }

    public void setEmitterProfile(Profile profile) {
        this.emitterProfile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Notification notification = (Notification) o;
        return Objects.equals(id, notification.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + id +
            ", creationDate='" + creationDate + "'" +
            ", read='" + read + "'" +
            ", type='" + type + "'" +
            ", itemId='" + itemId + "'" +
            ", data='" + data + "'" +
            '}';
    }
}
