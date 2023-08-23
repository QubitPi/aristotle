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
package com.qubitpi.aristotle.graphql.query

import org.bitbucket.jack20191124.graphql.query.elements.Book
import org.bitbucket.jack20191124.graphql.query.elements.ObjectValueWithVariable

import spock.lang.Specification

class VariableFieldSerializerSpec extends Specification {

    def "Regular value is quoted"() {
        given: "a root level entity instance"
        Book book = new Book()
        book.setId(1)
        book.setTitle("my new book!")

        and:
        String expected = "{id:1,title:\"my new book!\"}"

        and:
        String actual = new ObjectValueWithVariable(book).toGraphQlSpec()

        expect:
        actual == expected
    }

    def "Variable value is unquoted"() {
        given: "a root level entity instance"
        Book book = new Book()
        book.setId(1)
        book.setTitle("\$firstTitle")

        and:
        String expected = "{id:1,title:\$firstTitle}"

        and:
        String actual = new ObjectValueWithVariable(book).toGraphQlSpec()

        expect:
        actual == expected
    }
}
