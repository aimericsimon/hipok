package com.happy.hipok.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "avatar_thumbnail_url")
    private String avatarThumbnailUrl;

    @OneToMany(mappedBy = "follower",cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Follow> followers = new HashSet<>();

    @OneToMany(mappedBy = "following",cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Follow> followings = new HashSet<>();

    @OneToMany(mappedBy = "authorProfile")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Publication> publications = new HashSet<>();

    @OneToMany(mappedBy = "commentatorProfile",cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "reporterProfile",cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reporting> reportings = new HashSet<>();

    @OneToMany(mappedBy = "sharerProfile",cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Share> shares = new HashSet<>();

    @OneToOne
    private ExtendedUser extendedUser;

    @OneToMany(mappedBy = "receiverProfile",cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notification> receiverNotifications = new HashSet<>();

    @OneToMany(mappedBy = "emitterProfile",cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notification> emitterNotifications = new HashSet<>();

    @OneToMany(mappedBy = "deviceProfile",cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Device> devices = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarThumbnailUrl() {
        return avatarThumbnailUrl;
    }

    public void setAvatarThumbnailUrl(String avatarThumbnailUrl) {
        this.avatarThumbnailUrl = avatarThumbnailUrl;
    }

    public Set<Follow> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Follow> follows) {
        this.followers = follows;
    }

    public Set<Follow> getFollowings() {
        return followings;
    }

    public void setFollowings(Set<Follow> follows) {
        this.followings = follows;
    }

    public Set<Publication> getPublications() {
        return publications;
    }

    public void setPublications(Set<Publication> publications) {
        this.publications = publications;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Reporting> getReportings() {
        return reportings;
    }

    public void setReportings(Set<Reporting> reportings) {
        this.reportings = reportings;
    }

    public Set<Share> getShares() {
        return shares;
    }

    public void setShares(Set<Share> shares) {
        this.shares = shares;
    }

    public ExtendedUser getExtendedUser() {
        return extendedUser;
    }

    public void setExtendedUser(ExtendedUser extendedUser) {
        this.extendedUser = extendedUser;
    }

    public Set<Notification> getReceiverNotifications() {
        return receiverNotifications;
    }

    public void setReceiverNotifications(Set<Notification> notifications) {
        this.receiverNotifications = notifications;
    }

    public Set<Notification> getEmitterNotifications() {
        return emitterNotifications;
    }

    public void setEmitterNotifications(Set<Notification> notifications) {
        this.emitterNotifications = notifications;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public User getUser() {
        ExtendedUser extendedUser = this.getExtendedUser();
        if (extendedUser == null) {
            return null;
        }
        User user = extendedUser.getUser();
        if (user == null) {
            return null;
        }
        return user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id);
    }



    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + id +
            ", avatarUrl='" + avatarUrl + "'" +
            ", description='" + description + "'" +
            ", avatarThumbnailUrl='" + avatarThumbnailUrl + "'" +
            '}';
    }
}
