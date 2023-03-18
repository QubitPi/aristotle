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
package com.qubitpi.aristotle.examples.basic.graphstore

import graphql.schema.DataFetchingEnvironment
import spock.lang.Specification
import spock.lang.Unroll

@SuppressWarnings('GroovyAccessibility')
class DataFetcherFactorySpec extends Specification {

    @Unroll
    def "Factory produces '#dataFetcher' as singleton"() {
        expect:
        DataFetcherFactory."get$dataFetcher"() is DataFetcherFactory."get$dataFetcher"()

        where:
        _ | dataFetcher
        _ | "BookDataFetcher"
        _ | "AuthorDataFetcher"
    }

    @Unroll
    def "'#dataFetcher' returns '#expected' for ID = '#id'"() {
        setup: "configure mocking behavior"
        DataFetchingEnvironment dataFetchingEnvironment = Mock(DataFetchingEnvironment) {
            getArgument(DataFetcherFactory.ID) >> id
            getSource() >> [authorId: id]
        }

        expect: "fetching data returns object with all attributes in it"
        DataFetcherFactory."get$dataFetcher"().get(dataFetchingEnvironment) == expected

        where:
        dataFetcher         | id         | expected
        "BookDataFetcher"   | "book-1"   | [id: "book-1", name: "Harry Potter and the Philosopher's Stone", pageCount: "223", authorId: "author-1"]
        "BookDataFetcher"   | "book-2"   | [id: "book-2", name: "Moby Dick", pageCount: "635", authorId: "author-2"]
        "BookDataFetcher"   | "book-3"   | [id: "book-3", name: "Interview with the vampire", pageCount: "371", authorId: "author-3"]
        "AuthorDataFetcher" | "author-1" | [id: "author-1", firstName: "Joanne", lastName: "Rowling"]
        "AuthorDataFetcher" | "author-2" | [id: "author-2", firstName: "Herman", lastName: "Melville"]
        "AuthorDataFetcher" | "author-3" | [id: "author-3", firstName: "Anne", lastName: "Rice"]
    }
}
