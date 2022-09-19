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
package com.qubitpi.aristotle.async.jobs.jobrows;

import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.Objects;

/**
 * The default fields for job metadata.
 */
@Immutable
@ThreadSafe
public enum DefaultJobField implements JobField {

    /**
     * The data query this job is satisfying.
     */
    QUERY("query", "The data query this job is satisfying."),

    /**
     * The current status of the job.
     */
    STATUS("status", "The current status of the job."),

    /**
     * The job's unique identifier.
     */
    JOB_TICKET("jobTicket", "The job's unique identifier."),

    /**
     * The ID of the user that created this job.
     */
    USER_ID("userId", "The ID of the user that created this job."),

    /**
     * The date the job was created.
     */
    DATE_CREATED("dateCreated", "The date the job was created."),

    /**
     * The date the job was last updated.
     */
    DATE_UPDATED("dateUpdated", "The date the job was last updated.");

    private final String serializedName;
    private final String description;

    /**
     * Constructor.
     *
     * @param serializedName  JSON key name for this JobField value.
     * @param description  Description of the job field
     *
     * @throws NullPointerException if {@code serializedName} or {@code  description} is {@code null}
     */
    DefaultJobField(final @NotNull String serializedName, final @NotNull String description) {
        this.serializedName = Objects.requireNonNull(serializedName);
        this.description = Objects.requireNonNull(description);
    }

    @Override
    @JsonValue
    public String getName() {
        return serializedName;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
