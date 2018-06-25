package org.kordamp.grpc.demo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class HelloClientServerStream {
    public static void main(String[] args) throws Exception {
        HelloClientServerStream client = new HelloClientServerStream();
        client.sayHello("Guadalajara");
        Thread.sleep(1000);
    }

    private void sayHello(String input) {
        asyncStub.helloServerStream(HelloRequest.newBuilder()
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

    private HelloClientServerStream() {
        channel = ManagedChannelBuilder.forAddress("localhost", 4567)
            .usePlaintext()
            .build();

        asyncStub = HelloServiceGrpc.newStub(channel);
    }
}
