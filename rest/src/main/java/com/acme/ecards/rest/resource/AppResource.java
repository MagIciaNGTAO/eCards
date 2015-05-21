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
package com.acme.ecards.rest.resource;

import com.acme.ecards.api.config.AppConfig;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.ok;
import org.glassfish.jersey.server.ManagedAsync;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
@Path("app")
public class AppResource {

    private final Map<String, Object> appConfig;

    @Inject
    AppResource(@AppConfig Map<String, Object> appConfig) {
        this.appConfig = appConfig;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @ManagedAsync
    public void post(@Suspended final AsyncResponse asyncResponse) {
        asyncResponse.resume(ok(appConfig.get("app")).build());
    }
}
