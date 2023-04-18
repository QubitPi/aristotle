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
package com.qubitpi.aristotle.config;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Mutable;

import jakarta.validation.constraints.NotNull;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.Objects;
import java.util.Optional;

/**
 * A place that stores all default Aristotle configurations.
 * <p>
 * {@link AristotleSystemConfig} is not to be instantiated directly. Instead, please use
 * {@link SystemConfigFactory#getInstance()} to get an instance of it.
 */
@Immutable
@ThreadSafe
class AristotleSystemConfig implements SystemConfig {

    public static final String DEFAULT_PACKAGE_NAME = "aristotle";

    /**
     * {@link InnerConfig} is source-of-truth of all config values.
     *
     * Downstream application should put their config in a file named "applicationConfig.properties" and write this file
     * into classpath during Maven build.
     *
     * After {@link InnerConfig} is instantiated, its properties can be {@link Mutable#setProperty(String, String)} and
     * can be {@link Accessible#getProperty(String) accessed} in ways beyond the regular property mapping methods.
     *
     * {@link InnerConfig} is a sub-class of {@link org.aeonbits.owner.Config}. One way to make a new
     * {@link InnerConfig} instance, for example, is
     * {@code ConfigFactory.create(AristotleSystemConfig.InnerConfig.class)}. See
     * <a href="https://qubitpi.github.io/owner/docs/usage/">OWNER config basic usage</a> for more information.
     *
     * @see <a href="https://qubitpi.github.io/owner/">OWNER config object</a>
     */
    @Config.Sources({"classpath:applicationConfig.properties"})
    interface InnerConfig extends Accessible, Mutable {

        /**
         * The prefix that namespacing config properties relevant to Aristotle.
         *
         * The default prefix is "aristotle" and can be changed via {@link #setProperty(String, String)}.
         *
         * @return a string
         */
        @Key("aristotle.packageName")
        @DefaultValue(DEFAULT_PACKAGE_NAME)
        String packageName();

        /**
         * The fully-qualified class name of binder factory used in the runtime application.
         *
         *
         *
         * @return a string
         */
        @Key("aristotle.resourceBinder")
        String resourceBinder();
    }

    private final InnerConfig properties;

    /**
     * All-args constructor.
     *
     * @param properties  An <a href="https://qubitpi.github.io/owner/">OWNER config object</a> that holds all config
     * values, making {@link AristotleSystemConfig} sort of like a proxy config.
     *
     * @throws NullPointerException if {@code properties} is {@code null}
     */
    AristotleSystemConfig(@NotNull final InnerConfig properties) {
        this.properties = Objects.requireNonNull(properties, "properties");
    }

    @Override
    public void setProperty(@NotNull final String key, @NotNull final String value) {
        getProperties().setProperty(key, value);
    }

    @Override
    public void clearProperty(@NotNull final String key) {
        getProperties().removeProperty(key);
    }

    @Override
    public String getPackageVariableName(@NotNull final String suffix) {
        return getStringProperty("aristotle.packageName").orElse(DEFAULT_PACKAGE_NAME) + "." + suffix;
    }

    @Override
    public Optional<String> getStringProperty(@NotNull final String key) {
        if (!getProperties().propertyNames().contains(key)) {
            return Optional.empty();
        }

        return Optional.ofNullable(getProperties().getProperty(key));
    }

    @Override
    public Optional<Integer> getIntProperty(@NotNull final String key) {
        if (!getProperties().propertyNames().contains(key)) {
            return Optional.empty();
        }

        return Optional.of(Integer.valueOf(getProperties().getProperty(key)));
    }

    @Override
    public Optional<Boolean> getBooleanProperty(@NotNull final String key) {
        if (!getProperties().propertyNames().contains(key)) {
            return Optional.empty();
        }

        return Optional.of(Boolean.valueOf(getProperties().getProperty(key)));
    }

    @Override
    public Optional<Long> getLongProperty(@NotNull final String key) {
        if (!getProperties().propertyNames().contains(key)) {
            return Optional.empty();
        }

        return Optional.of(Long.valueOf(getProperties().getProperty(key)));
    }

    @Override
    public Optional<Double> getDoubleProperty(@NotNull final String key) {
        if (!getProperties().propertyNames().contains(key)) {
            return Optional.empty();
        }

        return Optional.of(Double.valueOf(getProperties().getProperty(key)));
    }

    @Override
    public Optional<Float> getFloatProperty(@NotNull final String key) {
        if (!getProperties().propertyNames().contains(key)) {
            return Optional.empty();
        }

        return Optional.of(Float.valueOf(getProperties().getProperty(key)));
    }

    @NotNull
    private InnerConfig getProperties() {
        return properties;
    }
}
