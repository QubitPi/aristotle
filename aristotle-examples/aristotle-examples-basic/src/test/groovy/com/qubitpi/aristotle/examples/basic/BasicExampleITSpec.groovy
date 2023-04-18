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
package com.qubitpi.aristotle.examples.basic

import org.hamcrest.Matchers
import org.testcontainers.containers.GenericContainer
import org.testcontainers.images.PullPolicy
import org.testcontainers.images.builder.ImageFromDockerfile
import org.testcontainers.spock.Testcontainers

import io.restassured.RestAssured
import io.restassured.path.json.JsonPath
import spock.lang.IgnoreIf
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.nio.file.Paths

/**
 * Integration tests for aristotle-examples-basic Dockerfile.
 *
 * It uses testcontainers to orchestrate lifecycle of the test container through @Testcontainers annotation
 *
 * see https://www.testcontainers.org/quickstart/spock_quickstart/
 * see https://www.testcontainers.org/test_framework_integration/spock/#testcontainers-class-annotation
 */
@Testcontainers
@IgnoreIf({ dockerNotInstalled() })
class BasicExampleITSpec extends Specification {

    static final int SUCCESS = 0
    static final String CHECK_DOCKER_INSTALLED_COMMAND = "docker -v"
    static final String DOCKERFILE_ABS_PATH = String.format("%s/Dockerfile", System.getProperty("user.dir"))

    /**
     * Returns the data to use in where blocks as a list of lists.
     *
     * @return the data as a list of lists
     */
    static testIterationData() {
        [
                ["{ bookById(id: \"book-1\") { id name pageCount author { id firstName lastName } } }", "book-1.json"],
                ["{ bookById(id: \"book-2\") { id name pageCount author { id firstName lastName } } }", "book-2.json"],
                ["{ bookById(id: \"book-3\") { id name pageCount author { id firstName lastName } } }", "book-3.json"],
                ["{ bookById(id: \"book-1\") { name author { firstName lastName } } }"                , "book-1-with-partial-fields.json"],
                ["{ bookById(id: \"book-2\") { name author { firstName lastName } } }"                , "book-2-with-partial-fields.json"],
                ["{ bookById(id: \"book-3\") { name author { firstName lastName } } }"                , "book-3-with-partial-fields.json"],
                ["{ bookById(id: \"book-1\") { name, pageCount } }"                                   , "book-1-no-author.json"],
                ["{ bookById(id: \"book-2\") { name, pageCount } }"                                   , "book-2-no-author.json"],
                ["{ bookById(id: \"book-3\") { name, pageCount } }"                                   , "book-3-no-author.json"]
        ]
    }

    @SuppressWarnings('GroovyUnusedCatchParameter')
    private static boolean dockerNotInstalled() {
        try {
            return Runtime.getRuntime().exec(CHECK_DOCKER_INSTALLED_COMMAND).waitFor() != SUCCESS
        } catch (Exception exception) {
            return true // I hate this
        }
    }

    @Shared
    @Subject
    GenericContainer container = new GenericContainer<>(
            new ImageFromDockerfile().withDockerfile(Paths.get(DOCKERFILE_ABS_PATH))
    )
            .withExposedPorts(8080)
            .withImagePullPolicy(PullPolicy.defaultPolicy())

    def setupSpec() {
        RestAssured.baseURI = "http://" + container.host
        RestAssured.port = container.firstMappedPort
        RestAssured.basePath = "/v1"
    }

    @Unroll
    def "Dockerized basic example responds to GET request '#query' with native GraphQL return data defined in '#expectedJsonFile'"() {
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

        where:
        [query, expectedJsonFile] << testIterationData()
    }
}
