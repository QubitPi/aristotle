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

class VariableDefinitionSpec extends Specification {

    ValueWithVariable defaultValue

    def setup() {
        defaultValue = Mock(ValueWithVariable) {
            toGraphQlSpec() >> "foo"
        }
    }

    def "(Serialization) Variable definition can have default value"() {
        expect:
        new VariableDefinition("title", "string", defaultValue).toGraphQlSpec() == '$title: string=foo'
    }

    def "(Serialization) Default value is not required"() {
        expect:
        new VariableDefinition("title", "string", null).toGraphQlSpec() == '$title: string'
    }

    def "Variable name is required"() {
        when:
        new VariableDefinition(null, "string", defaultValue)

        then:
        NullPointerException exception = thrown()
        exception.message == "variable"
    }

    def "Variable type is required"() {
        when:
        new VariableDefinition("title", null, defaultValue)

        then:
        NullPointerException exception = thrown()
        exception.message == "type"
    }

    def "Static factory methods produce the same instances as all-args constructor"() {
        expect:
        VariableDefinition.of("title", "string") == new VariableDefinition("title", "string", null)

        and:
        VariableDefinition.of("title", "string", defaultValue) == new VariableDefinition(
                "title",
                "string",
                defaultValue)
    }

    def "toString produces the same query string"() {
        expect:
        new VariableDefinition("title", "string", defaultValue).toGraphQlSpec() == new VariableDefinition(
                "title",
                "string",
                defaultValue
        ).toString()
    }
}
