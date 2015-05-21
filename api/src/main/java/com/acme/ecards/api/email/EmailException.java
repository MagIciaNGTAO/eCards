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
package com.acme.ecards.api.email;

import com.acme.ecards.api.kernal.ServiceKernalException;

/**
 * EmailException is an exception that gets thrown if an error is encountered
 * during the sending of an email.
 *
 * @author Sharmarke Aden (saden1)
 */
public class EmailException extends ServiceKernalException {

    private static final long serialVersionUID = -9199041237212139568L;

    /**
     * Constructs an instance of <code>EmailException</code> with the specified
     * detail message and cause.
     *
     * @param msg the detail message.
     * @param cause the cause
     */
    public EmailException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
