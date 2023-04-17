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

import com.qubitpi.aristotle.graphstore.GraphStore;
import com.qubitpi.aristotle.web.TopSelectionFieldIdArgumentExtractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graphql.language.Document;
import graphql.language.NodeVisitor;
import graphql.parser.Parser;
import graphql.util.DefaultTraverserContext;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.Objects;

/**
 * Endpoint for POSTing and GETing graph data.
 * <p>
 * This is the resource that serves GraphQL over HTTP. See
 * <a href="https://qubitpi.github.io/graphql.github.io/learn/serving-over-http/">GraphQL documentation</a> for
 * specifications on serving GraphQL over HTTP.
 */
@Singleton
@Immutable
@ThreadSafe
@Path("/data/graphql")
@Produces(MediaType.APPLICATION_JSON)
public class DataServlet {

    private static final Logger LOG = LoggerFactory.getLogger(DataServlet.class);

    private final GraphStore graphStore;

    /**
     * Constructor for dependency injection.
     *
     * @param graphStore  An abstraction layer that handles all GraphQL queries
     *
     * @throws NullPointerException if {@code graphStore} is {@code null}
     */
    @Inject
    public DataServlet(@NotNull final GraphStore graphStore) {
        this.graphStore = Objects.requireNonNull(graphStore);
    }

    /**
     * The standard HTTP GET method that handles GraphQL queries.
     * <p>
     * See <a href="https://qubitpi.github.io/graphql.github.io/learn/serving-over-http/">GraphQL documentation</a> for
     * specifications on serving GraphQL over HTTP.
     *
     * @param graphqlQuery  A native GraphQL query operation definition, such as "query={user{name}}"
     *
     * @return native GraphQL query result
     *
     * @throws NullPointerException if {@code graphqlQuery} is {@code null}
     */
    @GET
    @NotNull
    public Response getData(@QueryParam("query") final String graphqlQuery) {
        return Response
                .status(Response.Status.OK)
                .entity(getGraphStore().query(Objects.requireNonNull(graphqlQuery)))
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
     * @param document  A programmable representation of the client's query, cannot be {@code null}. It is usually
     * obtained by {@link Parser#parse(String)}
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

    @NotNull
    private GraphStore getGraphStore() {
        return graphStore;
    }
}
