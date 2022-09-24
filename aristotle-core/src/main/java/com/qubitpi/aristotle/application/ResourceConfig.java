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

import com.qubitpi.aristotle.web.ErrorMessageFormat;

import org.glassfish.hk2.utilities.Binder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.ApplicationPath;

/**
 * The resource configuration for the Aristotle web applications.
 */
@ApplicationPath("v1")
public class ResourceConfig extends org.glassfish.jersey.server.ResourceConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceConfig.class);
    private static final String ARISTOTLE_ENDPOINT_PACKAGE = "com.qubitpi.aristotle.web.endpoints";

    private final ApplicationConfig applicationConfig;

    /**
     * DI Constructor.
     *
     * @param applicationConfigProvider  An injectable provider offering objects containing config values
     *
     * @throws ClassNotFoundException if a class was not found when attempting to load it
     * @throws InstantiationException if a class was not able to be instantiated
     * @throws IllegalAccessException if there was a problem accessing something due to security restrictions
     */
    @Inject
    public ResourceConfig(
            final @NotNull Provider<ApplicationConfig> applicationConfigProvider
    ) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.applicationConfig = applicationConfigProvider.get();

        final String bindingFactory = getApplicationConfig().bindingFactory().orElseThrow(() -> {
            LOG.error(ErrorMessageFormat.CONFIG_NOT_FOUND.logFormat("aristotle_resource_binder"));
            return new IllegalStateException(ErrorMessageFormat.CONFIG_NOT_FOUND.format());
        });

        final Class<? extends BinderFactory> binderClass = Class.forName(bindingFactory)
                .asSubclass(BinderFactory.class);
        final BinderFactory binderFactory = binderClass.newInstance();
        final Binder binder = binderFactory.buildBinder();

        packages(ARISTOTLE_ENDPOINT_PACKAGE);
        register(binder);

        // Call post-registration hook to allow for additional registration
        binderFactory.afterRegistration(this);
    }

    @NotNull
    private ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }
}
