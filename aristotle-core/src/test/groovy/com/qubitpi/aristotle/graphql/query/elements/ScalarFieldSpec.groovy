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

class ScalarFieldSpec extends Specification {

    def "Scalar field is an un-quoted string"() {
        expect:
        new ScalarField("foo").toGraphQlSpec() == "foo"
    }

    def "Static factory method produces the same instance as all-args constructor does"() {
        expect:
        new ScalarField("foo") == ScalarField.of("foo")
    }

    def "Field name cannot be null"() {
        when:
        new ScalarField(null)

        then:
        NullPointerException exception = thrown()
        exception.message == "name"
    }

    def "toString produces the same query string"() {
        expect:
        new ScalarField("foo").toGraphQlSpec() == new ScalarField("foo").toString()
    }
}
