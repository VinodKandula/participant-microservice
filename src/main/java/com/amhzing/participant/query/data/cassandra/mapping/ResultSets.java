package com.amhzing.participant.query.data.cassandra.mapping;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.*;

public final class ResultSets {

    private ResultSets() {
    }

    public static Observable<ResultSet> queryAllAsObservable(final Session session,
                                                             final String query,
                                                             final Object... partitionKeys) {
        notNull(session);
        notEmpty(query);
        noNullElements(partitionKeys);

        final List<ResultSetFuture> futures = sendQueries(session, query, partitionKeys);
        final Scheduler scheduler = Schedulers.io();

        return futures.stream()
                      .map(future -> Observable.from(future, scheduler))
                      .collect(collectingAndThen(toList(), Observable::merge));
    }

    private static List<ResultSetFuture> sendQueries(final Session session,
                                                     final String query,
                                                     final Object[] partitionKeys) {

        return Stream.of(partitionKeys)
                     .map(key -> session.executeAsync(query, key))
                     .collect(toList());
    }
}
