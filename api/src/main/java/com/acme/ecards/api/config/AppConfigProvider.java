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
package com.acme.ecards.api.config;

import com.acme.ecards.api.common.ProviderFactory;
import java.io.InputStream;
import static java.util.Collections.unmodifiableMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import org.yaml.snakeyaml.Yaml;

/**
 * Provides a {@linkplain Map map} containing config.yml configuration
 * properties. Note that only one instance of the configuration map will be
 * created and that an immutable map is returned .
 *
 * @author Sharmarke Aden (saden1)
 */
@Service
public class AppConfigProvider implements ProviderFactory<Map<String, Object>> {

    private final Yaml yaml;

    @Inject
    AppConfigProvider(Yaml yaml) {
        this.yaml = yaml;
    }

    @AppConfig
    @Singleton
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> provide() {
        //load config.yml from the classpath
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.yml");

        return unmodifiableMap(yaml.loadAs(inputStream, Map.class));
    }

}
