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

import graphql.language.Argument;
import graphql.language.Document;
import graphql.language.Field;
import graphql.language.Node;
import graphql.language.NodeVisitor;
import graphql.language.NodeVisitorStub;
import graphql.language.OperationDefinition;
import graphql.language.SelectionSet;
import graphql.language.StringValue;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import jakarta.validation.constraints.NotNull;
import net.jcip.annotations.NotThreadSafe;

import java.util.Optional;

/**
 * {@link TopSelectionFieldIdArgumentExtractor} is a visitor that, by the end of its visit of a {@link Document},
 * returns the first selection field argument value whose key is "id".
 * <p>
 * For example, when a {@link Document} below is visited
 * <pre>
 * {@code
 * query {
 *     getTask(id: "0x3") {
 *         id
 *         title
 *         completed
 *     }
 *     getAssignee(id: "EWGETBSDF") {
 *         name
 *         title
 *         department
 *     }
 * }
 * }
 * </pre>
 * The "0x3" would be picked up and will be available as the return value of {@link #getArgumentValue()}
 * <p>
 * Note that there are constraints where {@link TopSelectionFieldIdArgumentExtractor} can be applied:
 * <ul>
 *     <li> The argument must exist in the first definition in the definition sequence of the {@link Document} query and
 *          the first definition must be an {@link OperationDefinition}. For example, in the query above,
 *          {@code getTask} and {@code getAssignee} together compose the "first" and it is itself a
 *          {@link OperationDefinition}
 *     <li> The {@link OperationDefinition} above must be defined by a {@link SelectionSet} and the argument must exist
 *          in the first {@link graphql.language.Selection}. For example, the {@code getTask} would be the "first"
 *          selection that has the target argument
 *     <li> The target argument has "id" as argument key and value must be a string. For instance, the argument
 *          {@code id: "0x3"} of {@code getTask} field has the matching key and matching value type
 * </ul>
 */
@NotThreadSafe
public class TopSelectionFieldIdArgumentExtractor extends NodeVisitorStub {

    private String id;

    /**
     * Returns a new instance of fully initialized {@link TopSelectionFieldIdArgumentExtractor}.
     *
     * @return the new instance
     */
    @NotNull
    public static NodeVisitor newInstance() {
        return new TopSelectionFieldIdArgumentExtractor();
    }

    /**
     * Visits the first definition, which must be a {@link OperationDefinition}, in a specified {@link Document}.
     *
     * @param node  A {@link Document} with the first definition being an {@link OperationDefinition}, cannot be
     * {@code null}
     * @param context  A traversal context object used during visit
     *
     * @return not intend to be used
     */
    @Override
    public TraversalControl visitDocument(@NotNull final Document node, final TraverserContext<Node> context) {
        return visitOperationDefinition((OperationDefinition) node.getDefinitions().get(0), context);
    }

    /**
     * Visits the {@link SelectionSet}, which must exist, of a specified {@link OperationDefinition}.
     *
     * @param node  An {@link OperationDefinition} that is guaranteed to contain a non-null {@link SelectionSet}.
     * @param context  A traversal context object used during visit
     *
     * @return not intend to be used
     */
    @Override
    public TraversalControl visitOperationDefinition(
            @NotNull final OperationDefinition node,
            final TraverserContext<Node> context
    ) {
        return visitSelectionSet(node.getSelectionSet(), context);
    }

    /**
     * Visits the first {@link graphql.language.Selection}, which must be a {@link Field}, within a specified
     * {@link SelectionSet}.
     *
     * @param node  A {@link SelectionSet} with the first {@link graphql.language.Selection} being a {@link Field},
     * cannot be {@code null}
     * @param context  A traversal context object used during visit
     *
     * @return not intend to be used
     */
    @Override
    public TraversalControl visitSelectionSet(@NotNull final SelectionSet node, final TraverserContext<Node> context) {
        return visitField((Field) node.getSelections().get(0), context);
    }

    /**
     * Visits all {@link Argument}s of a specified {@link Field} and picks up the value of the argument, whose key is
     * "id", as the return value of {@link #getArgumentValue()}.
     * <p>
     * If no such argument exists, the {@link #getArgumentValue()} would return {@link Optional#empty()}
     *
     * @param node  A {@link Field} instance
     * @param context  A traversal context object used during visit
     *
     * @return not intend to be used
     */
    @Override
    public TraversalControl visitField(@NotNull final Field node, final TraverserContext<Node> context) {
        for (final Argument argument : node.getArguments()) {
            if (visitArgument(argument, context) == TraversalControl.QUIT) {
                break;
            }
        }

        return TraversalControl.QUIT;
    }

    /**
     * Visits an {@link Argument} and, if the argument key is "id", picks up the value of that argument as a string
     * value and make it as the return value of {@link #getArgumentValue()}.
     *
     * @param node  An {@link Argument} instance
     * @param context  A traversal context object used during visit
     *
     * @return a signal to stop the whole visit if the argument key is "id" or a signal to continue, otherwise
     */
    @NotNull
    @Override
    public TraversalControl visitArgument(@NotNull final Argument node, final TraverserContext<Node> context) {
        if ("id".equals(node.getName())) {
            visitStringValue((StringValue) node.getValue(), context);
            return TraversalControl.QUIT;
        }

        return TraversalControl.CONTINUE;
    }

    /**
     * Visits a specified {@link StringValue} node and always assign the value to the target argument value, i.e. the
     * return value of {@link #getArgumentValue()}.
     *
     * @param node  A {@link StringValue} instance
     * @param context  A traversal context object used during visit
     *
     * @return not intend to be used
     */
    @NotNull
    @Override
    public TraversalControl visitStringValue(@NotNull final StringValue node, final TraverserContext<Node> context) {
        id = node.getValue();
        return TraversalControl.QUIT;
    }

    /**
     * Returns the visit result of this visitor, i.e. the top selection field id argument value.
     *
     * @return the id argument value or empty if not exists
     */
    @NotNull
    public Optional<String> getArgumentValue() {
        return id == null ? Optional.empty() : Optional.of(id);
    }
}
