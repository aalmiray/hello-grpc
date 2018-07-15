package org.kordamp.grpc.demo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public abstract class AbstractAsyncHelloClient {
    protected HelloRequest asRequest(String str) {
        return HelloRequest.newBuilder()
            .setName(str)
            .build();
    }

    protected final ManagedChannel channel;
    protected final HelloServiceGrpc.HelloServiceStub asyncStub;

    protected AbstractAsyncHelloClient() {
        channel = ManagedChannelBuilder.forAddress("localhost", 4567)
            .usePlaintext()
            .build();

        asyncStub = HelloServiceGrpc.newStub(channel);
    }
}
