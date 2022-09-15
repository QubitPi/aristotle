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
package com.qubitpi.aristotle.async.jobs.jobrows;

import jakarta.validation.constraints.NotNull;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

/**
 * Represents an attribute of job metadata.
 * <p>
 * Implementation must be immutable and override {@link #equals(Object)} and {@link #hashCode()}, because {@link JobRow}
 * uses it as {@link java.util.Map} key.
 */
@Immutable
@ThreadSafe
public interface JobField {

    /**
     * Returns the name of the field (i.e. the name of the attribute in the job metadata).
     *
     * @return the name of the field
     */
    @NotNull
    String getName();

    /**
     * Returns a human-friendly description of the field.
     *
     * @return a human-readable description of the field
     */
    @NotNull
    String getDescription();
}
