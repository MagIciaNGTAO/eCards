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
package com.acme.ecards.support.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
public class Jackson2Feature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        Configuration configuration = context.getConfiguration();
        boolean isJacksonRegistered = configuration.isRegistered(Jackson2ContextResolver.class);

        //if jackson 2 feature is not already registered then configure and
        //register it.
        if (!isJacksonRegistered) {
            ObjectMapper mapper = new ObjectMapper()
                    .configure(MapperFeature.USE_ANNOTATIONS, true)
                    .configure(MapperFeature.AUTO_DETECT_GETTERS, true)
                    .configure(MapperFeature.AUTO_DETECT_IS_GETTERS, true)
                    .configure(MapperFeature.AUTO_DETECT_SETTERS, true)
                    .configure(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS, false)
                    .configure(MapperFeature.AUTO_DETECT_FIELDS, true)
                    .configure(SerializationFeature.INDENT_OUTPUT, false)
                    .configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true)
                    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                    .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL);

            Jackson2ContextResolver resolver = new Jackson2ContextResolver(mapper);
            context.register(resolver);
            isJacksonRegistered = true;
        }

        return isJacksonRegistered;
    }

}
