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
package com.acme.ecards.rest.fixture;

import com.acme.ecards.api.common.ProviderFactory;
import com.acme.ecards.api.config.AppConfig;
import java.io.InputStream;
import static java.util.Collections.unmodifiableMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Rank;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Sharmarke Aden (saden)
 */
public class InvalidConfigProvider implements ProviderFactory<Map<String, Object>> {

    private final Yaml yaml;

    @Inject
    InvalidConfigProvider(Yaml yaml) {
        this.yaml = yaml;
    }

    @Rank(Integer.MAX_VALUE)
    @AppConfig
    @Singleton
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> provide() {
        //load config.yml from the classpath
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("invalid-config.yml");

        return unmodifiableMap(yaml.loadAs(inputStream, Map.class));
    }

}
