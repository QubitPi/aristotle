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

import graphql.schema.GraphQLSchema
import graphql.schema.idl.RuntimeWiring
import spock.lang.Specification
import spock.lang.Unroll

import java.util.stream.Collectors

@SuppressWarnings('GroovyAccessibility')
class GraphQLFactorySpec extends Specification {

    def "Factory always return the same instance"() {
        expect:
        GraphQLFactory.getInstance() is GraphQLFactory.getInstance()
    }

    def "Factory is able to load resource as a whole string from resource folder in the case of #description"() {
        expect:
        GraphQLFactory.getGraphQLSchemaResourceAsString("test-schema.graphqls") == "This is a test file\n"
    }

    def "File not in resource folder are not loadable"() {
        when: "a file not in resource path is triing to be loaded"
        GraphQLFactory.getGraphQLSchemaResourceAsString("non-existing")

        then: "an error is thrown saying resource not found"
        Exception exception = thrown(IllegalStateException)
        exception.message == "GraphQL schema file not found: 'non-existing'"
    }

    @Unroll
    def "'#entity' with field mapping of '#fieldMapping' is built into GraphQL schema"() {
        when: "a schema is built out of a definition file"
        GraphQLSchema schema = GraphQLFactory.buildSchema(
                GraphQLFactory.getGraphQLSchemaResourceAsString("schema.graphqls")
        )

        then: "the query type and its selection sets are all loaded"
        schema
                .getObjectType(entity)
                .getDefinition()
                .getFieldDefinitions()
                .stream()
                .map({ it -> [(it.name): it.type.name] })
                .collect(Collectors.toList()) == fieldMapping

        where:
        entity   | fieldMapping
        "Query"  | [[bookById: "Book"]]
        "Book"   | [[id: "ID"], [name: "String"], [pageCount: "Int"], [author: "Author"]]
        "Author" | [[id: "ID"], [firstName: "String"], [lastName: "String"]]
    }

    def "Runtime wiring bundles two Data Fetchers - one for book and the other for author"() {
        when: "data fetchers are being built for basic application"
        RuntimeWiring runtimeWiring = GraphQLFactory.buildWiring()

        then: "two data fetchers, one for book and another for author, are built"
        runtimeWiring.dataFetchers == [
                Query: [bookById: DataFetcherFactory.getBookDataFetcher()],
                Book: [author: DataFetcherFactory.getAuthorDataFetcher()]
        ]
    }
}
