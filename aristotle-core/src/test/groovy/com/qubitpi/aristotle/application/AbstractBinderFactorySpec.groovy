/*
 * Copyright Jiaqi Liu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qubitpi.aristotle.application

import com.qubitpi.aristotle.graphstore.GraphStore
import com.qubitpi.aristotle.graphstore.TestGraphStore

import org.glassfish.hk2.api.DynamicConfiguration
import org.glassfish.hk2.utilities.Binder

import spock.lang.Shared
import spock.lang.Specification

class AbstractBinderFactorySpec extends Specification {

    @Shared
    TestBinderFactory binderFactory

    def setup() {
        binderFactory = new TestBinderFactory()
    }

    def "Test configure bindings"() {
        given: "an mocked HK2 Descriptor binder "
        DynamicConfiguration dynamicConfiguration = Mock(DynamicConfiguration)

        when: "the Descriptor binder is used for resource configuration"
        Binder binder = binderFactory.buildBinder()
        binder.bind(dynamicConfiguration)

        then: "the resource configuration object is present"
        binder != null

        and: "object storage resource is injected"
        1 * dynamicConfiguration.bind(
                {
                    it.advertisedContracts.contains(GraphStore.canonicalName)
                    it.implementation.contains(TestGraphStore.canonicalName)
                },
                _
        )
    }

    def "BindAtEnd is called when binding"() {
        given: "an AbstractBinder"
        DynamicConfiguration dynamicConfiguration = Mock(DynamicConfiguration)

        when: "Bind is called"
        binderFactory.buildBinder().bind(dynamicConfiguration)

        then: "The afterBinding hook was called"
        binderFactory.afterBindingHookWasCalled
    }
}
