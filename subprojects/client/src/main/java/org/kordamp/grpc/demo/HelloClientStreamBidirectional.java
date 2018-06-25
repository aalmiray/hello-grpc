package org.kordamp.grpc.demo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class HelloClientStreamBidirectional {
    public static void main(String[] args) throws Exception {
        HelloClientStreamBidirectional client = new HelloClientStreamBidirectional();
        client.sayHello("Guadalajara");
        Thread.sleep(1000);
    }

    private void sayHello(String input) {
        StreamObserver<HelloRequest> toServer = asyncStub.helloBidirectional(new StreamObserver<HelloResponse>() {
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

        for (int i = 0; i < 5; i++) {
            HelloRequest request = HelloRequest.newBuilder()
                .setName(input + " (" + i + ")")
                .build();
            toServer.onNext(request);
        }
        toServer.onCompleted();
    }

    private final ManagedChannel channel;
    private final HelloServiceGrpc.HelloServiceStub asyncStub;

    private HelloClientStreamBidirectional() {
        channel = ManagedChannelBuilder.forAddress("localhost", 4567)
            .usePlaintext()
            .build();

        asyncStub = HelloServiceGrpc.newStub(channel);
    }
}
