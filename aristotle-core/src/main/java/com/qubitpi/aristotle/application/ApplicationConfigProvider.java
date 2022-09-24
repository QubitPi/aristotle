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

import org.aeonbits.owner.ConfigFactory;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import javax.inject.Provider;

/**
 * Provides instance of {@link ApplicationConfig} for dependency injection.
 */
@Immutable
@ThreadSafe
@javax.ws.rs.ext.Provider
public class ApplicationConfigProvider implements Provider<ApplicationConfig> {

    @Override
    public ApplicationConfig get() {
        return ConfigFactory.create(ApplicationConfig.class);
    }
}
