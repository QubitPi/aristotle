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

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Reloadable;

import jakarta.validation.constraints.NotNull;

import java.util.Optional;

/**
 * {@link ApplicationConfig} an interface for retrieving configuration values, allowing for compile-time null-check and
 * decomposing module config into composite config sub-objects.
 * <p>
 * Downstream app should put their configuration properties in af file named "userConfig.properties". When this is done,
 * {@link ApplicationConfig} will load the properties from several sources in the follwoing order:
 * <ol>
 *     <li> Load the given property from the
 *          <a href="https://docs.oracle.com/javase/tutorial/essential/environment/env.html">
 *              operating system's environment variables
 *          </a>; if an environment variable with the same name is found, its value will be returned.
 *     <li> Load the given property from the
 *          <a href="https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html">
 *              Java system properties
 *          </a>; if such property is defined, the associated value is returned.
 *     <li> Load the given property from the classpath from the app-defined resource identified by the path
 *          "classpath:userConfig.properties"; if the property is found, the associated value is returned.
 *     <li> Load the default property defined by aristotle
 *     <li> In case app doesn't provide a required config (such as binding factory), an {@link Optional#empty()} or
 *          an empty collection value shall be returned.
 * </ol>
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",
        "system:properties",
        "classpath:userConfig.properties",
        "classpath:applicationConfig.properties"
})
public interface ApplicationConfig extends Reloadable {

    /**
     * Returns the canonical class name of the concrete {@link AbstractBinderFactory binder factory} of downstream app.
     * <p>
     * The property key for this value is "aristotle_resource_binder". If no such key is found from all config sources,
     * this method returns {@link Optional#empty()}.
     *
     * @return a fully qualified class name, such as {@code "com.company.app.application.AppBinderFactory"}, wrapped
     * inside an {@link Optional} or {@link Optional#empty()} if the property key for this value does not exist
     */
    @NotNull
    @Key("aristotle_resource_binder")
    Optional<String> bindingFactory();
}
