package org.kordamp.grpc.demo;

import io.grpc.stub.StreamObserver;

public class HelloClientServerStream extends AbstractAsyncHelloClient {
    public static void main(String[] args) throws Exception {
        HelloClientServerStream client = new HelloClientServerStream();
        client.sayHello("World");
        Thread.sleep(1000);
    }

    private void sayHello(String input) {
        asyncStub.helloServerStream(asRequest(input), new StreamObserver<HelloResponse>() {
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
}
