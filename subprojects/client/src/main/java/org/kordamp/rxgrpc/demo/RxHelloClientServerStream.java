package org.kordamp.rxgrpc.demo;

import org.kordamp.grpc.demo.HelloResponse;

public class RxHelloClientServerStream extends AbstractRxHelloClient {
    public static void main(String[] args) throws Exception {
        RxHelloClientServerStream client = new RxHelloClientServerStream();
        client.sayHello("World");
        Thread.sleep(1000);
    }

    private void sayHello(String input) {
        stub.helloServerStream(asRequest(input))
            .doOnError(Throwable::printStackTrace)
            .doOnComplete(() -> System.out.println("DONE"))
            .map(HelloResponse::getReply)
            .subscribe(System.out::println);
    }
}
