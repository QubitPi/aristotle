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

class ObjectFieldSpec extends Specification {

    Arguments arguments
    SelectionSet selectionSet

    def setup() {
        arguments = Mock(Arguments) {
            toGraphQlSpec() >> '(id: "1")'
        }
        selectionSet = Mock(SelectionSet) {
            toGraphQlSpec() >> '{id title}'
        }
    }

    def "Object field with only a name and a selection set is a query"() {
        expect:
        ObjectField.withoutArguments("book", selectionSet).toGraphQlSpec() == 'book {id title}'
    }

    def "Object field with a name, arguments, and a selection set is a query"() {
        expect:
        ObjectField.of("book", arguments, selectionSet).toGraphQlSpec() == 'book(id: "1") {id title}'
    }

    def "Field name cannot be null"() {
        when:
        new ObjectField(null, arguments, selectionSet)

        then:
        NullPointerException exception = thrown()
        exception.message == "name"
    }

    def "Arguments cannot be null"() {
        when:
        new ObjectField("book", null, selectionSet)

        then:
        NullPointerException exception = thrown()
        exception.message == "arguments"
    }

    def "SelectionSet cannot be null"() {
        when:
        new ObjectField("book", arguments, null)

        then:
        NullPointerException exception = thrown()
        exception.message == "selectionSet"
    }

    def "toString produces the same query string"() {
        expect:
        ObjectField.of("book", arguments, selectionSet).toGraphQlSpec() == ObjectField.of(
                "book",
                arguments,
                selectionSet
        ).toString()

        and:
        ObjectField.withoutArguments("book", selectionSet).toGraphQlSpec() == ObjectField.withoutArguments(
                "book",
                selectionSet
        ).toString()
    }
}
