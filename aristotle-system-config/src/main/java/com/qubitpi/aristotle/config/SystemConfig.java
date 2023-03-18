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

import jakarta.validation.constraints.NotNull;

import java.util.Optional;

/**
 * {@link SystemConfig} is an interface for retrieving configuration values, allowing for implicit type conversion and
 * use of a runtime properties interface to override configured settings.
 */
public interface SystemConfig {

    /**
     * Gets a package scoped variable name.
     *
     * @param suffix  The variable name of the configuration variable without the package prefix
     *
     * @return variable name
     *
     * @throws NullPointerException if {@code suffix} is {@code null}
     */
    @NotNull
    String getPackageVariableName(@NotNull String suffix);

    /**
     * Sets property value for a key.
     *
     * @param key  The key of the property to change
     * @param value  The value to set to
     *
     * @throws NullPointerException if either {@code key} or {@code value} is {@code null}
     */
    void setProperty(@NotNull String key, @NotNull String value);

    /**
     * Removes property from the use-defined runtime configuration.
     *
     * @param key  The key of the property to remove
     *
     * @throws NullPointerException if {@code key} is {@code null}
     */
    void clearProperty(@NotNull String key);

    /**
     * Returns property value as String value wrapped inside an {@link Optional} for a key or
     * {@link Optional#empty() empty} if no such property exists.
     *
     * @param key  The key for which value needs to be fetched
     *
     * @return an {@link Optional} value for the requested key or {@link Optional#empty()}
     *
     * @throws NullPointerException if {@code key} is {@code null}
     */
    @NotNull
    Optional<String> getStringProperty(@NotNull String key);

    /**
     * Returns property value as int value wrapped inside an {@link Optional} for a key or
     * {@link Optional#empty() empty} if no such property exists.
     *
     * @param key  The key for which value needs to be fetched
     *
     * @return an {@link Optional} value for the requested key or {@link Optional#empty()}
     *
     * @throws NullPointerException if {@code key} is {@code null}
     */
    @NotNull
    Optional<Integer> getIntProperty(@NotNull String key);

    /**
     * Returns property value as boolean value wrapped inside an {@link Optional} for a key or
     * {@link Optional#empty() empty} if no such property exists.
     *
     * @param key  The key for which value needs to be fetched
     *
     * @return an {@link Optional} value for the requested key or {@link Optional#empty()}
     *
     * @throws NullPointerException if {@code key} is {@code null}
     */
    @NotNull
    Optional<Boolean> getBooleanProperty(@NotNull String key);

    /**
     * Returns property value as long value wrapped inside an {@link Optional} for a key or
     * {@link Optional#empty() empty} if no such property exists.
     *
     * @param key  The key for which value needs to be fetched
     *
     * @return an {@link Optional} value for the requested key or {@link Optional#empty()}
     *
     * @throws NullPointerException if {@code key} is {@code null}
     */
    @NotNull
    Optional<Long> getLongProperty(@NotNull String key);

    /**
     * Returns property value as a double wrapped inside an {@link Optional} for a key or {@link Optional#empty() empty}
     * if no such property exists.
     *
     * @param key  The key for which value needs to be fetched
     *
     * @return an {@link Optional} value for the requested key or {@link Optional#empty()}
     *
     * @throws NullPointerException if {@code key} is {@code null}
     */
    @NotNull
    Optional<Double> getDoubleProperty(@NotNull String key);

    /**
     * Returns property value as float value wrapped inside an {@link Optional} for a key or
     * {@link Optional#empty() empty} if no such property exists.
     *
     * @param key  The key for which value needs to be fetched
     *
     * @return an {@link Optional} value for the requested key or {@link Optional#empty()}
     *
     * @throws NullPointerException if {@code key} is {@code null}
     */
    @NotNull
    Optional<Float> getFloatProperty(@NotNull String key);
}
