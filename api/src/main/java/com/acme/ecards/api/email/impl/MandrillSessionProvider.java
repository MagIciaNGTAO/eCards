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
import com.acme.ecards.api.config.AppConfig;
import com.acme.ecards.api.email.support.SessionService;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.mail.Session;
import org.jvnet.hk2.annotations.Service;

/**
 * Provides javamail session object based on mandrill configuration.
 *
 * @author Sharmarke Aden (saden1)
 */
@Service
public class MandrillSessionProvider implements ProviderFactory<Session> {

    private final Map<String, Object> config;
    private final SessionService sessionFactory;

    /**
     *
     * @param config
     */
    @Inject
    MandrillSessionProvider(@AppConfig Map<String, Object> config, SessionService sessionFactory) {
        this.config = config;
        this.sessionFactory = sessionFactory;
    }

    @Named("mandrill")
    @Singleton
    @Override
    public Session provide() {
        @SuppressWarnings("unchecked")
        Map<String, Map<String, Object>> emailServices = (Map) config.get("email-services");
        Map<String, Object> mandrill = emailServices.get("mandrill");

        return sessionFactory.newSession(mandrill);
    }

}
