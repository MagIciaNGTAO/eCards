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

import com.acme.ecards.api.email.EmailMessage;
import com.acme.ecards.api.email.EmailService;
import com.acme.ecards.api.kernal.ServiceKernal;
import com.acme.ecards.rest.model.Greeting;
import static java.lang.String.format;
import java.util.Map;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import static javax.ws.rs.core.Response.status;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.glassfish.jersey.server.ManagedAsync;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
@Path("geetings")
public class GreetingsResource {

    private final EmailService emailService;
    private final ServiceKernal serviceKernal;

    @Inject
    GreetingsResource(EmailService emailService, ServiceKernal serviceKernal) {
        this.emailService = emailService;
        this.serviceKernal = serviceKernal;
    }

    @Path("{greetingType}")
    @POST
    @Consumes({APPLICATION_FORM_URLENCODED, APPLICATION_JSON})
    @Produces(APPLICATION_JSON)
    @ManagedAsync
    public void post(@Suspended final AsyncResponse asyncResponse,
            @PathParam("greetingType") final String greetingType,
            @Valid final Greeting greeting) {
        try {
            String email = greeting.getEmail();
            String personal = format("%s %s", greeting.getFirstName(), greeting.getLastName());

            Map<String, Object> context = ImmutableMap.<String, Object>builder()
                    .put("email", email)
                    .put("firstName", greeting.getFirstName())
                    .put("lastName", greeting.getLastName())
                    .build();

            EmailMessage message = new EmailMessage(email, personal, greetingType, context);
            emailService.send(message);
            asyncResponse.resume(Response.ok());
        } catch (Exception e) {
            //TODO: This should be handled through filter.
            serviceKernal.failover();
            asyncResponse.resume(status(SERVICE_UNAVAILABLE).build());
        }
    }
}
