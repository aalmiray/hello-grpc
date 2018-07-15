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
