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
package com.qubitpi.aristotle.examples.basic.web.endpoints

import com.qubitpi.aristotle.application.ResourceConfig
import com.qubitpi.aristotle.config.SystemConfig
import com.qubitpi.aristotle.config.SystemConfigFactory
import com.qubitpi.aristotle.examples.basic.BasicExampleITSpec
import com.qubitpi.aristotle.examples.basic.JettyServerFactory

import org.eclipse.jetty.server.Server
import org.hamcrest.Matchers

import io.restassured.RestAssured
import io.restassured.path.json.JsonPath
import spock.lang.Specification
import spock.lang.Unroll

class DataServletSpec extends Specification {

    static final SystemConfig SYSTEM_CONFIG = SystemConfigFactory.getInstance()

    static final int PORT = 8080
    private static final String RESOURCE_BINDER_KEY = "resourceBinder"
    private static final String RESOURCE_BINDER = "com.qubitpi.aristotle.examples.basic.application.BasicBinderFactory"

    def setupSpec() {
        SYSTEM_CONFIG.setProperty(SYSTEM_CONFIG.getPackageVariableName(RESOURCE_BINDER_KEY), RESOURCE_BINDER)

        RestAssured.baseURI = "http://localhost"
        RestAssured.port = PORT
        RestAssured.basePath = "/v1"
    }

    @Unroll
    def "Sending #query to native endpoints returns what's defined in #expectedJsonFile"() {
        setup:
        Server server = JettyServerFactory.newInstance(PORT, "/v1/*", new ResourceConfig())
        server.start()

        expect:
        RestAssured.given()
                .queryParam("query", query)
                .when()
                .get("/data/graphql")
                .then()
                .statusCode(200)
                .body(
                        "data.bookById",
                        Matchers.equalTo(
                                new JsonPath(
                                        new File(String.format("src/test/resources/%s", expectedJsonFile))
                                ).get()
                        )
                )

        cleanup:
        server.stop()

        where:
        [query, expectedJsonFile] << BasicExampleITSpec.testIterationData()
    }
}
