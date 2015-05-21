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
package com.acme.ecards.api.kernal;

import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import org.glassfish.hk2.runlevel.RunLevelController;
import org.jvnet.testing.hk2testng.HK2;
import org.testng.annotations.Test;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
@HK2
public class ServiceKernalNGTest {

    @Inject
    ServiceKernal sut;

    @Inject
    RunLevelController controller;

    @Test(priority = 0)
    public void verifyInjection() {
        assertThat(sut).isNotNull();
        assertThat(controller).isNotNull();
    }

    @Test(priority = 0)
    public void verifyCurrentState() {
        assertThat(controller.getCurrentRunLevel()).isEqualTo(ServiceLevel.STARTUP);
    }

    @Test(priority = 1)
    public void callToFailoverShouldSetCurrentLevelToFailover() {
        sut.failover();
        assertThat(controller.getCurrentRunLevel()).isEqualTo(ServiceLevel.FAILOVER);
    }

    @Test(priority = 2)
    public void callToStartupShouldSetCurrentLevelToStartup() {
        sut.startup();
        assertThat(controller.getCurrentRunLevel()).isEqualTo(ServiceLevel.STARTUP);
    }

    @Test(priority = 3)
    public void callToShutdownShouldSetCurrentLevelToShutdown() {
        sut.shutdown();
        assertThat(controller.getCurrentRunLevel()).isEqualTo(ServiceLevel.STARTUP);
    }

}
