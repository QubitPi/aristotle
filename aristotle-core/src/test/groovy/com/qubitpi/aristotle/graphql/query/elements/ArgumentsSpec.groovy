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

class ArgumentsSpec extends Specification {

    Argument argument1
    Argument argument2

    def setup() {
        argument1 = Mock(Argument) {
            toGraphQlSpec() >> 'name: "Jack"'
        }
        argument2 = Mock(Argument) {
            toGraphQlSpec() >> 'gender: "male"'
        }
    }

    def "Arguments serializes to GraphQL query"() {
        expect:
        new Arguments([argument1, argument2]).toGraphQlSpec() == '(name: "Jack" gender: "male")'
    }

    def "Static factory method produces empty arguments"() {
        expect:
        Arguments.empty().toGraphQlSpec() == ""
    }

    def "Static factory method produces instance wrapping specified arguments"() {
        expect:
        Arguments.of(argument1, argument2).toGraphQlSpec() == '(name: "Jack" gender: "male")'
    }

    def "Wrapped arguments cannot be null"() {
        when:
        new Arguments(null)

        then:
        NullPointerException exception = thrown()
        exception.message == "arguments"
    }

    def "toString produces the same query string"() {
        when:
        Arguments arguments = new Arguments([argument1, argument2])

        then:
        arguments.toString() == arguments.toGraphQlSpec()
    }
}
