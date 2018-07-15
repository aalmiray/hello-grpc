/*
 * Copyright 2018 Andres Almiray
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
package org.kordamp.grpc.demo;

import io.grpc.stub.StreamObserver;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void helloUnary(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        doWithObserver(responseObserver, observer -> {
            HelloResponse response = HelloResponse.newBuilder()
                .setReply("Hello " + request.getName())
                .build();
            observer.onNext(response);
        });
    }

    @Override
    public void helloServerStream(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        doWithObserver(responseObserver, observer -> {
            for (int i = 0; i < 5; i++) {
                HelloResponse response = HelloResponse.newBuilder()
                    .setReply("(" + i + ") Hello " + request.getName())
                    .build();
                observer.onNext(response);
            }
        });
    }

    @Override
    public StreamObserver<HelloRequest> helloClientStream(StreamObserver<HelloResponse> responseObserver) {
        List<String> requests = new ArrayList<>();
        return new StreamObserver<HelloRequest>() {
            @Override
            public void onNext(HelloRequest request) {
                requests.add(request.getName());
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                HelloResponse response = HelloResponse.newBuilder()
                    .setReply("Hello " + requests.stream().collect(Collectors.joining(", ")))
                    .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<HelloRequest> helloBidirectional(StreamObserver<HelloResponse> responseObserver) {
        return new StreamObserver<HelloRequest>() {
            @Override
            public void onNext(HelloRequest request) {
                HelloResponse response = HelloResponse.newBuilder()
                    .setReply("Hello " + request.getName())
                    .build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    private <T> void doWithObserver(@Nonnull StreamObserver<T> observer, @Nonnull Consumer<StreamObserver<T>> consumer) {
        try {
            consumer.accept(observer);
            observer.onCompleted();
        } catch (Exception e) {
            observer.onError(e);
        }
    }
}
