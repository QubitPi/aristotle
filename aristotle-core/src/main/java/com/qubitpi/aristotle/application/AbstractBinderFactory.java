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

import com.qubitpi.aristotle.graphstore.GraphStore;

import org.glassfish.hk2.utilities.Binder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import jakarta.validation.constraints.NotNull;

/**
 * {@link AbstractBinderFactory} implements standard buildBinder functionality.
 */
public abstract class AbstractBinderFactory implements BinderFactory {

    @Override
    public Binder buildBinder() {
        return new AbstractBinder() {
            @Override
            protected void configure() {
                bind(buildGraphStore()).to(GraphStore.class);

                afterBinding(this);
            }
        };
    }

    /**
     * Registers graph data client.
     *
     * @return a service for fetching graphs
     */
    @NotNull
    protected abstract Class<? extends GraphStore> buildGraphStore();

    @Override
    public void afterRegistration(final ResourceConfig resourceConfig) {
        // No-ops by default
    }

    /**
     * Allows additional app-specific binding.
     *
     * @param abstractBinder  Binder to use for binding
     */
    protected void afterBinding(final @NotNull AbstractBinder abstractBinder) {
        // No-ops by default
    }
}
