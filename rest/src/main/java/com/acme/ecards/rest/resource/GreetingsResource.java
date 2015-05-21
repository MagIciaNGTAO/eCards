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
import com.acme.ecards.rest.model.User;
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
import static javax.ws.rs.core.Response.ok;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.glassfish.jersey.server.ManagedAsync;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
@Path("greetings")
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
            @Valid final User user) {
        String email = user.getEmail();
        String personal = format("%s %s", user.getFirstName(), user.getLastName());

        Map<String, Object> context = ImmutableMap.<String, Object>builder()
                .put("email", email)
                .put("firstName", user.getFirstName())
                .put("lastName", user.getLastName())
                .build();

        EmailMessage message = new EmailMessage(email, personal, greetingType, context);
        emailService.send(message);
        asyncResponse.resume(ok());
    }
}
