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

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Common message formats for errors.
 */
public enum ErrorMessageFormat {

    /**
     * When a client request does not have a top-level selection "id" argument.
     *
     * For example, the query below does have a top ID argument of "2000"
     * {@code
     *     query {
     *         user(id: "2000") {
     *             name
     *         }
     *         location {
     *             city
     *             zip
     *         }
     *     }
     * }
     * whereas
     * {@code
     *     query {
     *         user {
     *             name
     *         }
     *         location {
     *             city
     *             zip
     *         }
     *     }
     * }
     * does not
     */
    TOP_ID_NOT_FOUND(
            "Invalid GraphQL query. An 'id' argument with string value must exist in the first top selection",
            "Visitor doesn't find target 'id' from '%s'"
    )
    ;

    private final String messageFormat;
    private final String loggingFormat;

    /**
     * An error message formatter with the same message for logging and messaging.
     *
     * @param messageFormat  The format string for logging and messaging
     *
     * @throws NullPointerException if any {@code messageFormat} is {@code null}
     */
    ErrorMessageFormat(final @NotNull String messageFormat) {
        this(messageFormat, messageFormat);
    }

    /**
     * Constructor.
     *
     * @param messageFormat  User facing message format
     * @param loggingFormat  Server log message format
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    ErrorMessageFormat(final @NotNull String messageFormat, final @NotNull String loggingFormat) {
        this.messageFormat = Objects.requireNonNull(messageFormat);
        this.loggingFormat = Objects.requireNonNull(loggingFormat);
    }

    /**
     * Formats a message for reporting to a user/client.
     *
     * @param values  The values to populate the format string
     *
     * @return the use message
     */
    @NotNull
    public String format(final Object... values) {
        return String.format(getMessageFormat(), values);
    }

    /**
     * Formats a message for writing to the log.
     *
     * @param values  The values to populate the format string
     *
     * @return the logging message
     */
    @NotNull
    public String logFormat(final Object... values) {
        return String.format(getLoggingFormat(), values);
    }

    @NotNull
    private String getMessageFormat() {
        return messageFormat;
    }

    @NotNull
    private String getLoggingFormat() {
        return loggingFormat;
    }
}
