package org.kordamp.rxgrpc.demo;

import io.reactivex.Flowable;
import io.reactivex.Single;
import org.kordamp.grpc.demo.HelloRequest;
import org.kordamp.grpc.demo.HelloResponse;
import org.kordamp.grpc.demo.RxHelloServiceGrpc;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RxHelloServiceImpl extends RxHelloServiceGrpc.HelloServiceImplBase {
    @Override
    public Single<HelloResponse> helloUnary(Single<HelloRequest> request) {
        return singleToSingle(request, s -> s.map(HelloRequest::getName)
            .map(n -> "Hello " + n)
            .map(RxHelloServiceImpl::asResponse));
    }

    @Override
    public Flowable<HelloResponse> helloServerStream(Single<HelloRequest> request) {
        return singleToFlowable(request, s -> s.map(HelloRequest::getName)
            .toFlowable()
            .flatMap(n -> Flowable.fromIterable(Arrays.asList(1, 2, 3, 4, 5))
                .map(i -> "(" + i + ") Hello " + n))
            .map(RxHelloServiceImpl::asResponse));
    }

    @Override
    public Single<HelloResponse> helloClientStream(Flowable<HelloRequest> request) {
        return flowableToSingle(request, f -> f.map(HelloRequest::getName)
            .map(n -> "Hello " + n)
            .toList()
            .flatMap(l -> Single.just(l.stream().collect(Collectors.joining(", "))))
            .map(RxHelloServiceImpl::asResponse));
    }

    @Override
    public Flowable<HelloResponse> helloBidirectional(Flowable<HelloRequest> request) {
        return flowableToFlowable(request, f -> f.map(HelloRequest::getName)
            .map(n -> "Hello " + n)
            .map(RxHelloServiceImpl::asResponse));
    }

    private static HelloResponse asResponse(String s) {
        return HelloResponse.newBuilder()
            .setReply(s)
            .build();
    }

    private static <R, T> Flowable<R> flowableToFlowable(Flowable<T> flowable, Function<Flowable<T>, Flowable<R>> function) {
        try {
            return function.apply(flowable);
        } catch (Throwable t) {
            return Flowable.error(t);
        }
    }

    private static <R, T> Single<R> flowableToSingle(Flowable<T> flowable, Function<Flowable<T>, Single<R>> function) {
        try {
            return function.apply(flowable);
        } catch (Throwable t) {
            return Single.error(t);
        }
    }

    private static <R, T> Flowable<R> singleToFlowable(Single<T> single, Function<Single<T>, Flowable<R>> function) {
        try {
            return function.apply(single);
        } catch (Throwable t) {
            return Flowable.error(t);
        }
    }

    private static <R, T> Single<R> singleToSingle(Single<T> single, Function<Single<T>, Single<R>> function) {
        try {
            return function.apply(single);
        } catch (Throwable t) {
            return Single.error(t);
        }
    }
}
