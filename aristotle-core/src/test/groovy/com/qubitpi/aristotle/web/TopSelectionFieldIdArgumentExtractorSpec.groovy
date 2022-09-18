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
package com.qubitpi.aristotle.web

import graphql.language.Document
import graphql.language.Node
import graphql.language.NodeVisitor
import graphql.parser.Parser
import graphql.util.DefaultTraverserContext
import spock.lang.Specification

class TopSelectionFieldIdArgumentExtractorSpec extends Specification {

    def "The first root field argument 'id' value is extracted"() {
        given: "a GraphQL root document object"
        Document document = new Parser().parseDocument(
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

        and: "a visitor that looks for the '0x3'"
        NodeVisitor nodeVisitor = new TopSelectionFieldIdArgumentExtractor()

        when: "the document accepts the visit from the visitor"
        document.accept(DefaultTraverserContext.simple(document), nodeVisitor)

        then: "the target '0x3' is picked up"
        ((TopSelectionFieldIdArgumentExtractor) nodeVisitor).getArgumentValue().get() == "0x3"
    }
}
