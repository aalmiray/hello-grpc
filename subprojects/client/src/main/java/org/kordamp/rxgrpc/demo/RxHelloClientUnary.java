package org.kordamp.rxgrpc.demo;

import org.kordamp.grpc.demo.HelloResponse;

public class RxHelloClientUnary extends AbstractRxHelloClient {
    public static void main(String[] args) throws Exception {
        RxHelloClientUnary client = new RxHelloClientUnary();
        client.sayHello("World");
        Thread.sleep(1000);
    }

    private void sayHello(String input) {
        stub.helloUnary(asRequest(input))
            .map(HelloResponse::getReply)
            .subscribe(System.out::println);
    }
}
