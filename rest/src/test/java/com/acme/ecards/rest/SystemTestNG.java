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

import com.acme.ecards.rest.model.User;
import java.util.Map;
import javax.ws.rs.client.Entity;
import static javax.ws.rs.client.Entity.entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;
import org.glassfish.jersey.test.JerseyTestNg;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
public class SystemTestNG extends JerseyTestNg.ContainerPerClassTest {

    @Override
    protected Application configure() {
        return new ApiApplication();
    }

    @Test
    public void callToAppResourceShouldReturnOKAndApplicationInformation() {
        Response response = target("app").request().get();

        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        Map<String, String> entity = response.readEntity(new GenericType<Map<String, String>>() {
        });
        assertThat(entity).hasSize(4);
    }

    @DataProvider
    Object[][] invalidGreetings() {
        return new Object[][]{
            {null, "lastname", "email@email.com"},
            {"", "lastname", "email@email.com"},
            {"\t", "lastname", "email@email.com"},
            {"<scirpt/>", "lastname", "email@email.com"},
            {"firstname", null, "email@email.com"},
            {"firstname", "", "email@email.com"},
            {"firstname", "\t", "email@email.com"},
            {"firstname", "<scirpt/>", "email@email.com"},
            {"firstname", "lastname", null},
            {"firstname", "lastname", ""},
            {"firstname", "lastname", "\t"},
            {"firstname", "lastname", "<scirpt/>"}
        };

    }

    @Test(dataProvider = "invalidGreetings")
    public void givenInvalidGreetingBirthdayGreetingsResourceShouldReturnBadRquestAndErrorMessage(
            String firstName, String lastName, String email) {
        User greeting = new User(firstName, lastName, email);
        Response response = target("greetings")
                .path("birthday")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(greeting, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.getStatusCode());
        String entity = response.readEntity(String.class);
        assertThat(entity).isNotEmpty();
    }

    @Test(dataProvider = "invalidGreetings")
    public void givenInvalidGreetingHolidaysGreetingsResourceShouldReturnBadRquestAndErrorMessage(
            String firstName, String lastName, String email) {
        User greeting = new User(firstName, lastName, email);
        Response response = target("greetings")
                .path("holidays")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(greeting, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.getStatusCode());
        String entity = response.readEntity(String.class);
        assertThat(entity).isNotEmpty();
    }

    @Test
    public void givenValidGreetingBirthdayGreetingsResourceShouldReturnOK() {
        User greeting = new User("firstname", "lastname", "saden1@gmail.com");
        Response response = target("greetings")
                .path("birthday")
                .request()
                .post(entity(greeting, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    }

    @Test
    public void givenValidGreetingHolidaysGreetingsResourceShouldReturnOK() {
        User greeting = new User("firstname", "lastname", "saden1@gmail.com");
        Response response = target("greetings")
                .path("holidays")
                .request()
                .post(entity(greeting, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    }

    @Test
    public void givenInvalidGreetingTypeGreetingsResourceShouldReturnBadRequest() {
        User greeting = new User("firstname", "lastname", "saden1@gmail.com");
        Response response = target("greetings")
                .path("invalid")
                .request()
                .post(entity(greeting, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.getStatusCode());
    }
}
