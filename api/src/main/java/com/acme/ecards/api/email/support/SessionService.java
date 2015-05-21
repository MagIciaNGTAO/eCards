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

import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import org.jvnet.hk2.annotations.Service;

/**
 * Provides javamail session helper methods.
 *
 * XXX: Due to the terribleness to JavaMail Session API this class is difficult
 * to mock and test. Because of time constraint this class will be left untest
 * for now. If we were to test it we are left with couple of choice, a) use
 * powermock or b) wrap and skin technique by martin flowers.
 *
 * @author Sharmarke Aden (saden1)
 */
@Service
public class SessionService {

    @SuppressWarnings("unchecked")
    public Session newSession(final Map<String, Object> config) {
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        (String) config.get("username"), (String) config.get("password"));
            }
        };

        Properties properties = new Properties();
        properties.putAll((Map) config.get("smtp"));

        return Session.getInstance(properties, authenticator);
    }

    public void send(Session session, MimeMessage mimeMessage)
            throws MessagingException, NoSuchProviderException {
        Transport transport = session.getTransport();
        try {
            transport.connect();
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        }
        finally {
            if (transport.isConnected()) {
                transport.close();
            }
        }
    }

}
