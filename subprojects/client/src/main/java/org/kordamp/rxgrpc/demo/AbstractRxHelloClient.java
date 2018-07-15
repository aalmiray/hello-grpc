/*
 * Copyright 018 Andres Almiray
 *
 * This file is part of Java Trove Examples
 *
 * Java Trove Examples is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Java Trove Examples is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Java Trove Examples. If not, see <http://www.gnu.org/licenses/>.
 */
package org.kordamp.rxgrpc.demo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.kordamp.grpc.demo.HelloRequest;
import org.kordamp.grpc.demo.RxHelloServiceGrpc;

public abstract class AbstractRxHelloClient {
    protected HelloRequest asRequest(String str) {
        return HelloRequest.newBuilder()
            .setName(str)
            .build();
    }

    protected final ManagedChannel channel;
    protected final RxHelloServiceGrpc.RxHelloServiceStub stub;

    protected AbstractRxHelloClient() {
        channel = ManagedChannelBuilder.forAddress("localhost", 4567)
            .usePlaintext()
            .build();

        stub = RxHelloServiceGrpc.newRxStub(channel);
    }
}
