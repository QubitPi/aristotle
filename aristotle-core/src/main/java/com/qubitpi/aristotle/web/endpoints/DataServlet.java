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
package com.qubitpi.aristotle.web.endpoints;

import static com.qubitpi.aristotle.web.ErrorMessageFormat.TOP_ID_NOT_FOUND;

import com.qubitpi.aristotle.web.TopSelectionFieldIdArgumentExtractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graphql.language.Document;
import graphql.language.NodeVisitor;
import graphql.parser.Parser;
import graphql.util.DefaultTraverserContext;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Endpoint for POSTing and GETing graph data.
 * <p>
 * This is the resource that serves GraphQL over HTTP. See
 * <a href="https://graphql.org/learn/serving-over-http/">GraphQL documentation</a> for specifications on serving
 * GraphQL over HTTP.
 */
@Singleton
@Path("/data/graphql")
public class DataServlet {

    private static final Logger LOG = LoggerFactory.getLogger(DataServlet.class);

    /**
     * Query graph data via GraphQL GET.
     *
     * @param graphqlQuery  A native GraphQL query operation definition, such as "query={user{name}}"
     *
     * @return native GraphQL query result
     *
     * @throws NullPointerException if {@code graphqlQuery} is {@code null}
     */
    public Response getData(@QueryParam("query") final String graphqlQuery) {
        Objects.requireNonNull(graphqlQuery);

        final Document document = Parser.parse(graphqlQuery);
        final String rootFieldId = getRootEntityId(document);

        return Response
                .status(Response.Status.OK)
                .entity(getGraphData(rootFieldId))
                .build();
    }

    /**
     * Extracts, from a specified {@link Document client GraphQL query}, the "id" argument value of the first selection
     * in top-level selection set.
     * <p>
     * For example, when document looks like
     * <pre>
     * {@code
     *     query {
     *         user(id: "2000") {
     *             name
     *         }
     *         location(id: "74543") {
     *             city
     *             zip
     *         }
     *     }
     * }
     * </pre>
     * then this method returns "2000"
     *
     * @param document  A programmable representation of the client's query, cannot be {@code null}
     *
     * @return the search key
     *
     * @throws IllegalArgumentException if {@code document} doesn't contain such "id"
     */
    @NotNull
    private static String getRootEntityId(@NotNull final Document document) {
        final NodeVisitor nodeVisitor = TopSelectionFieldIdArgumentExtractor.newInstance();

        document.accept(DefaultTraverserContext.simple(document), nodeVisitor);

        return ((TopSelectionFieldIdArgumentExtractor) nodeVisitor)
                .getArgumentValue()
                .orElseThrow(() -> {
                    LOG.error(TOP_ID_NOT_FOUND.logFormat(document));
                    return new IllegalArgumentException(TOP_ID_NOT_FOUND.format());
                });
    }

    /**
     * A simple abstraction layer for query delegation.
     *
     * @param rootFieldId  A search key
     *
     * @return a complete response graph
     */
    @NotNull
    private Object getGraphData(final String rootFieldId) {
        return null;
    }
}
