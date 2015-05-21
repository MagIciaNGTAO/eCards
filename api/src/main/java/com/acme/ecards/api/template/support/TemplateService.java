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
package com.acme.ecards.api.template.support;

import com.acme.ecards.api.template.EmailTemplate;
import com.acme.ecards.api.template.EmailTemplateLoader;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import java.io.IOException;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;

/**
 * Provides template helper methods.
 *
 * @author Sharmarke Aden (saden1)
 */
@Service
public class TemplateService {

    private final Handlebars handlebars;
    private final EmailTemplateLoader templateLoader;

    @Inject
    TemplateService(Handlebars handlebars, EmailTemplateLoader templateLoader) {
        this.handlebars = handlebars;
        this.templateLoader = templateLoader;
    }

    public EmailTemplate load(String templateResource) {
        //XXX: right now we only have one template loader, the classpath loader.
        //in the future it might be desirable to search for templates in
        //multiple locations.

        return templateLoader.load(templateResource);
    }

    public String interpolate(String inputTemplate, Object context) throws IOException {
        Template template = handlebars.compileInline(inputTemplate);

        return template.apply(context);
    }

}
