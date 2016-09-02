package com.amhzing.participant.domain.gateway;

import org.axonframework.commandhandling.gateway.Timeout;
import org.axonframework.common.annotation.MetaData;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface MetaDataEnrichedCommandGateway {

    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void send(final Object command,
                     @MetaData("correlationId") String correlationId,
                     @MetaData("userId") String userId)
            throws TimeoutException, InterruptedException, RuntimeException;

    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public <R> R sendAndWait(final Object command,
                             @MetaData("correlationId") String correlationId,
                             @MetaData("userId") String userId)
            throws TimeoutException, InterruptedException;
}
