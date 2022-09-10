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
package com.qubitpi.aristotle.async.jobs.jobrows

import static com.qubitpi.aristotle.async.jobs.jobrows.DefaultJobField.DATE_CREATED
import static com.qubitpi.aristotle.async.jobs.jobrows.DefaultJobField.DATE_UPDATED
import static com.qubitpi.aristotle.async.jobs.jobrows.DefaultJobField.JOB_TICKET
import static com.qubitpi.aristotle.async.jobs.jobrows.DefaultJobField.QUERY
import static com.qubitpi.aristotle.async.jobs.jobrows.DefaultJobField.STATUS
import static com.qubitpi.aristotle.async.jobs.jobrows.DefaultJobField.USER_ID

import org.codehaus.groovy.runtime.ScriptTestAdapter

import spock.lang.Specification

import java.security.Principal
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.core.SecurityContext
import javax.ws.rs.core.UriInfo

class DefaultJobRowBuilderSpec extends Specification {

    static final String USER_NAME = "greg"
    static final String UUID = "this_id_is_unique_just_like_everybody_else"
    static final String QUERY_STRING =
            "https://host:4443/v1/data?query=%7BmetaData%28fileId%3A%221%22%29%7BfileName%7D%7D"

    def "A DefaultJobRow is built correctly"() {
        given: "UriInfo and RequestContext that will be used to populate the jobMetadata"
        UriInfo request = Stub(UriInfo) {
            getRequestUri() >> new URI(QUERY_STRING)
        }
        Instant testTime = Instant.now()
        ContainerRequestContext requestContext = Stub(ContainerRequestContext) {
            getSecurityContext() >> Stub(SecurityContext) {
                getUserPrincipal() >> Stub(Principal) {
                    getName() >> USER_NAME
                }
            }
        }

        when: "We build the job jobMetadata"
        Map<JobField, String> jobMetadata = new DefaultJobRowBuilder(
                Clock.fixed(testTime, ZoneId.systemDefault()),
                {USER_NAME},
                {it[USER_ID] + UUID}
        )
                .build(request, requestContext)
                .getJobMetadata()

        then: "the JobRow is built correctly"
        jobMetadata.get(QUERY) == QUERY_STRING
        jobMetadata.get(STATUS) == DefaultJobStatus.PENDING.name
        jobMetadata.get(USER_ID) == USER_NAME
        jobMetadata.get(DATE_CREATED) == ZonedDateTime.ofInstant(testTime, ZoneId.systemDefault()).toString()
        jobMetadata.get(DATE_UPDATED) == jobMetadata.get(DATE_UPDATED)
        jobMetadata.get(JOB_TICKET) == USER_NAME + UUID
    }
}
