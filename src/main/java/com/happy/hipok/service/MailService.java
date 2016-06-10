package com.happy.hipok.service;

import com.happy.hipok.Application;
import com.happy.hipok.config.JHipsterProperties;
import com.happy.hipok.domain.*;
import com.happy.hipok.repository.DeclarationEmailRepository;
import com.happy.hipok.repository.ExtendedUserRepository;
import com.happy.hipok.web.rest.mapper.ImageMapper;
import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;

/**
 * Service for sending e-mails.
 * <p/>
 * <p>
 * We use the @Async annotation to send e-mails asynchronously.
 * </p>
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Inject
    private ImageMapper imageMapper;

    @Inject
    private JHipsterProperties jHipsterProperties;

    @Inject
    private JavaMailSenderImpl javaMailSender;

    @Inject
    private MessageSource messageSource;

    @Inject
    private SpringTemplateEngine templateEngine;

    @Inject
    private ExtendedUserRepository extendedUserRepository;

    @Inject
    private DeclarationEmailRepository declarationEmailRepository;

    /**
     * System default email address that sends the e-mails.
     */
    private String from;

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            if(javaMailSender.getJavaMailProperties() == null){
            	javaMailSender.setJavaMailProperties(new Properties());
            }
            javaMailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
            javaMailSender.send(mimeMessage);
            log.debug("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }

    @Async
    public void sendActivationEmail(ExtendedUser extendedUser, String baseUrl) {
        log.debug("Sending activation e-mail to '{}'", extendedUser.getUser().getEmail());
        sendExtendedUserMail(extendedUser, baseUrl, "activationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user, String baseUrl) {
        log.debug("Sending password reset e-mail to '{}'", user.getEmail());

        Optional<ExtendedUser> extendedUser = extendedUserRepository.findOneByUserId(user.getId());
        if(extendedUser.isPresent()){
            sendExtendedUserMail(extendedUser.get(), baseUrl, "passwordResetEmail", "email.reset.title");
        }
    }

    @Async
    public void sendReporterProfileMail(ExtendedUser extendedUser, Publication publication, String baseUrl) {
        log.debug("Sending reporting e-mail to reporter '{}'", extendedUser.getUser().getEmail());
        sendReportMail(extendedUser, publication, baseUrl, "reporterEmail", "email.reporter.title");
    }

    @Async
    public void sendReportedProfileMail(ExtendedUser extendedUser, Publication publication, String baseUrl) {
        log.debug("Sending reporting e-mail to reported '{}'", extendedUser.getUser().getEmail());
        sendReportMail(extendedUser, publication, baseUrl, "reportedEmail", "email.reported.title");
    }

    @Async
    public void sendRemovedPublicationMail(ExtendedUser extendedUser, Publication publication, String baseUrl) {
        log.debug("Sending removed publication to reported '{}'", extendedUser.getUser().getEmail());
        sendReportMail(extendedUser, publication, baseUrl, "removedPublicationEmail", "email.removed.publication.title");
    }

    /**
     *
     * @param extendedUser
     * @param publication
     * @param baseUrl
     * @param mailTemplate
     * @param messageTitle
     */
    @Async
    private void sendReportMail(ExtendedUser extendedUser, Publication publication, String baseUrl, String mailTemplate, String messageTitle) {
        Locale locale = Locale.forLanguageTag(extendedUser.getUser().getLangKey());
        Context context = new Context(locale);
        context.setVariable("extendedUser", extendedUser);
        context.setVariable("baseUrl", baseUrl);
        context.setVariable("publicationImageUrl", imageMapper.getFullUrl(baseUrl+"/" + Application.UPLOAD_DIRECTORY,publication.getImage().getImageUrl()));
        context.setVariable("publicationText", publication.getDescription());
        String content = templateEngine.process(mailTemplate, context);
        String subject = messageSource.getMessage(messageTitle, null, locale);
        sendEmail(extendedUser.getUser().getEmail(), subject, content, false, true);
    }

    @Async
    private void sendExtendedUserMail(ExtendedUser extendedUser, String baseUrl, String mailTemplate, String messageTitle) {
        Locale locale = Locale.forLanguageTag(extendedUser.getUser().getLangKey());
        Context context = new Context(locale);
        context.setVariable("extendedUser", extendedUser);
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process(mailTemplate, context);
        String subject = messageSource.getMessage(messageTitle, null, locale);
        sendEmail(extendedUser.getUser().getEmail(), subject, content, false, true);
    }

    @Async
    public void sendDeclaration(Declaration declaration, DeclarationEmail email, String baseUrl)
    {
        Locale locale = Locale.forLanguageTag(declaration.getProfile().getExtendedUser().getUser().getLangKey());
        Context context = new Context(locale);
        context.setVariable("declaration", declaration);
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("declarationEmail", context);
        String subject = messageSource.getMessage("email.declaration.title", null, locale);
        sendEmail(email.getEmail(), subject, content, false, true);
    }
}
