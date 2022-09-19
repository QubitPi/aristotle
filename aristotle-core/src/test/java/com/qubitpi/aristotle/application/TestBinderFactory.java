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
import com.qubitpi.aristotle.graphstore.TestGraphStore;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Athena test app configuration binder.
 */
public class TestBinderFactory extends AbstractBinderFactory {

    /**
     * A switch indicating whether after-binding is invoked.
     */
    public boolean afterBindingHookWasCalled = false;

    /**
     * A switch indicating whether after-registration is invoked.
     */
    public boolean afterRegistrationHookWasCalled = false;

    private final ApplicationState applicationState;

    /**
     * Constructor used for unit-testing {@link AbstractBinderFactory} extension capability to make sure all bindings
     * occurs.
     *
     * @see AbstractBinderFactorySpec
     */
    public TestBinderFactory() {
        this.applicationState = new ApplicationState();
    }

    /**
     * Constructor for servlet testing where JerseyTest harness and relevant DI are involved.
     *
     * @param applicationState  An entry point for setting up test data
     */
    public TestBinderFactory(final ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    @Override
    protected Class<? extends GraphStore> buildGraphStore() {
        return TestGraphStore.class;
    }

    @Override
    public void afterRegistration(final ResourceConfig resourceConfig) {
        afterRegistrationHookWasCalled = true;
    }

    @Override
    protected void afterBinding(final AbstractBinder abstractBinder) {
        afterBindingHookWasCalled = true;
    }
}
