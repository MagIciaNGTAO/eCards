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

import com.acme.ecards.rest.fixture.InvalidConfigProvider;
import com.acme.ecards.rest.model.User;
import static javax.ws.rs.client.Entity.entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.test.JerseyTestNg;
import org.testng.annotations.Test;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
public class FailoverSystemTestNG extends JerseyTestNg.ContainerPerClassTest {

    @Override
    protected Application configure() {
        ApiApplication app = new ApiApplication();
        app.registerInstances(new AbstractBinder() {

            @Override
            protected void configure() {
                addActiveDescriptor(InvalidConfigProvider.class);
            }
        });

        return app;
    }

    @Test(priority = 1)
    public void givenValidGreetingBirthdayGreetingsResourceShouldReturnServiceUnavailable() {
        User greeting = new User("firstname", "lastname", "saden1@gmail.com");
        Response response = target("greetings")
                .path("birthday")
                .request()
                .post(entity(greeting, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus()).isEqualTo(SERVICE_UNAVAILABLE.getStatusCode());
    }

    @Test(priority = 1)
    public void givenValidGreetingHolidaysGreetingsResourceShouldReturnOK() {
        User greeting = new User("firstname", "lastname", "saden1@gmail.com");
        Response response = target("greetings")
                .path("holidays")
                .request()
                .post(entity(greeting, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    }
}
