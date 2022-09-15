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

import jakarta.validation.constraints.NotNull;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.Locale;
import java.util.Objects;

/**
 * Provides an enumeration of the standard job statuses that Aristotle supports.
 */
@Immutable
@ThreadSafe
public enum DefaultJobStatus implements JobStatus {

    /**
     * The job is in progress.
     */
    PENDING("This job is in progress."),

    /**
     * The job has completed successfully.
     */
    SUCCESS("The job has completed successfully."),

    /**
     * The job has been canceled before it could be completed.
     */
    CANCELED("The job has been canceled before it could be completed."),

    /**
     * An error occurred and the job failed to complete.
     */
    FAILURE("An error occurred and the job failed to complete.");

    private final String name;
    private final String description;

    /**
     * Constructor.
     *
     * @param description  Description of the job status
     *
     * @throws NullPointerException if {@code description} is {@code null}
     */
    DefaultJobStatus(final @NotNull String description) {
        this.name = name().toLowerCase(Locale.ENGLISH);
        this.description = Objects.requireNonNull(description);
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @NotNull
    @Override
    public String getDescription() {
        return description;
    }
}
