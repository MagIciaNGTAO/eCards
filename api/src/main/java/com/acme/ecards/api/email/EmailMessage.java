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
package com.acme.ecards.api.email;

import java.util.Collections;
import java.util.Map;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
public class EmailMessage {

    private final String email;
    private final String personal;
    private final String templateId;
    private final Map<String, Object> context;

    public EmailMessage(String email,
            String personal,
            String templateId,
            Map<String, Object> context) {
        this.email = email;
        this.personal = personal;
        this.templateId = templateId;
        this.context = context;
    }

    public String getEmail() {
        return email;
    }

    public String getPersonal() {
        return personal;
    }

    public String getTemplateId() {
        return templateId;
    }

    public Map<String, Object> getContext() {
        return Collections.unmodifiableMap(context);
    }

}
