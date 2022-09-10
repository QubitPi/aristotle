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

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

import groovy.json.JsonSlurper
import spock.lang.Specification
import spock.lang.Unroll

import java.time.ZonedDateTime

class JobRowSpec extends Specification {

    static final ObjectMapper JSON_MAPPER = new ObjectMapper()

    static final String JOB_TICKET_DATA = "123"
    static final String QUERY_DATA = "https://host:port/v1/data?query='...'"
    static final String STATUS_DATA = DefaultJobStatus.PENDING.getName()
    static final String DATE_CREATED_DATA = ZonedDateTime.now().toString()
    static final String DATE_UPDATED_DATA = DATE_CREATED_DATA
    static final String USER_ID_DATA = "A man with a plan"

    @SuppressWarnings('GroovyResultOfObjectAllocationIgnored')
    def "The JobRow constructor throws an IllegalArgumentException if the jobMetaData map does not contain the key field"() {
        when: "we construct a JobRow with a map without the ID field"
        new JobRow(JOB_TICKET, [(DefaultJobField.STATUS): DefaultJobStatus.PENDING.getName()])

        then: "an exception is thrown"
        thrown(IllegalArgumentException)
    }

    def "getRowMap returns a correct representation of the row as a map with string as keys instead of JobFields"() {
        given: "a JobRow with some data"
        JobRow jobRow = buildJobRow()

        and: "the expected map"
        Map<String, String> expectedMap = [
                jobTicket: JOB_TICKET_DATA,
                query: QUERY_DATA,
                status: STATUS_DATA,
                dateCreated: DATE_CREATED_DATA,
                dateUpdated: DATE_UPDATED_DATA,
                userId: USER_ID_DATA
        ]

        expect:
        jobRow.getRowMap() == expectedMap
    }

    def "A RowMap serializes correctly"() {
        given: "a JobRow with all the standard fields"
        JobRow row = buildJobRow()

        and: "the expected JSON"
        JsonNode expected = JSON_MAPPER.readTree(
                """{
                    "query": "$QUERY_DATA",
                    "status": "$STATUS_DATA",
                    "jobTicket": "$JOB_TICKET_DATA",
                    "dateCreated": "$DATE_CREATED_DATA",
                    "dateUpdated": "$DATE_UPDATED_DATA",
                    "userId": "$USER_ID_DATA"
                }"""
        )

        expect:
        JSON_MAPPER.valueToTree(row) == expected
    }

    @Unroll
    def "equal returns #truefalse for #row1 and #row2"() {
        expect:
        equalityOperation(row1, row2)

        where:
        row1 | row2 || truefalse
        buildJobRow() | buildJobRow() || true
        buildJobRow(1) | buildJobRow(2) || false
        buildJobRow() | buildJobRow([(JOB_TICKET): JOB_TICKET_DATA.reverse()]) || false
        buildJobRow() | buildJobRow([(QUERY): QUERY_DATA + ",aMetric"]) || false

        equalityOperation = truefalse ? {row1, row2 -> row1 == row2} : {row1, row2 -> row1 != row2}
    }

    @Unroll
    def "A job with id #id has hashcode #id.hashCode()"() {
        expect: "a JobRow with the specified ID has the specified hashcode"
        buildJobRow([(JOB_TICKET): id]).hashCode() == id.hashCode()

        where:
        id << ["1", "An ID", "89fa"]
    }

    static JobRow buildJobRow(int id) {
        // Invocations of toString are necessary to convert GString to a String for use in JobRow constructor
        buildJobRow([(JOB_TICKET): "$id".toString()])
    }

    static JobRow buildJobRow(Map<JobField, String> jobMetadata = [:]) {
        new JobRow(
                JOB_TICKET,
                [
                        (QUERY): jobMetadata.get(QUERY) ?: QUERY_DATA,
                        (STATUS): jobMetadata.get(STATUS) ?: STATUS_DATA,
                        (JOB_TICKET): jobMetadata.get(JOB_TICKET) ?: JOB_TICKET_DATA,
                        (DATE_CREATED): jobMetadata.get(DATE_CREATED) ?: DATE_CREATED_DATA,
                        (DATE_UPDATED): jobMetadata.get(DATE_UPDATED) ?: DATE_UPDATED_DATA,
                        (USER_ID): jobMetadata.get(USER_ID) ?: USER_ID_DATA
                ] as LinkedHashMap
        )
    }
}
