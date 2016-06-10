package com.happy.hipok.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.happy.hipok.domain.enumeration.Visibility;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Publication.
 */
@Entity
@Table(name = "publication")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Publication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @Size(max = 200)
    @Column(name = "location", length = 200)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    private Visibility visibility;

    @Column(name = "publication_date")
    private ZonedDateTime publicationDate;

    @Column(name = "nb_vizualisations")
    private Integer nbVizualisations;

    @Size(max = 3000)
    @Column(name = "processed_description", length = 3000)
    private String processedDescription;

    @ManyToOne
    @JoinColumn(name = "author_profile_id")
    private Profile authorProfile;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "reportedPublication", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reporting> reportings = new HashSet<>();

    @OneToMany(mappedBy = "publication", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Share> shares = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "publication_hashtag",
        joinColumns = @JoinColumn(name="publications_id", referencedColumnName="ID"),
        inverseJoinColumns = @JoinColumn(name="hashtags_id", referencedColumnName="ID"))
    private Set<Hashtag> hashtags = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "anatomic_zone_ref_id")
    private AnatomicZoneRef anatomicZoneRef;

    @ManyToOne
    @JoinColumn(name = "specialty_ref_id")
    private SpecialtyRef specialtyRef;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

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

    public Profile getAuthorProfile() {
        return authorProfile;
    }

    public void setAuthorProfile(Profile profile) {
        this.authorProfile = profile;
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

    public Set<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(Set<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public AnatomicZoneRef getAnatomicZoneRef() {
        return anatomicZoneRef;
    }

    public void setAnatomicZoneRef(AnatomicZoneRef anatomicZoneRef) {
        this.anatomicZoneRef = anatomicZoneRef;
    }

    public SpecialtyRef getSpecialtyRef() {
        return specialtyRef;
    }

    public void setSpecialtyRef(SpecialtyRef specialtyRef) {
        this.specialtyRef = specialtyRef;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Publication publication = (Publication) o;
        return Objects.equals(id, publication.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Publication{" +
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
