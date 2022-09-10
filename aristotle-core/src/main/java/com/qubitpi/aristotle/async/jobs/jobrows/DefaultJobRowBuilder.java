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

import static java.util.AbstractMap.SimpleImmutableEntry;

import jakarta.validation.constraints.NotNull;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * A {@link JobRowBuilder} that populates a JobRow with the information for all the job fields defined in
 * {@link DefaultJobField}.
 */
public class DefaultJobRowBuilder implements JobRowBuilder {

    private final Clock timestampGenerator;
    private final Function<SecurityContext, String> userIdExtractor;
    private final Function<Map<JobField, String>, String> idGenerator;

    /**
     * An internal all-args constructor that builds a factor for generating JobRows with a custom function for
     * extracting user ID from a request's {@link SecurityContext}.
     *
     * @param timestampGenerator  The clock to use to generate timestamps
     * @param userIdExtractor  A function that, given a {@link SecurityContext}, returns the ID of the user who made the
     * request
     * @param idGenerator  A function that takes all the job metadata except for the job's ID, and returns a globally
     * unique ID for the job
     */
    protected DefaultJobRowBuilder(
            final Clock timestampGenerator,
            final Function<SecurityContext, String> userIdExtractor,
            final Function<Map<JobField, String>, String> idGenerator
    ) {
        this.idGenerator = idGenerator;
        this.userIdExtractor = userIdExtractor;
        this.timestampGenerator = timestampGenerator;
    }

    /**
     * Builds a factory for generating JobRows containing values for every {@link DefaultJobField}.
     * <p>
     * The {@link DefaultJobField#USER_ID user ID} is extracted from the
     * {@link SecurityContext#getUserPrincipal() UserPrincipal} and timestamps are generated from the System clock.
     *
     * @param idGenerator  A function that takes all the job metadata except for the job's ID, and returns a globally
     * unique ID for the job
     */
    public DefaultJobRowBuilder(final Function<Map<JobField, String>, String> idGenerator) {
        this(Clock.systemDefaultZone(), securityContext -> securityContext.getUserPrincipal().getName(), idGenerator);
    }

    /**
     * Builds a JobRow with values for every field defined in {@link DefaultJobField}.
     *
     * @param request   The request that is triggering this job
     * @param requestContext  The context of the request triggering this job
     *
     * @return A JobRow with all the metadata for the job under construction
     */
    @Override
    public JobRow build(final UriInfo request, final ContainerRequestContext requestContext) {
        final String isoDateCreated = ZonedDateTime.ofInstant(
                        getTimestampGenerator().instant(),
                        getTimestampGenerator().getZone()
                ).toString();

        final Map<JobField, String> jobMetadata = Stream.of(
                new SimpleImmutableEntry<>(DefaultJobField.QUERY, request.getRequestUri().toString()),
                new SimpleImmutableEntry<>(DefaultJobField.STATUS, DefaultJobStatus.PENDING.getName()),
                new SimpleImmutableEntry<>(DefaultJobField.DATE_CREATED, isoDateCreated),
                new SimpleImmutableEntry<>(DefaultJobField.DATE_UPDATED, isoDateCreated),
                new SimpleImmutableEntry<>(
                        DefaultJobField.USER_ID,
                        getUserIdExtractor().apply(requestContext.getSecurityContext())
                )
        )
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue
                                ),
                                LinkedHashMap::new
                        )
                );

        jobMetadata.put(DefaultJobField.JOB_TICKET, getIdGenerator().apply(jobMetadata));
        return new JobRow(DefaultJobField.JOB_TICKET, jobMetadata);
    }

    @NotNull
    private Function<Map<JobField, String>, String> getIdGenerator() {
        return idGenerator;
    }

    @NotNull
    private Function<SecurityContext, String> getUserIdExtractor() {
        return userIdExtractor;
    }

    @NotNull
    private Clock getTimestampGenerator() {
        return timestampGenerator;
    }
}
