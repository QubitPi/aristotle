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
package com.qubitpi.aristotle.examples.basic.graphstore;

import graphql.schema.DataFetcher;
import jakarta.validation.constraints.NotNull;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A sanity-check data fetcher factory that defines some demo data for querying.
 * <p>
 * The factory defines two data fetchers:
 * <p>
 * <ol>
 *     <li> {@link #getBookDataFetcher() book data fetcher}
 *     <li> {@link #getAuthorDataFetcher() book author data fetcher}
 * </ol>
 */
@Immutable
@ThreadSafe
public class DataFetcherFactory {

    /**
     * All queryable book ID's.
     */
    public static final List<String> BOOK_IDS = Arrays.asList("book-1", "book-2", "book-3");

    /**
     * All linked/sub-query author ID's.
     */
    public static final List<String> AUTHOR_IDS = Arrays.asList("author-1", "author-2", "author-3");

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PAGE_COUNT = "pageCount";
    private static final String AUTHOR_ID = "authorId";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";

    private static final List<Map<String, String>> BOOKS = Arrays.asList(
            Map.of(
                    ID, BOOK_IDS.get(0),
                    NAME, "Harry Potter and the Philosopher's Stone",
                    PAGE_COUNT, "223",
                    AUTHOR_ID, AUTHOR_IDS.get(0)
            ),
            Map.of(
                    ID, BOOK_IDS.get(1),
                    NAME, "Moby Dick",
                    PAGE_COUNT, "635",
                    AUTHOR_ID, AUTHOR_IDS.get(1)
            ),
            Map.of(
                    ID, BOOK_IDS.get(2),
                    NAME, "Interview with the vampire",
                    PAGE_COUNT, "371",
                    AUTHOR_ID, AUTHOR_IDS.get(2)
            )
    );

    private static final List<Map<String, String>> AUTHORS = Arrays.asList(
            Map.of(
                    ID, AUTHOR_IDS.get(0),
                    FIRST_NAME, "Joanne",
                    LAST_NAME, "Rowling"
            ),
            Map.of(
                    ID, AUTHOR_IDS.get(1),
                    FIRST_NAME, "Herman",
                    LAST_NAME, "Melville"
            ),
            Map.of(
                    ID, AUTHOR_IDS.get(2),
                    FIRST_NAME, "Anne",
                    LAST_NAME, "Rice"
            )
    );

    private static final DataFetcher<Map<String, String>> BOOK_DATA_FETCHER = dataFetchingEnvironment -> {
        final String bookId = dataFetchingEnvironment.getArgument(ID);
        return BOOKS
                .stream()
                .filter(book -> book.get(ID).equals(bookId))
                .findFirst()
                .orElse(null);
    };

    private static final DataFetcher<Map<String, String>> AUTHOR_DATA_FETCHER = dataFetchingEnvironment -> {
        final Map<String, String> book = dataFetchingEnvironment.getSource();
        final String authorId = book.get(AUTHOR_ID);
        return AUTHORS
                .stream()
                .filter(author -> author.get(ID).equals(authorId))
                .findFirst()
                .orElse(null);
    };

    /**
     * The book attribute data fetcher.
     *
     * Each book is represented by a {@link Map}, with each map key-value pair representing a book metadata, such as
     * book name. The queryable ID's are {@link #BOOK_IDS}
     *
     * @return a standard {@link DataFetcher}
     */
    @NotNull
    public static DataFetcher<Map<String, String>> getBookDataFetcher() {
        return BOOK_DATA_FETCHER;
    }

    /**
     * The book author attribute data fetcher.
     *
     * Each author is represented by a {@link Map}, with each map key-value pair representing am author metadata, such
     * as author name. The queryable ID's are {@link #AUTHOR_IDS}
     *
     * @return a standard {@link DataFetcher}
     */
    @NotNull
    public static DataFetcher<Map<String, String>> getAuthorDataFetcher() {
        return AUTHOR_DATA_FETCHER;
    }
}
