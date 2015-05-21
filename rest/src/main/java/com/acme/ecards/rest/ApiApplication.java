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
package com.acme.ecards.rest;

import com.acme.ecards.rest.feature.exception.ExceptionMappingFeature;
import com.acme.ecards.rest.feature.hk2.HK2Feature;
import com.acme.ecards.rest.feature.jackson.Jackson2Feature;
import org.glassfish.jersey.server.ResourceConfig;
import static org.glassfish.jersey.server.ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK;
import static org.glassfish.jersey.server.ServerProperties.BV_SEND_ERROR_IN_RESPONSE;
import static org.glassfish.jersey.server.ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR;

/**
 *
 * @author Sharmarke Aden (saden)
 */
public class ApiApplication extends ResourceConfig {

    public ApiApplication() {
        this.setApplicationName("eCards")
                .packages("com.acme.ecards.rest.resource")
                .register(HK2Feature.class)
                .register(Jackson2Feature.class)
                .register(ExceptionMappingFeature.class)
                .property(BV_SEND_ERROR_IN_RESPONSE, true)
                .property(BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true)
                .property(RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
    }

}
