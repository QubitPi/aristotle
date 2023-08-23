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

class SelectionSetSpec extends Specification {

    Selection selection1
    Selection selection2

    def setup() {
        selection1 = Mock(Selection) {
            toGraphQlSpec() >> "name"
        }
        selection2 = Mock(Selection) {
            toGraphQlSpec() >> "address"
        }
    }

    def "Selection set groups selections together"() {
        expect:
        new SelectionSet([selection1, selection2]).toGraphQlSpec() == '{name address}'
    }

    def "Static factory method produces the same instance as all-args constructor does"() {
        expect:
        new SelectionSet([selection1, selection2]) == SelectionSet.of(selection1, selection2)
    }

    def "Selections cannot be null"() {
        when:
        new SelectionSet(null)

        then:
        NullPointerException exception = thrown()
        exception.message == "selections"
    }

    def "toString produces the same query string"() {
        expect:
        new SelectionSet([selection1, selection2]).toString() == new SelectionSet([selection1, selection2]).toGraphQlSpec()
    }
}
