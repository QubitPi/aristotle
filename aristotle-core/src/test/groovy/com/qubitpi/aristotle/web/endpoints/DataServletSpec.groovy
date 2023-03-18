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
package com.qubitpi.aristotle.web.endpoints

import com.qubitpi.aristotle.graphstore.GraphStore

import graphql.language.Document
import graphql.parser.Parser
import spock.lang.Specification

class DataServletSpec extends Specification {

    GraphStore graphStore

    DataServlet servlet

    def "There is GraphQL-native endpoint which takes native GraphQL query and returns data in exactly the same native format"() {
        setup:
        graphStore = Mock(GraphStore) {
            query(_ as String) >> {}
        }
        servlet = new DataServlet(graphStore)

        when:
        servlet.getData("")

        then:
        1 * graphStore.query(_ as String)
    }

    @SuppressWarnings('GroovyAccessibility')
    def "The first root field argument 'id' value is extracted"() {
        given: "a GraphQL root document object"
        Document document = Parser.parse(
                """
                query {
                    getTask(id: "0x3") {
                        id
                        title
                        completed
                    }
                    getAssignee(id: "EWGETBSDF") {
                        name
                        title
                        department
                    }
                }
                """
        )

        expect: "'id' argument is extracted"
        DataServlet.getRootEntityId(document) == "0x3"
    }

    @SuppressWarnings('GroovyAccessibility')
    def "When the first root field argument 'id' does not exit in query, error is returned from servlet"() {
        given: "a GraphQL root document object without 'id' argument"
        Document document = Parser.parse(
                """
                query {
                    getTask {
                        id
                        title
                        completed
                    }
                }
                """
        )

        when: "attempting to extract 'id' argument"
        DataServlet.getRootEntityId(document)

        then: "an error is thrown"
        thrown(IllegalArgumentException)
    }
}
