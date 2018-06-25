package org.kordamp.grpc.demo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class HelloClientUnaryStreaming {
    public static void main(String[] args) throws Exception {
        HelloClientUnaryStreaming client = new HelloClientUnaryStreaming();
        client.sayHello("Guadalajara");
        Thread.sleep(1000);
    }

    private void sayHello(String input) {
        asyncStub.helloUnary(HelloRequest.newBuilder()
            .setName(input)
            .build(), new StreamObserver<HelloResponse>() {
            @Override
            public void onNext(HelloResponse value) {
                System.out.println(value.getReply());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("DONE");
            }
        });
    }

    private final ManagedChannel channel;
    private final HelloServiceGrpc.HelloServiceStub asyncStub;

    private HelloClientUnaryStreaming() {
        channel = ManagedChannelBuilder.forAddress("localhost", 4567)
            .usePlaintext()
            .build();

        asyncStub = HelloServiceGrpc.newStub(channel);
    }
}
