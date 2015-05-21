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

import com.acme.ecards.api.common.BackupService;
import com.acme.ecards.api.email.EmailMessage;
import com.acme.ecards.api.email.EmailService;
import com.acme.ecards.api.kernal.ServiceLevel;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Session;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;

/**
 * Mailgun backup email service provider implementation.
 *
 * @author Sharmarke Aden (saden1)
 */
@Service
@RunLevel(ServiceLevel.FAILOVER)
public class MailgunBackupEmailService implements EmailService, BackupService {

    private final Session session;
    private final EmailSender emailSender;

    @Inject
    MailgunBackupEmailService(@Named("mailgun") Session session, EmailSender emailSender) {
        this.session = session;
        this.emailSender = emailSender;
    }

    @Override
    public void send(EmailMessage emailMessage) {
        emailSender.send(emailMessage, session);
    }

}
