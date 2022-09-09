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
package com.qubitpi.aristotle.web;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.constraints.NotNull;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.Objects;

/**
 * {@link PreResponse} is an encapsulation of graph data, which can be extarctd from {@link #getGraph()}, ready to be
 * sent back to client.
 * <p>
 * {@link PreResponse} is agnostic of the structure of a graph. It simply treats a graph as a serializable JSON object.
 * This is to abstract {@link PreResponse} away from the internal design of graph modeling.
 */
@Immutable
@ThreadSafe
public class PreResponse {

    private final JsonNode graph;
    private final ResponseContext responseContext;

    /**
     * Constructor.
     *
     * @param graph  Graph data associated with a response
     * @param responseContext  Response context associated with a response
     *
     * @throws NullPointerException if {@code graph} or {@code responseContext} is {@code null}
     */
    public PreResponse(final @NotNull JsonNode graph, final @NotNull ResponseContext responseContext) {
        this.graph = Objects.requireNonNull(graph);
        this.responseContext = Objects.requireNonNull(responseContext);
    }

    @NotNull
    public JsonNode getGraph() {
        return graph;
    }

    @NotNull
    public ResponseContext getResponseContext() {
        return responseContext;
    }
}
