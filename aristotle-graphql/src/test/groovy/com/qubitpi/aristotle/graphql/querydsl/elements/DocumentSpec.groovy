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
package com.qubitpi.aristotle.graphql.querydsl.elements

import spock.lang.Specification
import spock.lang.Subject

class DocumentSpec extends Specification {

    def "Document cannot be constructed with a null definition list"() {
        when: "definition list is null"
        @Subject
        Document document = new Document(null)

        then: "error is thrown"
        thrown(NullPointerException)
    }

    def "Verify multiple-query serialization"() {
        given: "a multi-query definition"
        List<Definition> definitions = multiQueryDefinitions()

        and: "a GraphQL document wrapping the multi-query definition"
        @Subject
        Document document = new Document(definitions)

        when: "the document serializes to a GraphQL query"
        String actual = document.toQuery()

        then: "the query is a valid GraphQL multi-query"
        actual == "${bookWithTitle()} ${bookWithIdAndTitle()}"
    }

    def "GraphQL query can also be obtained via toString()"() {
        given: "a multi-query definition"
        List<Definition> definitions = multiQueryDefinitions()

        and: "a GraphQL document wrapping the multi-query definition"
        @Subject
        Document document = new Document(definitions)

        when: "the document serializes to a string using standard toString() method"
        String actual = document.toQuery()

        then: "the returned string is a valid GraphQL multi-query"
        actual == "${bookWithTitle()} ${bookWithIdAndTitle()}"
    }

    def "Both toQuery() and toString() can be used to obtain valid GraphQL query"() {
        given: "a multi-query definition"
        List<Definition> definitions = multiQueryDefinitions()

        and: "a GraphQL document wrapping the multi-query definition"
        @Subject
        Document document = new Document(definitions)

        expect: "toQuery() and toString() method returns the same query serialization"
        document.toQuery() == document.toString()
    }

    /**
     * Sets the mocking behavior of a multi-query-definition list
     *
     * @return a new instance
     */
    def multiQueryDefinitions() {
        return [
                Mock(Definition) { toGraphQLSpec() >> bookWithTitle() },
                Mock(Definition) { toGraphQLSpec() >> bookWithIdAndTitle() }
        ]
    }

    /**
     * A single query definition asking for for single attribute.
     *
     * @return a new instance
     */
    def bookWithTitle() {
        "{book {edges {node {title}}}"
    }

    /**
     * A single query definition asking for two attributes
     * @return
     */
    def bookWithIdAndTitle() {
        "book {edges {node {id title}}}}"
    }
}
