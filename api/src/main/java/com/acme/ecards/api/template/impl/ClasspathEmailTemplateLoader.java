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
package com.acme.ecards.api.template.impl;

import com.acme.ecards.api.template.EmailTemplate;
import com.acme.ecards.api.template.EmailTemplateException;
import com.acme.ecards.api.template.EmailTemplateLoader;
import java.io.InputStream;
import static java.lang.String.format;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import org.yaml.snakeyaml.Yaml;

/**
 * Load yaml based email templates from the classpath.
 *
 * @author Sharmarke Aden (saden1)
 */
@Service
public class ClasspathEmailTemplateLoader implements EmailTemplateLoader {

    private final Yaml yaml;
    private final ConcurrentHashMap<String, EmailTemplate> cache;

    @Inject
    ClasspathEmailTemplateLoader(Yaml yaml, ConcurrentHashMap<String, EmailTemplate> cache) {
        this.yaml = yaml;
        this.cache = cache;
    }

    @SuppressWarnings("unchecked")
    @Override
    public EmailTemplate load(String templateId) {
        try {
            return cache.computeIfAbsent(templateId, k -> {
                String path = format("%s.yml", templateId);
                InputStream is = getClass().getClassLoader().getResourceAsStream(path);

                Map<String, String> template = yaml.loadAs(is, Map.class);
                String subject = template.get("subject");
                String plain = template.get("plain");
                String html = template.get("html");

                //TODO: validate that subject and plain text variant are not null or empty.
                return new DefaultEmailTemplate(subject, plain, html);
            });
        }
        catch (Exception e) {
            throw new EmailTemplateException(format("could not load template [%s]", templateId), e);
        }

    }
}
