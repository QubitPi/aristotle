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
package com.qubitpi.aristotle.examples.basic.graphstore;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.validation.constraints.NotNull;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

/**
 * {@link GraphQLFactory} produces native GraphQL API.
 */
@Immutable
@ThreadSafe
public class GraphQLFactory {

    private static final Logger LOG = LoggerFactory.getLogger(GraphQLFactory.class);

    private static final GraphQL API = init();

    /**
     * Returns a fully initialized GraphQL API instance ready for querying.
     *
     * @return the same instance
     */
    @NotNull
    public static GraphQL getInstance() {
        return API;
    }

    /**
     * Constructs a fully initialized GraphQL API instance ready for querying.
     *
     * @return a new instance
     */
    private static GraphQL init() {
        final String schemaString = getGraphQLSchemaResourceAsString("schema.graphqls");
        final GraphQLSchema graphQLSchema = buildSchema(schemaString);
        return GraphQL.newGraphQL(graphQLSchema).build();
    }

    /**
     * Loads a resource file into a single {@code String} object.
     *
     * @param resourceName cannot be null
     *
     * @return a resource file content
     *
     * @throws NullPointerException if {@code resourceName} is {@code null}
     */
    @NotNull
    private static String getGraphQLSchemaResourceAsString(@NotNull final String resourceName) {
        final InputStream resourceStream = Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream(Objects.requireNonNull(resourceName));

        if (resourceStream == null) {
            final String message = String.format("GraphQL schema file not found: '%s'", resourceName);
            LOG.error(message);
            throw new IllegalStateException(message);
        }

        final Scanner scanner = new Scanner(resourceStream).useDelimiter("\\A");

        return scanner.hasNext() ? scanner.next() : "";
    }

    /**
     * Given a GraphQL schema all in one string, this method transforms it into a
     * {@link GraphQLSchema programmable schema object}.
     *
     * @param schemaString  The provided GraphQL schema in one string
     *
     * @return a new object equivalent to the schema string
     */
    private static GraphQLSchema buildSchema(final String schemaString) {
        final TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaString);
        final RuntimeWiring runtimeWiring = buildWiring();
        final SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }

    /**
     * Combines all data fetchers to form the GraphQL runtime specification for basic application.
     *
     * @return a new {@link RuntimeWiring} instance
     */
    private static RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(
                        newTypeWiring("Query")
                                .dataFetcher("bookById", DataFetcherFactory.getBookDataFetcher())
                )
                .type(
                        newTypeWiring("Book")
                                .dataFetcher("author", DataFetcherFactory.getAuthorDataFetcher())
                )
                .build();
    }
}
