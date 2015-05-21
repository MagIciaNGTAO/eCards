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
package com.acme.ecards.api.template.handlebars;

import com.github.jknack.handlebars.Handlebars;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import org.jvnet.testing.hk2testng.HK2;
import org.testng.annotations.Test;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
@HK2
public class HandlebarsProviderNGTest {

    @Inject
    Handlebars sut1;

    @Inject
    Handlebars sut2;

    @Test
    public void verifyInjection() {
        assertThat(sut1).isNotNull();
        assertThat(sut2).isNotNull();
    }

    @Test
    public void verifyIsSingleton() {
        assertThat(sut1).isNotNull().isSameAs(sut2);
    }

}
