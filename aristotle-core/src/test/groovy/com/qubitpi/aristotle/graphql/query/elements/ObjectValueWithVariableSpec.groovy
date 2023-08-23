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

class ObjectValueWithVariableSpec extends Specification {

    def "Wrapped object is Jackson-serialized"() {
        given: "a relationship"
        Author author = new Author()
        author.setId(2L)

        and: "a root level entity instance"
        Book book = new Book()
        book.setId(1)
        book.setTitle("my new book!")
        book.setAuthors([author])

        and:
        String expected = "{id:1,title:\"my new book!\",authors:[{id:2}]}"

        and:
        String actual = new ObjectValueWithVariable(book).toGraphQlSpec()

        expect:
        actual == expected
    }

    def "Wrapped object cannot be null"() {
        when:
        new ObjectValueWithVariable(null)

        then:
        NullPointerException exception = thrown()
        exception.message == "object"
    }
}
