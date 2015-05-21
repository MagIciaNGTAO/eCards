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

import com.acme.ecards.api.template.EmailTemplateLoader;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import java.io.IOException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
public class TemplateServiceNGTest {

    TemplateService sut;
    Handlebars handlebars;
    EmailTemplateLoader templateLoader;

    @BeforeMethod
    void init() {
        handlebars = mock(Handlebars.class);
        templateLoader = mock(EmailTemplateLoader.class);

        sut = new TemplateService(handlebars, templateLoader);
    }

    @Test
    public void givenTemplateResourceLoadShouldReturnEmailTemplate() {
        String templateResource = "templateResource";

        sut.load(templateResource);

        verify(templateLoader).load(templateResource);
    }

    @Test
    public void givenInputTemplateAndContextCallToInterpolateShouldReturn() throws IOException {
        String inputTemplate = "inputTemplate";
        Object context = "context";
        Template template = mock(Template.class);

        given(handlebars.compileInline(inputTemplate)).willReturn(template);
        sut.interpolate(inputTemplate, context);

        verify(handlebars).compileInline(inputTemplate);
        verify(template).apply(context);
    }

}
