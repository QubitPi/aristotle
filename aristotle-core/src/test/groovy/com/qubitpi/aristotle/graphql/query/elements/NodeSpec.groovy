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

class NodeSpec extends Specification {

    SelectionSet fields

    def setup() {
        fields = Mock(SelectionSet) {
            toGraphQlSpec() >> "{name address}"
        }
    }

    def "Node is a named group of fields"() {
        expect:
        new groovy.util.Node(fields).toGraphQlSpec() == "node {name address}"
    }

    def "Static factory method produces the same instance as all-args constructor does"() {
        expect:
        new groovy.util.Node(fields) == groovy.util.Node.of(fields)
    }

    def "Wrapped fields cannot be null"() {
        when:
        new groovy.util.Node(null)

        then:
        NullPointerException exception = thrown()
        exception.message == "fields"
    }

    def "toString produces the same query string"() {
        expect:
        new groovy.util.Node(fields).toGraphQlSpec() == new groovy.util.Node(fields).toString()
    }
}
