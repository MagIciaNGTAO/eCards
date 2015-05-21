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

import com.acme.ecards.api.email.EmailMessage;
import com.acme.ecards.api.email.support.MessageFactory;
import com.acme.ecards.api.email.support.SessionService;
import com.acme.ecards.api.template.EmailTemplate;
import com.acme.ecards.api.template.impl.DefaultEmailTemplate;
import com.acme.ecards.api.template.support.TemplateService;
import static com.google.common.collect.ImmutableMap.of;
import com.google.common.net.MediaType;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
public class EmailSenderNGTest {

    EmailSender sut;

    Map<String, Object> appConfig;
    TemplateService templateFactory;
    MessageFactory messageFactory;
    SessionService sessionService;

    @BeforeMethod
    @SuppressWarnings("unchecked")
    void init() {
        appConfig = mock(Map.class);
        templateFactory = mock(TemplateService.class);
        messageFactory = mock(MessageFactory.class);
        sessionService = mock(SessionService.class);

        sut = new EmailSender(appConfig, sessionService, templateFactory, messageFactory);

    }

    @DataProvider
    Object[][] invalidAppConfig() {
        return new Object[][]{
            {null},
            {of()},
            {of("email-services", of())},
            {of("email-services", of("from", of("email", "test@email.com")))},
            {of("email-services", of("from", of("personal", "test")))},
            {of("email-services", of("reply", of("email", "test@email.com")))},
            {of("email-services", of("reply", of("personal", "test")))},};
    }

    @Test(dataProvider = "invalidAppConfig", expectedExceptions = NullPointerException.class)
    public void givenInvalidAppConfigSendShouldThrowException(Map<String, Object> appConfig) {
        sut = new EmailSender(appConfig, sessionService, templateFactory, messageFactory);
        Session session = Session.getDefaultInstance(new Properties());
        EmailMessage emailMessage = mock(EmailMessage.class);

        sut.send(emailMessage, session);
    }

    @Test
    public void givenValidStateAndParametersSendReturn()
            throws UnsupportedEncodingException, MessagingException, IOException {
        String email = "to@test.com";
        String personal = "to";
        String fromEmail = "from@test.com";
        String fromPersonal = "test";
        String replyEmail = "reply@test.com";
        String replyPersonal = "reply";
        String subject = "test";
        String plainContent = "plain";
        String htmlContent = "html";
        InternetAddress fromAddress = mock(InternetAddress.class);
        InternetAddress replyAddress = mock(InternetAddress.class);
        InternetAddress toAddress = mock(InternetAddress.class);

        EmailMessage emailMessage = spy(new EmailMessage(email, personal, "test", of()));
        Session session = Session.getInstance(new Properties());
        EmailTemplate emailTemplate = new DefaultEmailTemplate(subject, plainContent, htmlContent);
        MimeBodyPart plainPart = mock(MimeBodyPart.class);
        MimeBodyPart htmlPart = mock(MimeBodyPart.class);
        MimeMultipart content = mock(MimeMultipart.class);
        MimeMessage message = mock(MimeMessage.class);

        Map<String, String> fromConfig = spy(new HashMap<String, String>() {
            {
                put("email", fromEmail);
                put("personal", fromPersonal);
            }
        });

        Map<String, String> replyConfig = spy(new HashMap<String, String>() {
            {
                put("email", replyEmail);
                put("personal", replyPersonal);
            }
        });

        Map<String, Map<String, String>> emailServices = spy(new HashMap<String, Map<String, String>>() {
            {
                put("from", fromConfig);
                put("reply", replyConfig);
            }
        });

        Map<String, Object> appConfig = spy(new HashMap<String, Object>() {
            {
                put("email-services", emailServices);
            }
        });

        given(messageFactory.newInternetAddress(fromEmail, fromPersonal)).willReturn(fromAddress);
        given(messageFactory.newInternetAddress(replyEmail, replyPersonal)).willReturn(replyAddress);
        given(messageFactory.newInternetAddress(email, personal)).willReturn(toAddress);
        given(templateFactory.load(emailMessage.getTemplateId())).willReturn(emailTemplate);
        given(templateFactory.interpolate(emailTemplate.getSubject(), emailMessage)).willReturn(subject);
        given(templateFactory.interpolate(emailTemplate.getPlain(), emailMessage)).willReturn(plainContent);
        given(templateFactory.interpolate(emailTemplate.getHtml(), emailMessage)).willReturn(htmlContent);
        given(messageFactory.newBodyPart(plainContent, MediaType.PLAIN_TEXT_UTF_8)).willReturn(plainPart);
        given(messageFactory.newBodyPart(htmlContent, MediaType.HTML_UTF_8)).willReturn(htmlPart);
        given(messageFactory.newMultipart(plainPart, htmlPart)).willReturn(content);
        given(messageFactory.newMessage(fromAddress, replyAddress, toAddress, subject, content)).willReturn(message);

        sut = new EmailSender(appConfig, sessionService, templateFactory, messageFactory);

        sut.send(emailMessage, session);

        verify(appConfig).get("email-services");
        verify(emailServices).get("from");
        verify(emailServices).get("reply");
        verify(fromConfig).get("email");
        verify(fromConfig).get("personal");
        verify(replyConfig).get("email");
        verify(replyConfig).get("personal");
        verify(messageFactory).newInternetAddress(fromEmail, fromPersonal);
        verify(messageFactory).newInternetAddress(replyEmail, replyPersonal);
        verify(messageFactory).newInternetAddress(email, personal);

        verify(templateFactory).load(emailMessage.getTemplateId());
        verify(templateFactory).interpolate(emailTemplate.getSubject(), emailMessage);
        verify(templateFactory).interpolate(emailTemplate.getPlain(), emailMessage);
        verify(templateFactory).interpolate(emailTemplate.getHtml(), emailMessage);

        verify(messageFactory).newBodyPart(plainContent, MediaType.PLAIN_TEXT_UTF_8);
        verify(messageFactory).newBodyPart(htmlContent, MediaType.HTML_UTF_8);
        verify(messageFactory).newMultipart(plainPart, htmlPart);
        verify(messageFactory).newMessage(fromAddress, replyAddress, toAddress, subject, content);
        verify(sessionService).send(session, message);
    }

}
