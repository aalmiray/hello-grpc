package org.kordamp.grpc.demo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class HelloClientClientStream {
    public static void main(String[] args) throws Exception {
        HelloClientClientStream client = new HelloClientClientStream();
        client.sayHello("Guadalajara");
        Thread.sleep(1000);
    }

    private void sayHello(String input) {
        StreamObserver<HelloRequest> toServer = asyncStub.helloClientStream(new StreamObserver<HelloResponse>() {
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

    private HelloClientClientStream() {
        channel = ManagedChannelBuilder.forAddress("localhost", 4567)
            .usePlaintext()
            .build();

        asyncStub = HelloServiceGrpc.newStub(channel);
    }
}
