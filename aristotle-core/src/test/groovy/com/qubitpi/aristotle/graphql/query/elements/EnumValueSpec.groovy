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
package com.qubitpi.aristotle.graphql.query.elements

import spock.lang.Specification

class EnumValueSpec extends Specification {

   def "EnumValue is not quoted in query"() {
       expect:
       new EnumValue("foo").toGraphQlSpec() == "foo"
   }

    def "Static factory method produces typed instance"() {
        expect:
        EnumValue.of("foo") instanceof ValueWithVariable
    }

    def "Static factory method produces the same instance as the no-args constructor does"() {
        expect:
        new EnumValue("foo") == EnumValue.of("foo")
    }

    def "Wrapped enum cannot be null"() {
        when:
        new EnumValue(null)

        then:
        NullPointerException exception = thrown()
        exception.message == "value"
    }

    def "toString produces the same query string"() {
        expect:
        new EnumValue("foo").toString() == new EnumValue("foo").toGraphQlSpec()
    }
}
