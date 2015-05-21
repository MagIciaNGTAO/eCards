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

import org.glassfish.hk2.runlevel.ChangeableRunLevelFuture;
import org.glassfish.hk2.runlevel.ErrorInformation;
import org.glassfish.hk2.runlevel.RunLevelFuture;
import org.glassfish.hk2.runlevel.RunLevelListener;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A listener service that receives service run level transition events.
 *
 * @author Sharmarke Aden (saden1)
 */
@Service
public class ServiceKernelListener implements RunLevelListener {

    private static final Logger LOG = LoggerFactory.getLogger("service.kernel");

    /**
     * {@inheritDoc}
     */
    @Override
    public void onProgress(ChangeableRunLevelFuture currentJob, int levelAchieved) {
        LOG.debug("Service level {} activated", levelAchieved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCancelled(RunLevelFuture currentJob, int levelAchieved) {
        if (currentJob.isDown()) {
            return;
        }

        LOG.debug("Service level activation canceled. Currently acive level is {}",
                levelAchieved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onError(RunLevelFuture currentJob, ErrorInformation errorInformation) {
        if (currentJob.isDown()) {
            return;
        }

        LOG.error("Error Info: {}", errorInformation.getError());
    }

}
