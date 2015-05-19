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
package com.acme.ecards.support.hk2;

import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceLocator;
import static org.glassfish.hk2.extras.ExtrasUtilities.enableTopicDistribution;
import static org.glassfish.hk2.utilities.ServiceLocatorUtilities.enableImmediateScope;
import static org.glassfish.hk2.utilities.ServiceLocatorUtilities.enableInheritableThreadScope;
import static org.glassfish.hk2.utilities.ServiceLocatorUtilities.enableLookupExceptions;
import static org.glassfish.hk2.utilities.ServiceLocatorUtilities.enablePerThreadScope;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
public class HK2Feature implements Feature {

    private final ServiceLocator locator;
    private final DynamicConfigurationService dcs;

    @Inject
    HK2Feature(ServiceLocator locator, DynamicConfigurationService dcs) {
        this.locator = locator;
        this.dcs = dcs;
    }

    @Override
    public boolean configure(FeatureContext context) {
        try {
            dcs.getPopulator().populate(new HK2DescriptorFileFinder());

            enableImmediateScope(locator);
            enableInheritableThreadScope(locator);
            enableLookupExceptions(locator);
            enablePerThreadScope(locator);
            enableTopicDistribution(locator);

            return true;
        }
        catch (IOException | MultiException e) {
            return false;
        }
    }

}
