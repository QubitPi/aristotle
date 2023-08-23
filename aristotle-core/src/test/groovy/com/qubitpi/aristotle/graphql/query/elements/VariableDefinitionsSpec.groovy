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

class VariableDefinitionsSpec extends Specification {

    VariableDefinition variableDefinition1
    VariableDefinition variableDefinition2

    def setup() {
        variableDefinition1 = Mock(VariableDefinition) {
            toGraphQlSpec() >> "foo"
        }
        variableDefinition2 = Mock(VariableDefinition) {
            toGraphQlSpec() >> "bar"
        }
    }

    def "(Serialization) Variable definitions are surrounded by a pair of parenthesis"() {
        expect:
        new VariableDefinitions([variableDefinition1, variableDefinition2]).toGraphQlSpec() == "(foo bar)"
    }

    def "Static factory method produces the same instance as all-args constructor does"() {
        expect:
        new VariableDefinitions([variableDefinition1, variableDefinition2]) == VariableDefinitions.of(
                variableDefinition1,
                variableDefinition2
        )
    }

    def "Wrapped definitions cannot be null"() {
        when:
        new VariableDefinitions(null)

        then:
        NullPointerException exception = thrown()
        exception.message == "variableDefinitions"
    }

    def "toString produces the same query string"() {
        expect:
        new VariableDefinitions([variableDefinition1, variableDefinition2]).toGraphQlSpec() ==
                new VariableDefinitions([variableDefinition1, variableDefinition2]).toString()
    }
}
