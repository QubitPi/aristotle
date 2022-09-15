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
package com.qubitpi.aristotle.async.jobs.jobrows

import com.fasterxml.jackson.databind.ObjectMapper

import spock.lang.Specification
import spock.lang.Unroll

class DefaultJobFieldSpec extends Specification {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()

    @Unroll
    def "JSON serialization of #name is '#serializaiton'"() {
        expect:
        JSON_MAPPER.writeValueAsString([(name): "foo"]) == """{"$serializaiton":"foo"}"""

        where:
        name << DefaultJobField.values()
        serializaiton << DefaultJobField.values().collect { it -> it.getName() }
    }
}
