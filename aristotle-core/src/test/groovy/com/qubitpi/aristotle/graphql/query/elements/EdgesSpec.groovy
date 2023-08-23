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

class EdgesSpec extends Specification {

    groovy.util.Node node;

    def setup() {
        node = Mock(groovy.util.Node) {
            toGraphQlSpec() >> "node {foo}"
        }
    }

    def "Edges wraps node"() {
        expect:
        new Edges(node).toGraphQlSpec() == "edges {node {foo}}"
    }

    def "Static factory method produces typed instance"() {
        expect:
        Edges.to(node) instanceof Selection
    }

    def "Static factory method produces the same object as all-args constructor does"() {
        expect:
        new Edges(node) == Edges.to(node)
    }

    def "Wrapped node cannot be null"() {
        when:
        new Edges(null)

        then:
        NullPointerException exception = thrown()
        exception.message == "node"
    }

    def "toString produces the same query string"() {
        expect:
        new Edges(node).toGraphQlSpec() == new Edges(node).toString()
    }
}
