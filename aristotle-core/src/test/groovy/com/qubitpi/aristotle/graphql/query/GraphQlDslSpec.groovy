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

import static org.bitbucket.jack20191124.graphql.query.GraphQlDsl.argument;
import static org.bitbucket.jack20191124.graphql.query.GraphQlDsl.arguments;
import static org.bitbucket.jack20191124.graphql.query.GraphQlDsl.document;
import static org.bitbucket.jack20191124.graphql.query.GraphQlDsl.entity;
import static org.bitbucket.jack20191124.graphql.query.GraphQlDsl.field;
import static org.bitbucket.jack20191124.graphql.query.GraphQlDsl.selection;
import static org.bitbucket.jack20191124.graphql.query.GraphQlDsl.selections;
import static org.bitbucket.jack20191124.graphql.query.GraphQlDsl.typedOperation;
import static org.bitbucket.jack20191124.graphql.query.GraphQlDsl.stringValue;
import static org.bitbucket.jack20191124.graphql.query.GraphQlDsl.variableDefinition;
import static org.bitbucket.jack20191124.graphql.query.GraphQlDsl.variableDefinitions;
import static org.bitbucket.jack20191124.graphql.query.GraphQlDsl.variableValue

import org.bitbucket.jack20191124.graphql.query.elements.TypedOperation;

import spock.lang.Specification

class GraphQlDslSpec extends Specification {

    def "Verify basic request"() {
        given:
        String expected = "{book {edges {node {id title}}}}"

        String actual = document(
                selection(
                        entity(
                                "book",
                                selections(
                                        field("id"),
                                        field("title")
                                )
                        )
                )
        ).toQuery()

        expect:
        actual == expected
    }

    def "Verify multiple top level selections"() {
        given:
        String expected = "{book {edges {node {user1SecretField}}} book {edges {node {id title}}}}"

        String actual = document(
                selections(
                        entity(
                                "book",
                                selection(
                                        field("user1SecretField")
                                )
                        ),
                        entity(
                                "book",
                                selections(
                                        field("id"),
                                        field("title")
                                )
                        )
                )
        ).toQuery()

        expect:
        actual == expected
    }

    def "Verify query with relationship" () {
        given:
        String expected = "{book {edges {node {id title authors {edges {node {name}}}}}}}"

        String actual = document(
                selections(
                        entity(
                                "book",
                                selections(
                                        field("id"),
                                        field("title"),
                                        field(
                                                "authors",
                                                selection(
                                                        field("name")
                                                )
                                        )
                                )
                        )
                )
        ).toQuery()

        expect:
        actual == expected
    }

    def "Verify query with single string argument"() {
        given:
        String expected = "{book(sort: \"-id\") {edges {node {id title}}}}"

        String actual = document(
                selections(
                        entity(
                                "book",
                                argument(
                                        argument(
                                                "sort",
                                                stringValue("-id")
                                        )
                                ),
                                selections(
                                        field("id"),
                                        field("title")
                                )
                        )
                )
        ).toQuery()

        expect:
        actual == expected
    }

    def "Verify query with multiple string arguments"() {
        given:
        String expected = "{book(sort: \"-id\" id: \"5\") {edges {node {id title}}}}"

        String actual = document(
                selections(
                        entity(
                                "book",
                                arguments(
                                        argument(
                                                "sort",
                                                stringValue("-id")
                                        ),
                                        argument(
                                                "id",
                                                stringValue("5")
                                        )
                                ),
                                selections(
                                        field("id"),
                                        field("title")
                                )
                        )
                )
        ).toQuery()

        expect:
        actual == expected
    }

    def "Verify query with variable"() {
        given:
        String expected = "query myQuery(\$bookId: [String]) {book(ids: \$bookId) {edges {node {id title authors" +
                " {edges {node {name}}}}}}}"

        String actual = document(
                typedOperation(
                        TypedOperation.OperationType.QUERY,
                        "myQuery",
                        variableDefinitions(
                                variableDefinition("bookId", "[String]")
                        ),
                        selections(
                                entity(
                                        "book",
                                        arguments(
                                                argument("ids", variableValue("bookId"))
                                        ),
                                        selections(
                                                field("id"),
                                                field("title"),
                                                field(
                                                        "authors",
                                                        selection(
                                                                field("name")
                                                        )
                                                )
                                        )
                                )
                        )
                )
        ).toQuery();

        expect:
        actual == expected
    }
}
