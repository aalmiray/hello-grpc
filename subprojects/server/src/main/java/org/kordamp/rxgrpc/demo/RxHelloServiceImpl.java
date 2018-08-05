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
package org.kordamp.rxgrpc.demo;

import io.reactivex.Flowable;
import io.reactivex.Single;
import org.kordamp.grpc.demo.HelloRequest;
import org.kordamp.grpc.demo.HelloResponse;
import org.kordamp.grpc.demo.RxHelloServiceGrpc;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class RxHelloServiceImpl extends RxHelloServiceGrpc.HelloServiceImplBase {
    @Override
    public Single<HelloResponse> helloUnary(Single<HelloRequest> request) {
        return doWithSingle(() -> request.map(HelloRequest::getName)
            .map(n -> "Hello " + n)
            .map(RxHelloServiceImpl::asResponse));
    }

    @Override
    public Flowable<HelloResponse> helloServerStream(Single<HelloRequest> request) {
        return doWithFlowable(() -> request.map(HelloRequest::getName)
            .toFlowable()
            .flatMap(n -> Flowable.fromIterable(Arrays.asList(1, 2, 3, 4, 5))
                .map(i -> "(" + i + ") Hello " + n))
            .map(RxHelloServiceImpl::asResponse));
    }

    @Override
    public Single<HelloResponse> helloClientStream(Flowable<HelloRequest> request) {
        return doWithSingle(() -> request.map(HelloRequest::getName)
            .map(n -> "Hello " + n)
            .toList()
            .flatMap(l -> Single.just(l.stream().collect(Collectors.joining(", "))))
            .map(RxHelloServiceImpl::asResponse));
    }

    @Override
    public Flowable<HelloResponse> helloBidirectional(Flowable<HelloRequest> request) {
        return doWithFlowable(() -> request.map(HelloRequest::getName)
            .map(n -> "Hello " + n)
            .map(RxHelloServiceImpl::asResponse));
    }

    private static HelloResponse asResponse(String s) {
        return HelloResponse.newBuilder()
            .setReply(s)
            .build();
    }

    private static <T> Flowable<T> doWithFlowable(Supplier<Flowable<T>> supplier) {
        try {
            return supplier.get();
        } catch (Throwable t) {
            return Flowable.error(t);
        }
    }

    private static <T> Single<T> doWithSingle(Supplier<Single<T>> supplier) {
        try {
            return supplier.get();
        } catch (Throwable t) {
            return Single.error(t);
        }
    }
}
