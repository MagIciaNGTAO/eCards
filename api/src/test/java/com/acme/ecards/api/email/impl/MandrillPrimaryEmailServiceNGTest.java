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
import java.util.Properties;
import javax.mail.Session;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
public class MandrillPrimaryEmailServiceNGTest {

    MandrillPrimaryEmailService sut;
    Session session;
    EmailSender emailSender;

    @BeforeMethod
    void init() {
        session = Session.getInstance(new Properties());
        emailSender = mock(EmailSender.class);
        sut = new MandrillPrimaryEmailService(session, emailSender);
    }

    @Test
    public void callToSendShouldCallEmailSenderSendMethod() {
        EmailMessage message = mock(EmailMessage.class);
        sut.send(message);

        verify(emailSender).send(message, session);
    }

}
