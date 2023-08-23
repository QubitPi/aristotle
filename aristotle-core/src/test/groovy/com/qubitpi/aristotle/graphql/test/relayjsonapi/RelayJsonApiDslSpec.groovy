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
package com.qubitpi.aristotle.graphql.test.relayjsonapi

import static org.bitbucket.jack20191124.graphql.test.relayjsonapi.RelayJsonApiDsl.attribute
import static org.bitbucket.jack20191124.graphql.test.relayjsonapi.RelayJsonApiDsl.datum
import static org.bitbucket.jack20191124.graphql.test.relayjsonapi.RelayJsonApiDsl.edges
import static org.bitbucket.jack20191124.graphql.test.relayjsonapi.RelayJsonApiDsl.node
import static org.bitbucket.jack20191124.graphql.test.relayjsonapi.RelayJsonApiDsl.resource
import static org.bitbucket.jack20191124.graphql.test.relayjsonapi.RelayJsonApiDsl.toJson

import groovy.json.JsonSlurper
import spock.lang.Specification

class RelayJsonApiDslSpec extends Specification {

    def "Response with relationships are constructed properly"() {
        given:
        String expected = """
            {
                "data":{
                    "book":{
                        "edges":[
                            {
                                "node":{
                                    "id":"1",
                                    "title":"My first book",
                                    "authors":{
                                        "edges":[
                                            {
                                                "node":{
                                                    "name":"Ricky Carmichael"
                                                }
                                            }
                                        ]
                                    }
                                }
                            }
                        ]
                    }
                }
            }
        """

        String actual = datum(
                resource(
                        "book",
                        edges(
                                node(
                                        attribute("id", "1"),
                                        attribute("title", "My first book"),
                                        resource(
                                                "authors",
                                                edges(
                                                        node(
                                                                attribute("name", "Ricky Carmichael")

                                                        )
                                                )
                                        )
                                )
                        )
                )
        ).toJson()

        expect:
        new JsonSlurper().parseText(actual) == new JsonSlurper().parseText(expected)
    }

    def "Object attribute value is serialized properly as part of response"() {
        given:
        Map<String, String> first = new HashMap<>()
        first.put("key", "Lost in the Data")
        first.put("value", "PEN/Faulkner Award")

        Map<String, String> second = new HashMap<>()
        second.put("key", "Bookz")
        second.put("value", "Pulitzer Prize")

        and:
        String expected = """
            [
                {
                    "value":"PEN/Faulkner Award",
                    "key":"Lost in the Data"
                },
                {
                    "value":"Pulitzer Prize",
                    "key":"Bookz"
                }
            ]
        """

        and:
        String actual = toJson([first, second])

        expect:
        new JsonSlurper().parseText(actual) == new JsonSlurper().parseText(expected)
    }
}
