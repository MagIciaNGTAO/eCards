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
package com.acme.ecards.api.template.handlebars;

import com.github.jknack.handlebars.cache.HighConcurrencyTemplateCache;
import com.github.jknack.handlebars.cache.TemplateCache;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Factory;
import org.jvnet.hk2.annotations.Service;

/**
 * Handlebars TemplateCache provider.
 *
 * @author Sharmarke Aden (saden1)
 */
@Service
public class TemplateCacheProvider implements Factory<TemplateCache> {

    @Singleton
    @Override
    public TemplateCache provide() {
        return new HighConcurrencyTemplateCache();
    }

    @Override
    public void dispose(TemplateCache instance) {
        instance.clear();
    }

}
