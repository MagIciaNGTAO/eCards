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
package com.acme.ecards.api.common;

import org.glassfish.hk2.api.Factory;
import org.jvnet.hk2.annotations.Contract;

/**
 * This interface should be implemented in order to provide a factory for
 * nondisposable types.
 *
 * @author Sharmarke Aden (saden1)
 * @param <T> This must be the type of entity for which this is a factory.
 */
@Contract
public interface ProviderFactory<T> extends Factory<T> {

    /**
     * defender method so one doesn't have to implement dispose method.
     */
    @Override
    public default void dispose(T instance) {

    }

}
