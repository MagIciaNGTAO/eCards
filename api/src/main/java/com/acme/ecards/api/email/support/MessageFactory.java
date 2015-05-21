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
package com.acme.ecards.api.email.support;

import com.google.common.net.MediaType;
import java.io.UnsupportedEncodingException;
import static java.nio.charset.StandardCharsets.UTF_8;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.jvnet.hk2.annotations.Service;

/**
 * Provides java mail message related factory methods.
 *
 * @author Sharmarke Aden (saden1)
 */
@Service
public class MessageFactory {

    private final Provider<InternetAddress> internetAddressProvider;
    private final Provider<MimeMessage> messageProvider;
    private final Provider<MimeBodyPart> bodyPartProvider;
    private final Provider<MimeMultipart> multipartProvider;

    @Inject
    MessageFactory(
            Provider<InternetAddress> internetAddressProvider,
            Provider<MimeMessage> messageProvider,
            Provider<MimeBodyPart> bodyPartProvider,
            Provider<MimeMultipart> multipartProvider) {
        this.internetAddressProvider = internetAddressProvider;
        this.messageProvider = messageProvider;
        this.bodyPartProvider = bodyPartProvider;
        this.multipartProvider = multipartProvider;
    }

    /**
     * Crate a new InternetAddress instance.
     *
     * @param email
     * @param personal
     * @return
     * @throws UnsupportedEncodingException
     */
    public InternetAddress newInternetAddress(String email, String personal)
            throws UnsupportedEncodingException {
        InternetAddress address = internetAddressProvider.get();
        address.setAddress(email);
        address.setPersonal(personal, UTF_8.name());

        return address;
    }

    /**
     * Crate a new MimeMessage instance.
     *
     * @param from
     * @param reply
     * @param to
     * @param subject
     * @param content
     * @return
     * @throws MessagingException
     */
    public MimeMessage newMessage(InternetAddress from,
            InternetAddress reply,
            InternetAddress to,
            String subject,
            Multipart content) throws MessagingException {
        MimeMessage mimeMessage = messageProvider.get();
        mimeMessage.setFrom(from);
        mimeMessage.setReplyTo(new InternetAddress[]{reply});
        mimeMessage.setRecipient(Message.RecipientType.TO, to);
        mimeMessage.setSubject(subject);
        mimeMessage.setContent(content);
        mimeMessage.saveChanges();

        return mimeMessage;
    }

    /**
     * Crate a new MimeMessage instance.
     *
     * @param bodyParts
     * @return
     * @throws MessagingException
     */
    public MimeMultipart newMultipart(BodyPart... bodyParts) throws MessagingException {
        MimeMultipart multiPart = multipartProvider.get();

        for (BodyPart bodyPart : bodyParts) {
            multiPart.addBodyPart(bodyPart);
        }

        return multiPart;
    }

    /**
     * Crate a new MimeBodyPart instance.
     *
     * @param content
     * @param type
     * @return
     * @throws MessagingException
     */
    public MimeBodyPart newBodyPart(String content, MediaType type) throws MessagingException {
        MimeBodyPart bodyPart = bodyPartProvider.get();
        bodyPart.setContent(content, type.toString());

        return bodyPart;
    }

}
