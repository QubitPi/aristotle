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
package com.qubitpi.aristotle.graphql.query;

import org.bitbucket.jack20191124.graphql.query.elements.Argument;
import org.bitbucket.jack20191124.graphql.query.elements.Arguments;
import org.bitbucket.jack20191124.graphql.query.elements.Definition;
import org.bitbucket.jack20191124.graphql.query.elements.Document;
import org.bitbucket.jack20191124.graphql.query.elements.Edges;
import org.bitbucket.jack20191124.graphql.query.elements.EnumValue;
import org.bitbucket.jack20191124.graphql.query.elements.Node;
import org.bitbucket.jack20191124.graphql.query.elements.ObjectField;
import org.bitbucket.jack20191124.graphql.query.elements.ObjectValueWithVariable;
import org.bitbucket.jack20191124.graphql.query.elements.OperationDefinition;
import org.bitbucket.jack20191124.graphql.query.elements.ScalarField;
import org.bitbucket.jack20191124.graphql.query.elements.Selection;
import org.bitbucket.jack20191124.graphql.query.elements.SelectionSet;
import org.bitbucket.jack20191124.graphql.query.elements.StringValue;
import org.bitbucket.jack20191124.graphql.query.elements.TypedOperation;
import org.bitbucket.jack20191124.graphql.query.elements.ValueWithVariable;
import org.bitbucket.jack20191124.graphql.query.elements.VariableDefinition;
import org.bitbucket.jack20191124.graphql.query.elements.VariableDefinitions;
import org.bitbucket.jack20191124.graphql.query.elements.VariableValue;
import org.bitbucket.jack20191124.graphql.test.relayjsonapi.RelayJsonApiDsl;

/**
 * {@link GraphQlDsl} programmatically construct GraphQL query(excluding query variables) and serializes it to a GraphQL
 * query string.
 * <p>
 * For example,
 * <pre>
 * {@code
 * String graphQLQuery = document(
 *         typedOperation(
 *                 TypedOperation.OperationType.QUERY,
 *                 "myQuery",
 *                 variableDefinitions(
 *                         variableDefinition("bookId", "[String]")
 *                 ),
 *                 selections(
 *                         entity(
 *                                 "book",
 *                                 arguments(
 *                                         argument("ids", variableValue("bookId"))
 *                                 ),
 *                                 selections(
 *                                         field("id"),
 *                                         field("title"),
 *                                         field(
 *                                                 "authors",
 *                                                 selection(
 *                                                         field("name")
 *                                                 )
 *                                         )
 *                                 )
 *                         )
 *                 )
 *         )
 * ).toQuery();
 * }
 * </pre>
 * will produces the following query
 * <pre>
 * {@code
 * query myQuery($bookId: [String]) {
 *     book(ids: $bookId) {
 *         edges {
 *             node {
 *                 id
 *                 title
 *                 authors {
 *                     edges {
 *                         node {
 *                             name
 *                         }
 *                     }
 *                 }
 *             }
 *         }
 *     }
 * }
 * }
 * </pre>
 * Note that the query follows Relay's connection pattern. Caller should use {@link GraphQlDsl} to construct test
 * GraphQL query and use {@link RelayJsonApiDsl} to verify the query results.
 *
 * @see <a href="https://graphql.org/learn/pagination/#pagination-and-edges">Relay's connectin pattern</a>
 *
 * @deprecated Use {@link org.bitbucket.jack20191124.graphql.query.elements} package instead. {@link GraphQlDsl}
 * is not sufficiently tested and is no longer being maintained.
 */
@Deprecated
public final class GraphQlDsl {

    /**
     * Constructor.
     * <p>
     * Suppress default constructor for noninstantiability.
     */
    private GraphQlDsl() {
        throw new AssertionError();
    }

    /**
     * Creates a GraphQL query.
     *
     * @param definitions  An variable-lengthed array of {@link Definition}s that composes a complete GraphQL query.
     *
     * @return a serializable GraphQL query object
     */
    public static Document document(final Definition... definitions) {
        return Document.of(definitions);
    }

    /**
     * Creates a single root-entity selection spec.
     *
     * @param selection  The single selection spec object
     *
     * @return a definition of a multi-definitions GraphQL query
     *
     * @see <a href="https://graphql.org/learn/queries/#fields">Field Selection</a>
     */
    public static SelectionSet selection(final Selection selection) {
        return SelectionSet.of(selection);
    }

    /**
     * Creates a multi-root-entities selections spec.
     *
     * @param selections  An variable-sized array of selection spec objects
     *
     * @return a definition of a multi-definitions GraphQL query
     *
     * @see <a href="https://graphql.org/learn/queries/#fields">Field Selection</a>
     */
    public static SelectionSet selections(final Selection... selections) {
        return SelectionSet.of(selections);
    }

    /**
     * Creates a typed query definition without query name and variable definitions.
     *
     * @param operationType  The type of the query definition.
     * @param selectionSet  The definition/selection-spec of a multi-definitions GraphQL query for
     *
     * @return a definition of a multi-definitions GraphQL query
     *
     * @see <a href="https://graphql.org/learn/schema/#the-query-and-mutation-types">Typed Query</a>
     */
    public static TypedOperation typedOperation(
            final TypedOperation.OperationType operationType,
            final SelectionSet selectionSet
    ) {
        return TypedOperation.of(operationType, selectionSet);
    }

    /**
     * Creates a typed query definition.
     *
     * @param operationType  The type of the query definition.
     * @param name  An unique identifier of a {@link OperationDefinition operation} in a multi-operations
     * {@link Document GraphQL query}
     * @param variableDefinitions  A collection of GraphQL variable definitions
     * @param selectionSet  The definition/selection-spec of a multi-definitions GraphQL query for
     *
     * @return a definition of a multi-definitions GraphQL query
     *
     * @see <a href="https://graphql.org/learn/schema/#the-query-and-mutation-types">Typed Query</a>
     * @see <a href="https://graphql.org/learn/queries/#variables">Variables</a>
     */
    public static TypedOperation typedOperation(
            final TypedOperation.OperationType operationType,
            final String name,
            final VariableDefinitions variableDefinitions,
            final SelectionSet selectionSet
    ) {
        return TypedOperation.of(operationType, name, variableDefinitions, selectionSet);
    }

    /**
     * Creates a collection of GraphQL variable definitions.
     *
     * @param variableDefinitions  A variable-sized array of {@link VariableDefinition}s
     *
     * @return A collection of GraphQL variable definitions
     *
     * @see <a href="https://graphql.org/learn/queries/#variables">Variables</a>
     */
    public static VariableDefinitions variableDefinitions(final VariableDefinition... variableDefinitions) {
        return VariableDefinitions.of(variableDefinitions);
    }

    /**
     * Creates a single GraphQL variable definition without default value.
     *
     * @param variable  A string (without "$" sign) representing the name of the variable
     * @param type  A simplified String representation of the aforementioned type literal.
     *
     * @return A single GraphQL variable definition
     *
     * @see <a href="https://graphql.org/learn/queries/#variables">Variables</a>
     */
    public static VariableDefinition variableDefinition(final String variable, final String type) {
        return VariableDefinition.of(variable, type);
    }

    /**
     * Creates a single top-level entity(object field) selection without {@link Argument}s.
     *
     * @param name  The name of the selected entity/field that would appear in a GraphQL query
     * @param selectionSet  The fields of the entity that are selected
     *
     * @return a top-level selection
     *
     * @see <a href="https://graphql.org/learn/queries/#fields">Fields</a>
     * @see <a href="https://graphql.org/learn/schema/#object-types-and-fields">Object Types and Fields</a>
     */
    public static Selection entity(final String name, final SelectionSet selectionSet) {
        return ObjectField.withoutArguments(name, relayWrap(selectionSet));
    }

    /**
     * Creates a top-level single entity(object field) selection.
     *
     * @param name  The name of the selected entity/field that would appear in a GraphQL query
     * @param arguments  The {@link Argument}s that would be applied to the selected entity
     * @param selectionSet  The fields of the entity that are selected
     *
     * @return a top-level selection
     *
     * @see <a href="https://graphql.org/learn/queries/#fields">Fields</a>
     * @see <a href="https://graphql.org/learn/queries/#arguments">Arguments</a>
     * @see <a href="https://graphql.org/learn/schema/#object-types-and-fields">Object Types and Fields</a>
     */
    public static Selection entity(final String name, final Arguments arguments, final SelectionSet selectionSet) {
        return ObjectField.of(name, arguments, relayWrap(selectionSet));
    }

    /**
     * Creates a entity(object field) selection without {@link Argument}s.
     *
     * @param name  The name of the selected entity/field that would appear in a GraphQL query
     * @param selectionSet  The fields of the entity that are selected
     *
     * @return a top-level selection
     *
     * @see <a href="https://graphql.org/learn/queries/#fields">Fields</a>
     * @see <a href="https://graphql.org/learn/schema/#object-types-and-fields">Object Types and Fields</a>
     */
    public static Selection field(final String name, final SelectionSet selectionSet) {
        return entity(name, selectionSet);
    }

    /**
     * Creates a single entity(object field) selection.
     *
     * @param name  The name of the selected entity/field that would appear in a GraphQL query
     * @param arguments  The {@link Argument}s that would be applied to the selected entity
     * @param selectionSet  The fields of the entity that are selected
     *
     * @return a top-level selection
     *
     * @see <a href="https://graphql.org/learn/queries/#fields">Fields</a>
     * @see <a href="https://graphql.org/learn/queries/#arguments">Arguments</a>
     * @see <a href="https://graphql.org/learn/schema/#object-types-and-fields">Object Types and Fields</a>
     */
    public static Selection field(final String name, final Arguments arguments, final SelectionSet selectionSet) {
        return entity(name, arguments, selectionSet);
    }

    /**
     * Creates an attribute(scalar field) selection.
     *
     * @param name  The name of the selected attribute that would appear in a GraphQL query.
     *
     * @return a field that represents an non-reltionship entity attribute
     *
     * @see <a href="https://graphql.org/learn/schema/#scalar-types">Scalar Field</a>
     */
    public static Selection field(final String name) {
        return ScalarField.of(name);
    }

    /**
     * Creates a complete set of {@link Argument}s that is passed to a object field selection.
     *
     * @param arguments  A variable-sized group of {@link Argument}s passed to the object field selection
     *
     * @return a complete specification of selection arguments
     *
     * @see <a href="https://graphql.org/learn/queries/#arguments">Arguments</a>
     */
    public static Arguments arguments(final Argument... arguments) {
        return Arguments.of(arguments);
    }

    /**
     * Creates a {@link Argument single argument} that is passed to a object field selection.
     *
     * @param argument  The single {@link Argument} passed to the object field selection
     *
     * @return a complete specification of selection argument
     *
     * @see <a href="https://graphql.org/learn/queries/#arguments">Arguments</a>
     */
    public static Arguments argument(final Argument argument) {
        return Arguments.of(argument);
    }

    /**
     * Creates a spec object of an GraphQL {@link Argument argument}.
     *
     * @param name  The name of the argument
     * @param value  An object that models argument value.
     *
     * @return a complete specification of selection argument
     *
     * @see <a href="https://graphql.org/learn/queries/#arguments">Arguments</a>
     */
    public static Argument argument(final String name, final ValueWithVariable value) {
        return Argument.of(name, value);
    }

    /**
     * Creates a string value that is quoted in its serialized form.
     *
     * @param value  The chars surrounded in a pair of double-quotes
     *
     * @return a GraphQL spec
     */
    public static ValueWithVariable stringValue(final String value) {
        return StringValue.of(value);
    }

    /**
     * Creates a query spec that represents a variable, whose variable name follows a {@code $} sign.
     * <p>
     * Foe example, {@code $episode}. In this case "episode" is the variable name
     *
     * @param name  A GraphQL variable literal without {@code $} sign
     *
     * @return a GraphQL variable spec
     *
     * @see <a href="https://graphql.org/learn/queries/#variables">Variables</a>
     */
    public static ValueWithVariable variableValue(final String name) {
        return VariableValue.of(name);
    }

    /**
     * Creates a query spec that represents a object value.
     * <p>
     * For example, "id:\"1\", title:\"update title\"" as in "{ id:\"1\", title:\"update title\" }".
     *
     * @param object  A string representation of the object wrapped
     *
     * @return a GraphQL spec
     */
    public static ValueWithVariable objectValueWithVariable(final String object) {
        return ObjectValueWithVariable.of(object);
    }

    /**
     * Creates a string value that is NOT quoted in its serialized form.
     *
     * @param enumValue  The same string in the serialized form of the created object.
     *
     * @return a GraphQL spec
     */
    public static ValueWithVariable enumValue(final String enumValue) {
        return EnumValue.of(enumValue);
    }

    /**
     * Wraps a set of field selections inside a Relay connection pattern spec.
     * <p>
     * For example,
     * <pre>
     * {@code
     * book(ids: $bookId) {
     *     id
     *     title
     *     authors {
     *         name
     *     }
     * }
     * }
     * </pre>
     * becomes
     * <pre>
     * {@code
     * book(ids: $bookId) {
     *     edges {
     *         node {
     *             id
     *             title
     *             authors {
     *                 edges {
     *                     node {
     *                         name
     *                     }
     *                 }
     *             }
     *         }
     *     }
     * }
     * }
     * </pre>
     *
     * @param selectionSet  The field selections to be wrapped
     *
     * @return the same GraphQL spec in Relay's annotation
     *
     * @see <a href="https://graphql.org/learn/pagination/">Relay's connection pattern</a>
     */
    private static SelectionSet relayWrap(final SelectionSet selectionSet) {
        final Selection node = Node.of(selectionSet);
        final Selection edges = Edges.to((Node) node);

        return SelectionSet.of(edges);
    }
}
