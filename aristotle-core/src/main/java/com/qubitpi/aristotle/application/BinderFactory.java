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
package com.qubitpi.aristotle.application;

import org.glassfish.hk2.utilities.Binder;

import jakarta.validation.constraints.NotNull;

/**
 * A binder factory builds a custom binder for the Jersey application.
 * <p>
 * The factory makes the component object instance that will eventually be passed to
 * {@link org.glassfish.jersey.server.ResourceConfig#register(Object)}.
 */
public interface BinderFactory {

    /**
     * Builds a hk2 Binder instance.
     * <p>
     * This binder should bind all relevant resources, such as {@link com.qubitpi.aristotle.graphstore.GraphStore},
     * for runtime dependency injection.
     *
     * @return a binder instance that will be registered by putting as a parameter to
     * {@link org.glassfish.jersey.server.ResourceConfig#register(Object)}
     */
    @NotNull
    Binder buildBinder();

    /**
     * Allows additional app-specific Jersey feature registration and config.
     * <p>
     * Specifically, this method will be invoked in {@link org.glassfish.jersey.server.ResourceConfig} constructor
     * before its being dependency-injected into Jetty container.
     *
     * @param resourceConfig  Resource config to use for accessing the configuration
     */
    void afterRegistration(@NotNull ResourceConfig resourceConfig);
}
