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

import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
public class DefaultEmailTemplateNGTest {

    DefaultEmailTemplate sut;

    @BeforeMethod
    void init() {
        sut = new DefaultEmailTemplate("subject", "plain", "html");
    }

    @Test
    public void callToGetSubjectShouldReturnSubject() {
        assertThat(sut.getSubject()).isEqualTo("subject");
    }

    @Test
    public void callToGetGetPlainShouldReturnPlain() {
        assertThat(sut.getPlain()).isEqualTo("plain");
    }

    @Test
    public void callToGetHtmlShouldReturnHtml() {
        assertThat(sut.getHtml()).isEqualTo("html");
    }
}
