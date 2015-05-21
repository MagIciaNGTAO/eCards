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
import javax.inject.Provider;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
public class MessageFactoryNGTest {

    MessageFactory sut;
    Provider<InternetAddress> internetAddressProvider;
    Provider<MimeMessage> messageProvider;
    Provider<MimeBodyPart> bodyPartProvider;
    Provider<MimeMultipart> multipartProvider;

    @BeforeMethod
    @SuppressWarnings("unchecked")
    void init() {
        internetAddressProvider = mock(Provider.class);
        messageProvider = mock(Provider.class);
        bodyPartProvider = mock(Provider.class);
        multipartProvider = mock(Provider.class);

        sut = new MessageFactory(internetAddressProvider, messageProvider, bodyPartProvider, multipartProvider);
    }

    @Test
    public void givenEmailAndPersonalCallToNewInternetAddressShouldReturn()
            throws UnsupportedEncodingException {
        String email = "test@test.com";
        String personal = "test";
        InternetAddress address = mock(InternetAddress.class);
        given(internetAddressProvider.get()).willReturn(address);

        InternetAddress result = sut.newInternetAddress(email, personal);
        assertThat(result).isNotNull().isSameAs(address);

        verify(internetAddressProvider).get();
        verify(address).setAddress(email);
        verify(address).setPersonal(personal, UTF_8.name());
    }

    @Test
    public void givenValidParametersCallToNewMessageShouldReturn()
            throws UnsupportedEncodingException, MessagingException {
        InternetAddress from = mock(InternetAddress.class);
        InternetAddress to = mock(InternetAddress.class);
        InternetAddress reply = mock(InternetAddress.class);
        String subject = "subject";
        Multipart content = mock(Multipart.class);
        MimeMessage mimeMessage = mock(MimeMessage.class);

        given(messageProvider.get()).willReturn(mimeMessage);

        MimeMessage result = sut.newMessage(from, reply, to, subject, content);
        assertThat(result).isNotNull().isSameAs(mimeMessage);

        verify(messageProvider).get();
        verify(mimeMessage).setFrom(from);
        verify(mimeMessage).setReplyTo(any());
        verify(mimeMessage).setRecipient(Message.RecipientType.TO, to);
        verify(mimeMessage).setSubject(subject);
        verify(mimeMessage).saveChanges();
    }

    @Test
    public void givenBodyPartsCallToNewMultipartShouldReturn()
            throws UnsupportedEncodingException, MessagingException {
        BodyPart part = mock(BodyPart.class);
        MimeMultipart multiPart = mock(MimeMultipart.class);

        given(multipartProvider.get()).willReturn(multiPart);

        MimeMultipart result = sut.newMultipart(part);
        assertThat(result).isNotNull().isSameAs(multiPart);

        verify(multipartProvider).get();
        verify(multiPart, times(1)).addBodyPart(part);
    }

    @Test
    public void givenContentAndMediaTypeNewBodyPartShouldReturn()
            throws UnsupportedEncodingException, MessagingException {
        String content = "content";
        MediaType type = MediaType.PLAIN_TEXT_UTF_8;
        MimeBodyPart bodyPart = mock(MimeBodyPart.class);

        given(bodyPartProvider.get()).willReturn(bodyPart);

        MimeBodyPart result = sut.newBodyPart(content, type);
        assertThat(result).isNotNull().isSameAs(bodyPart);

        verify(bodyPartProvider).get();
        verify(bodyPart).setContent(content, type.toString());
    }

}
