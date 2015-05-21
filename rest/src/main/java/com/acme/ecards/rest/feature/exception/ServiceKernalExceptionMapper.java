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
package com.acme.ecards.rest.feature.exception;

import com.acme.ecards.api.kernal.ServiceKernal;
import com.acme.ecards.api.kernal.ServiceKernalException;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import static javax.ws.rs.core.Response.status;
import javax.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author Sharmarke Aden (saden)
 */
public class ServiceKernalExceptionMapper implements ExceptionMapper<ServiceKernalException> {

    ServiceKernal kernal;

    @Inject
    ServiceKernalExceptionMapper(ServiceKernal kernal) {
        this.kernal = kernal;
    }

    @Override
    public Response toResponse(ServiceKernalException exception) {
        //if this type of exception is encountered change application state to
        //failover level and return service unavailable response.
        kernal.failover();
        return status(SERVICE_UNAVAILABLE).build();
    }

}
