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

import java.util.Locale;

/**
 * The default fields for job metadata.
 */
public enum DefaultJobField implements JobField {

    QUERY("The data query this job is satisfying."),
    STATUS("The current status of the job."),
    JOB_TICKET("The job's unique identifier."),
    USER_ID("The ID of the user that created this job."),
    DATE_CREATED("The date the job was created."),
    DATE_UPDATED("The date the job was last updated.");

    private final String serializedName;
    private final String description;

    /**
     * Constructor.
     *
     * @param description  Description of the job field
     */
    DefaultJobField(final String description) {
        this.serializedName = camelCase(name());
        this.description = description;
    }

    @Override
    public String getName() {
        return serializedName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Converts the string value to a camel case string, e.g. ENUM_CONST_VALUE {@literal ->} enumConstValue.
     *
     * <ul>
     *     <li> We do not want to load a giant library, such as Guava or Commons, simply for the sake of using one
     *          small method
     *     <li> We do NOT bump this method as a utility method because we believe utility class is always a misuse of
     *          Java language feature
     *     <li> We tolerate this bit of code duplication as long as this method, along with its duplication, is
     *          encapsulated in its class (i.e. making it a 'private' method) and bug free so there will be no need to
     *          extend or change this method (and its duplication)
     * </ul>
     * @param underscoredString  A string in underscore form
     *
     * @return enum string changed to camel case format
     */
    @NotNull
    private static String camelCase(final String underscoredString) {
        final String[] words = underscoredString.toLowerCase(Locale.ENGLISH).split("_");
        final StringBuilder lowerCamelCase = new StringBuilder(words[0]);

        for (int i = 1; i < words.length; i++) {
            lowerCamelCase.append(words[i].substring(0, 1).toUpperCase(Locale.ENGLISH));
            lowerCamelCase.append(words[i].substring(1));
        }

        return lowerCamelCase.toString();
    }
}
