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
package com.acme.ecards.resource;

import com.acme.ecards.model.Greeting;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ManagedAsync;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
@Path("send")
public class SendResource {

    @POST
    @Consumes({APPLICATION_FORM_URLENCODED, APPLICATION_JSON})
    @Produces(APPLICATION_JSON)
    @ManagedAsync
    public void post(@Suspended final AsyncResponse asyncResponse, @Valid Greeting greeting) {
        asyncResponse.resume(Response.ok());
    }
}
