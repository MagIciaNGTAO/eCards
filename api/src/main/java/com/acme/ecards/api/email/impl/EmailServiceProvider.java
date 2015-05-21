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

import com.acme.ecards.api.common.ProviderFactory;
import com.acme.ecards.api.email.EmailService;
import com.acme.ecards.api.kernal.ServiceKernal;
import com.acme.ecards.api.kernal.ServiceLevel;
import javax.inject.Inject;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;

/**
 * Returns an email service based on the current service level. Note that all
 * request for email service will be delegated to this provider as it has the
 * highest possible service rank. If the current service level is startup it
 * will return the
 * {@linkplain MandrillPrimaryEmailService primary email service}. if we are at
 * failover level it will return the
 * {@linkplain MailgunBackupEmailService backup email service}.
 *
 * @author Sharmarke Aden (saden1)
 */
@Service
public class EmailServiceProvider implements ProviderFactory<EmailService> {

    private final ServiceLocator locator;
    private final ServiceKernal serviceKernal;

    @Inject
    EmailServiceProvider(ServiceLocator locator, ServiceKernal serviceKernal) {
        this.locator = locator;
        this.serviceKernal = serviceKernal;
    }

    @PerLookup
    @Rank(Integer.MAX_VALUE)
    @Override
    public EmailService provide() {
        int level = serviceKernal.currentServiceLevel();
        if (level < ServiceLevel.FAILOVER) {
            return locator.getService(MandrillPrimaryEmailService.class);
        } else {
            return locator.getService(MailgunBackupEmailService.class);
        }
    }

}
