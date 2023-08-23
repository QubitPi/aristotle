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

class DocumentSpec extends Specification {

    Definition definition1
    Definition definition2

    def setup() {
        definition1 = Mock(Definition) {
            toGraphQlSpec() >> '{foo}'
        }
        definition2 = Mock(Definition) {
            toGraphQlSpec() >> '{bar}'
        }
    }

    def "A document is a series of space-separated definitions"() {
        expect:
        new Document([definition1, definition2]).toQuery() == '{foo} {bar}'
    }

    def "Static factory method produces the same instance as all-args constructor does"() {
        expect:
        new Document([definition1, definition2]) == Document.of(definition1, definition2)
    }

    def "Document definitions cannot be null"() {
        when:
        new Document(null)

        then:
        NullPointerException exception = thrown()
        exception.message == "definitions"
    }

    def "toString produces the same query string"() {
        when:
        Document document = new Document([definition1, definition2])

        then:
        document.toString() == document.toQuery()
    }
}
