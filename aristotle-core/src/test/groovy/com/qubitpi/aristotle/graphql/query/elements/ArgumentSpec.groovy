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

class ArgumentSpec extends Specification {

    ValueWithVariable argumentValue

    def setup() {
        argumentValue = Mock(ValueWithVariable) {
            toGraphQlSpec() >> "bar"
        }
    }

    def "Argument serializes to GraphQL query"() {
        expect:
        new Argument("foo", argumentValue).toGraphQlSpec() == "foo: bar"
    }

    def "Static factory method produces the same instance as the all-args constructor does"() {
        expect:
        new Argument("foo", argumentValue) == Argument.of("foo", argumentValue)
    }

    def "Argument name cannot be null"() {
        when:
        new Argument(null, argumentValue)

        then:
        NullPointerException exception = thrown()
        exception.message == "name"
    }

    def "Argument value cannot be null"() {
        when:
        new Argument("foo", null)

        then:
        NullPointerException exception = thrown()
        exception.message == "value"
    }

    def "toString produces the same query string"() {
        when:
        Argument argument = new Argument("foo", argumentValue)

        then:
        argument.toString() == argument.toGraphQlSpec()
    }
}
