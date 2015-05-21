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
package com.acme.ecards.api;

import com.acme.ecards.api.email.EmailMessage;
import com.acme.ecards.api.email.EmailService;
import com.acme.ecards.api.email.impl.MailgunBackupEmailService;
import com.acme.ecards.api.email.impl.MandrillPrimaryEmailService;
import com.acme.ecards.api.kernal.ServiceKernal;
import static com.google.common.collect.ImmutableMap.of;
import java.util.Map;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.testing.hk2testng.HK2;
import org.testng.annotations.Test;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
@HK2
public class IntegrationNGTest {

    @Inject
    ServiceLocator locator;

    @Test(priority = 1)
    public void givenStartupServiceStateEmailServiceShouldBeMandrill() {
        ServiceKernal serviceKernal = locator.getService(ServiceKernal.class);
        serviceKernal.startup();

        EmailService emailService = locator.getService(EmailService.class);
        assertThat(emailService).isInstanceOf(MandrillPrimaryEmailService.class);

        Map<String, Object> context = of("email", "saden1@gmail.com", "firstName", "Sharmarke", "lastName", "Aden");
        EmailMessage message = new EmailMessage("saden1@gmail.com", "Sharmarke Aden", "birthday", context);

        emailService.send(message);
    }

    @Test(priority = 2)
    public void givenFailoverServiceStateEmailServiceShouldBeMailgun() {
        ServiceKernal serviceKernal = locator.getService(ServiceKernal.class);
        serviceKernal.failover();

        EmailService emailService = locator.getService(EmailService.class);
        assertThat(emailService).isInstanceOf(MailgunBackupEmailService.class);

        Map<String, Object> context = of("email", "saden1@gmail.com", "firstName", "Sharmarke", "lastName", "Aden");
        EmailMessage message = new EmailMessage("saden1@gmail.com", "Sharmarke Aden", "birthday", context);

        emailService.send(message);

    }
}
