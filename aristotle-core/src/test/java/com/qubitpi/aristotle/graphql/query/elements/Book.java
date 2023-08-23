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
package com.qubitpi.aristotle.graphql.query.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bitbucket.jack20191124.graphql.query.VariableFieldSerializer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A test bean.
 */
@SuppressWarnings({"JavadocMethod", "JavadocVariable"})
public class Book {

    @JsonProperty
    private long id;

    @JsonProperty
    @JsonSerialize(using = VariableFieldSerializer.class, as = String.class)
    private String title;

    /**
     * A test relationship.
     */
    @JsonProperty
    private Collection<Author> authors = new ArrayList<>();

    public Book() {
        // intentionally left blank
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(final Collection<Author> authors) {
        this.authors = authors;
    }
}
