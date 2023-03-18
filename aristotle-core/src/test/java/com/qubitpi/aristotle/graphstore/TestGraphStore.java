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
package com.qubitpi.aristotle.graphstore;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * A {@link GraphStore} test stub that facilitates {@link com.qubitpi.aristotle.web.endpoints.DataServlet} mocking
 * through {@link com.qubitpi.aristotle.application.TestBinderFactory} and
 * {@link com.qubitpi.aristotle.application.JerseyTestBinder}.
 */
public class TestGraphStore implements GraphStore {

    @Override
    public JsonNode query(final String nativeQuery) {
        return null;
    }
}
