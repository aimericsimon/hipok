package com.happy.hipok.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Reporting.
 */
@Entity
@Table(name = "reporting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reporting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "reporting_date")
    private ZonedDateTime reportingDate;

    @ManyToOne
    @JoinColumn(name = "reporter_profile_id")
    private Profile reporterProfile;

    @ManyToOne
    @JoinColumn(name = "reported_publication_id")
    private Publication reportedPublication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(ZonedDateTime reportingDate) {
        this.reportingDate = reportingDate;
    }

    public Profile getReporterProfile() {
        return reporterProfile;
    }

    public void setReporterProfile(Profile profile) {
        this.reporterProfile = profile;
    }

    public Publication getReportedPublication() {
        return reportedPublication;
    }

    public void setReportedPublication(Publication publication) {
        this.reportedPublication = publication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reporting reporting = (Reporting) o;
        return Objects.equals(id, reporting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reporting{" +
            "id=" + id +
            ", reportingDate='" + reportingDate + "'" +
            '}';
    }
}
