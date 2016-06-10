package com.happy.hipok.service;

import com.happy.hipok.domain.Profile;
import com.happy.hipok.domain.Publication;
import com.happy.hipok.event.ReportEvent;
import com.happy.hipok.repository.PublicationRepository;
import com.happy.hipok.repository.ReportingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class ReportingService implements ApplicationEventPublisherAware {

    private final Logger log = LoggerFactory.getLogger(ReportingService.class);

    @Inject
    private MailService mailService;

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private ReportingRepository reportingRepository;

    @Inject
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Send mails to concerned people. Remove publication and all its dependencies if the publication has been reported more than 3 times.
     *
     * @param baseUrl url for mails
     * @param publication publication
     * @param reporter current profile
     * @return Has the publication and all its dependencies been removed?
     */
    public boolean handleReporting(String baseUrl, Publication publication, Profile reporter) {
        // Envoi d' un mail de confirmation de signalement à celui qui a signalé
        mailService.sendReporterProfileMail(reporter.getExtendedUser(), publication, baseUrl);
        // Envoi d'un mail vers l'auteur pour l'avertir
        mailService.sendReportedProfileMail(publication.getAuthorProfile().getExtendedUser(),publication, baseUrl);

        publisher.publishEvent(new ReportEvent(this, publication, reporter));

        // Si 3ème signalement sur la publi., supprimer la publication et envoi d'un flag au retour du POST + envoi d'un mail d'avertissement à l'auteur
        Long nbReportings = reportingRepository.countByReportedPublicationId(publication.getId());
        boolean removedPublication = false;
        if (nbReportings >= 3) {
            log.debug("Remove publication : {}", publication);
            publicationRepository.delete(publication.getId());
            removedPublication = true;
            mailService.sendRemovedPublicationMail(publication.getAuthorProfile().getExtendedUser(), publication,baseUrl);
        }
        return removedPublication;
    }

}
