/*
 * Copyright 2015 Acme Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acme.ecards.api.email.impl;

import com.acme.ecards.api.config.AppConfig;
import com.acme.ecards.api.email.EmailException;
import com.acme.ecards.api.email.EmailMessage;
import com.acme.ecards.api.email.support.MessageFactory;
import com.acme.ecards.api.email.support.SessionService;
import com.acme.ecards.api.template.EmailTemplate;
import com.acme.ecards.api.template.support.TemplateService;
import com.google.common.net.MediaType;
import java.io.IOException;
import java.util.Map;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.jvnet.hk2.annotations.Service;

/**
 * Delegate class that does the heavy lifting of sending out email.
 *
 * @author Sharmarke Aden (saden1)
 */
@Service
public class EmailSender {

    private final Map<String, Object> config;
    private final MessageFactory messageFactory;
    private final SessionService sessionService;
    private final TemplateService templateFactory;

    @Inject
    EmailSender(
            @AppConfig Map<String, Object> config,
            SessionService sessionService,
            TemplateService templateFactory,
            MessageFactory messageFactory) {
        this.config = config;
        this.messageFactory = messageFactory;
        this.sessionService = sessionService;
        this.templateFactory = templateFactory;
    }

    /**
     * Send an email message using the given email message and session.
     *
     * @param emailMessage email message context object
     * @param session java mail session object used to send the message.
     */
    public void send(EmailMessage emailMessage, Session session) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Map<String, String>> emailServices = (Map) config.get("email-services");
            Map<String, String> fromConfig = emailServices.get("from");
            Map<String, String> replyConfig = emailServices.get("reply");

            InternetAddress from = messageFactory.newInternetAddress(fromConfig.get("email"),
                    fromConfig.get("personal"));

            InternetAddress reply = messageFactory.newInternetAddress(replyConfig.get("email"),
                    replyConfig.get("personal"));

            InternetAddress to = messageFactory.newInternetAddress(emailMessage.getEmail(),
                    emailMessage.getPersonal());

            EmailTemplate emailTemplate = templateFactory.load(emailMessage.getTemplateId());

            String subject = templateFactory.interpolate(emailTemplate.getSubject(), emailMessage);
            String plainContent = templateFactory.interpolate(emailTemplate.getPlain(), emailMessage);
            String htmlContent = templateFactory.interpolate(emailTemplate.getHtml(), emailMessage);

            MimeBodyPart plainPart = messageFactory.newBodyPart(plainContent, MediaType.PLAIN_TEXT_UTF_8);
            MimeBodyPart htmlPart = messageFactory.newBodyPart(htmlContent, MediaType.HTML_UTF_8);

            MimeMultipart content = messageFactory.newMultipart(plainPart, htmlPart);
            MimeMessage mimeMessage = messageFactory.newMessage(from, reply, to, subject, content);

            sessionService.send(session, mimeMessage);
        }
        catch (MessagingException e) {
            throw new EmailException("could not send email", e);
        }
        catch (IOException e) {
            throw new EmailException("could not load email template", e);
        }
    }

}
