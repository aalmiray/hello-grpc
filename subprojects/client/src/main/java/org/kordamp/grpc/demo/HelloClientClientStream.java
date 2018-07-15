package org.kordamp.grpc.demo;

import io.grpc.stub.StreamObserver;

public class HelloClientClientStream extends AbstractAsyncHelloClient {
    public static void main(String[] args) throws Exception {
        HelloClientClientStream client = new HelloClientClientStream();
        client.sayHello("World");
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
            toServer.onNext(asRequest(input + " (" + i + ")"));
        }
        toServer.onCompleted();
    }
}
