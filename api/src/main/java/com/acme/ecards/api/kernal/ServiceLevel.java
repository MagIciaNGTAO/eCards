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
package com.acme.ecards.api.kernal;

/**
 * ServiceLevel is a class that contains service run level constants.
 *
 * @author Sharmarke Aden (saden1)
 */
public class ServiceLevel {


    /**
     * Shutdown service level. At this level all services with run level above 0
     * will not be available.
     */
    public static final int SHUTDOWN = 0;

    /**
     * Startup service level. All services including failover services will be
     * available at this run level.
     */
    public static final int STARTUP = 10;

    /**
     * Failover service run level. Failover services will be made available to
     * take over for failed services.
     */
    public static final int FAILOVER = 20;

    private ServiceLevel() {
    }

}
