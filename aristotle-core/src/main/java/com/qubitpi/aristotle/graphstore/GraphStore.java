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
package com.qubitpi.aristotle.graphstore;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.constraints.NotNull;

/**
 * A {@link GraphStore} is an abstractin layer between servlet and graph data client.
 */
@FunctionalInterface
public interface GraphStore {

    /**
     * Query graph by top selection argument ID.
     *
     * @param id  top selection ID
     *
     * @return a complete graph ready to be returned to client as JSON
     *
     * @throws NullPointerException if {@code id} is {@code null}
     */
    @NotNull
    JsonNode getGraph(@NotNull String id);
}
