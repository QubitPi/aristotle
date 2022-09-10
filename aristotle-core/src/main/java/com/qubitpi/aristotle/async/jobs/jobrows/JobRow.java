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

import static com.qubitpi.aristotle.web.ErrorMessageFormat.MISSING_JOB_ID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ws.rs.core.Link;

/**
 * A row in the conceptual table defined by the ApiJobStore, containing the metadata about a particular job.
 * <p>
 * The JSON serialization of its object is given by {@link #getJobMetadata()}
 */
@Immutable
@ThreadSafe
public class JobRow {

    private static final Logger LOG = LoggerFactory.getLogger(JobRow.class);

    private final String jobId;
    private final Map<JobField, String> jobMetadata;

    /**
     * An internal-only all-args constructor.
     *
     * @param jobId  An unique identifier for this job
     * @param jobMetadata  The metadata about this job; The key order of this map also defines the JSON serialization
     * key order
     */
    protected JobRow(final @NotNull String jobId, final @NotNull Map<JobField, String> jobMetadata) {
        this.jobId = jobId;

        if (this.jobId == null) {
            LOG.error(MISSING_JOB_ID.logFormat(jobMetadata));
            throw new IllegalArgumentException(MISSING_JOB_ID.format());
        }

        this.jobMetadata = Objects.requireNonNull(jobMetadata);
    }

    /**
     *
     * @param jobIdFieldName
     * @param jobMetadata  The metadata about this job; The key order of this map also defines the JSON serialization
     * key order
     */
    public JobRow(final @NotNull JobField jobIdFieldName, final @NotNull Map<JobField, String> jobMetadata) {
        this(jobMetadata.get(jobIdFieldName), jobMetadata);
    }

    /**
     * Coerces the JobRow into a mapping from the names of JobFields to their values.
     *
     * @return a mapping from the name of ech JobField to its associated value
     */
    @NotNull
    @JsonValue
    public Map<String, String> getRowMap() {
        return getJobMetadata()
                .entrySet()
                .stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toMap(
                                        entry -> entry.getKey().getName(),
                                        Map.Entry::getValue,
                                        (first, second) -> first,
                                        LinkedHashMap::new
                                ),
                                Collections::unmodifiableMap
                        )
                );
    }

    @NotNull
    public String getId() {
        return jobId;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final JobRow that = (JobRow) other;
        return Objects.equals(jobId, that.jobId) && Objects.equals(jobMetadata, that.jobMetadata);
    }

    /**
     * The hash code of a job row is simply the hash code of its {@link #getId() ID}.
     *
     * @return the hash code of the job row
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return String.join("'", "{jobId=", getId(), ", entries=", getJobMetadata().toString(), "}");
    }

    @NotNull
    private Map<JobField, String> getJobMetadata() {
        return jobMetadata;
    }
}
