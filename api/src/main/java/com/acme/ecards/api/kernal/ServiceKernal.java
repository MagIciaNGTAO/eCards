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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import org.glassfish.hk2.runlevel.RunLevelController;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ServiceKernal facilitates the initialization and management of application
 * run level state.
 *
 * @author Sharmarke Aden (saden1)
 */
@Service
public class ServiceKernal {

    private static final Logger LOG = LoggerFactory.getLogger("service.kernel");

    private final RunLevelController controller;
    //injected so the listener is instantiated
    private final ServiceKernelListener listener;

    @Inject
    ServiceKernal(RunLevelController controller, ServiceKernelListener listener) {
        this.controller = controller;
        this.listener = listener;
    }

    /**
     * Set the current run level of the application to
     * {@link ServiceLevel.STARTUP}.
     *
     * Note that failover recovery generally requires human intervention. This
     * method is made accessible for testing purpose and in the event that
     * secure and automated runtime recovery is implemented.
     */
    @PostConstruct
    public void startup() {
        if (controller.getCurrentRunLevel() < ServiceLevel.STARTUP) {
            LOG.info("Service kernel proceeding to startup state [run level {}]",
                    ServiceLevel.STARTUP);
        } else if (controller.getCurrentRunLevel() > ServiceLevel.STARTUP) {
            LOG.info("Service kernel receding to startup state [run level {}]",
                    ServiceLevel.STARTUP);
        }

        controller.proceedTo(ServiceLevel.STARTUP);
    }

    /**
     * Set the current run level of the application to
     * {@link ServiceLevel.SHUTDOWN}.
     *
     * If the current run level of the application is above
     * {@link ServiceLevel.SHUTDOWN run level state} then set the application's
     * run level to {@link ServiceLevel.SHUTDOWN}. Once the application is in
     * shutdown state injection of run level services will no longer work.
     */
    @PreDestroy
    public void shutdown() {
        if (controller.getCurrentRunLevel() > ServiceLevel.SHUTDOWN) {
            LOG.info("Service kernel receding to shutdown state [run level {}]",
                    ServiceLevel.STARTUP);

            controller.proceedTo(ServiceLevel.STARTUP);
        }
    }

    /**
     * Set the current run level of the application to
     * {@link ServiceLevel.FAILOVER}.
     *
     * If the current run level of the application is below
     * {@link ServiceLevel.FAILOVER} run level state then set the application's
     * run level to failover. Note that failover recovery generally requires
     * human intervention.
     */
    public void failover() {
        if (controller.getCurrentRunLevel() < ServiceLevel.FAILOVER) {
            LOG.info("Service kernel proceeding to failover state [run level ({}]",
                    ServiceLevel.FAILOVER);

            controller.proceedTo(ServiceLevel.FAILOVER);
        }
    }

    /**
     * Get the current service level.
     *
     * @return the current service level.
     */
    public int currentServiceLevel() {
        return controller.getCurrentRunLevel();
    }

}
