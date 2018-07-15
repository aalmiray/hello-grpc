package org.kordamp.grpc.demo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class HelloClientUnary {
    public static void main(String[] args) {
        HelloClientUnary client = new HelloClientUnary();
        System.out.println(client.sayHello("World"));
    }

    private String sayHello(String input) {
        return blockingStub.helloUnary(HelloRequest.newBuilder()
            .setName(input)
            .build()).getReply();
    }

    private final ManagedChannel channel;
    private final HelloServiceGrpc.HelloServiceBlockingStub blockingStub;

    private HelloClientUnary() {
        channel = ManagedChannelBuilder.forAddress("localhost", 4567)
            .usePlaintext()
            .build();

        blockingStub = HelloServiceGrpc.newBlockingStub(channel);
    }
}
