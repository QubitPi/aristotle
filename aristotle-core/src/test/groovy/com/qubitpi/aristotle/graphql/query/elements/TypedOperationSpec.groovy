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
import spock.lang.Unroll

class TypedOperationSpec extends Specification {

    VariableDefinitions variableDefinitions
    SelectionSet selectionSet

    def setup() {
        variableDefinitions = Mock(VariableDefinitions) {
            toGraphQlSpec() >> "(foo)"
        }
        selectionSet = Mock(SelectionSet) {
            toGraphQlSpec() >> "{name address}"
        }
    }

    @Unroll
    def "'#operationType' type serializes to '#expected' in GraphQL query"() {
        expect:
        TypedOperation.OperationType."${operationType}".toGraphQlSpec() == expected

        where:
        operationType  || expected
        "QUERY"        || "query"
        "MUTATION"     || "mutation"
        "SUBSCRIPTION" || "subscription"
    }

    def "A complete typed operation contains type, name, variables, and selections"() {
        expect:
        new TypedOperation(TypedOperation.OperationType.QUERY, "person", variableDefinitions, selectionSet)
                .toGraphQlSpec() == 'query person(foo) {name address}'
    }

    def "Static factory method produces the same instance as the all-args constructor does"() {
        expect:
        new TypedOperation(TypedOperation.OperationType.QUERY, "person", variableDefinitions, selectionSet) ==
                 TypedOperation.of(TypedOperation.OperationType.QUERY, "person", variableDefinitions, selectionSet)

        and:
        new TypedOperation(TypedOperation.OperationType.QUERY, null, null, selectionSet) ==
                TypedOperation.of(TypedOperation.OperationType.QUERY, selectionSet)
    }

    def "Operation type cannot be null"() {
        when:
        new TypedOperation(null, "person", variableDefinitions, selectionSet)

        then:
        NullPointerException exception = thrown()
        exception.message == "operationType"
    }

    def "Selections cannot be null"() {
        when:
        new TypedOperation(TypedOperation.OperationType.QUERY, "person", variableDefinitions, null)

        then:
        NullPointerException exception = thrown()
        exception.message == "selectionSet"
    }

    def "Operation name can be null"() {
        when:
        new TypedOperation(TypedOperation.OperationType.QUERY, null, variableDefinitions, selectionSet)

        then:
        noExceptionThrown()
    }

    def "Variables can be null"() {
        when:
        new TypedOperation(TypedOperation.OperationType.QUERY, "person", null, selectionSet)

        then:
        noExceptionThrown()
    }

    def "toString produces the same query string"() {
        expect:
        new TypedOperation(TypedOperation.OperationType.QUERY, "person", variableDefinitions, selectionSet)
                .toGraphQlSpec() ==
                new TypedOperation(TypedOperation.OperationType.QUERY, "person", variableDefinitions, selectionSet)
                        .toString()
    }
}
