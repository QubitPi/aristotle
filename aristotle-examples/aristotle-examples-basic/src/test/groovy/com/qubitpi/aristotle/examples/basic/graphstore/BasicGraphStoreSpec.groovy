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

import com.qubitpi.aristotle.graphstore.GraphStore

import graphql.ExecutionResult
import graphql.GraphQL
import spock.lang.Specification

class BasicGraphStoreSpec extends Specification {

    static final QUERY = "{ bookById(id:\"book-1\")}"

    GraphQL queryDelegate
    ExecutionResult mockedExecutionResult

    GraphStore basicGraphStore

    @SuppressWarnings('GroovyAccessibility')
    def setup() {
        mockedExecutionResult = Mock(ExecutionResult) {
            toSpecification() >> {
                data: [
                        bookById: [
                                id: "book-1",
                                name: "Harry Potter and the Philosopher's Stone"
                        ]
                ]
            }
        }
        queryDelegate = Mock(GraphQL) { execute(QUERY) >> mockedExecutionResult }
        basicGraphStore = new BasicGraphStore(queryDelegate)
    }

    def "Querying the store delegates everything to native GraphQL API"() {
        when:
        basicGraphStore.query(QUERY)

        then:
        1 * queryDelegate.execute(QUERY) >> mockedExecutionResult
    }
}
