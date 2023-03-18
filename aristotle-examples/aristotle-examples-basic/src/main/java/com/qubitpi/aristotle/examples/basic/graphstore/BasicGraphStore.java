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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qubitpi.aristotle.graphstore.GraphStore;

import graphql.GraphQL;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * {@link BasicGraphStore} shows that to have a full-fledged knowledge-graph service running, we only need to implement
 * our own {@link GraphStore} to make it running.
 */
public class BasicGraphStore implements GraphStore {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private final GraphQL api;

    /**
     * Constructor used for dependency injection.
     */
    @Inject
    public BasicGraphStore() {
        this.api = GraphQLFactory.getInstance();
    }

    /**
     * All-args constructor.
     *
     * @param api  The native GraphQL API object to delegate queries for.
     *
     * @throws NullPointerException if {@code  api} is {@code null}
     */
    private BasicGraphStore(@NotNull final GraphQL api) {
        this.api = Objects.requireNonNull(api, "api");
    }

    @Override
    public JsonNode query(@NotNull final String nativeQuery) {
        return JSON_MAPPER.convertValue(
                getApi()
                        .execute(Objects.requireNonNull(nativeQuery, "nativeQuery"))
                        .toSpecification(),
                JsonNode.class
        );
    }

    @NotNull
    private GraphQL getApi() {
        return api;
    }
}
