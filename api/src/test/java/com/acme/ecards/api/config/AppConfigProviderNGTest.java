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
package com.acme.ecards.api.config;

import java.util.Map;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import org.jvnet.testing.hk2testng.HK2;
import org.testng.annotations.Test;

/**
 *
 * @author saden
 */
@HK2
public class AppConfigProviderNGTest {

    @Inject
    @AppConfig
    Map<String, Object> sut1;
    @Inject
    @AppConfig
    Map<String, Object> sut2;

    @Test
    public void verifyInjection() {
        assertThat(sut1).isNotNull();
        assertThat(sut2).isNotNull();
    }

    @Test
    public void verifyIsSingleton() {
        assertThat(sut1).isNotNull().isSameAs(sut2);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void callToClearShouldThrowException() {
        sut1.clear();
    }

}
