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

import com.acme.ecards.api.common.BackupService;
import com.acme.ecards.api.common.PrimaryService;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.runlevel.Sorter;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author Sharmarke Aden (saden1)
 */
@Service
public class ServiceKernalSorter implements Sorter {

    @Override
    public List<ServiceHandle<?>> sort(List<ServiceHandle<?>> descriptors) {
        LinkedList<ServiceHandle<?>> sorted = new LinkedList<>();
        descriptors.forEach(p -> {
            Set<String> contracts = p.getActiveDescriptor().getAdvertisedContracts();
            if (contracts.contains(PrimaryService.class.getName())) {
                sorted.addLast(p);
            } else if (contracts.contains(BackupService.class.getName())) {
                sorted.addFirst(p);
            }
        });

        return sorted;
    }

}
