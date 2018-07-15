package org.kordamp.rxgrpc.demo;

import io.reactivex.Flowable;
import org.kordamp.grpc.demo.HelloRequest;
import org.kordamp.grpc.demo.HelloResponse;

public class RxHelloClientClientStream extends AbstractRxHelloClient {
    public static void main(String[] args) throws Exception {
        RxHelloClientClientStream client = new RxHelloClientClientStream();
        client.sayHello("World");
        Thread.sleep(1000);
    }

    private void sayHello(String input) {
        Flowable<HelloRequest> inputs = Flowable.range(1, 5)
            .map(i -> asRequest(input + " (" + i + ")"));

        stub.helloClientStream(inputs)
            .doOnError(Throwable::printStackTrace)
            .doAfterTerminate(() -> System.out.println("DONE"))
            .map(HelloResponse::getReply)
            .subscribe(System.out::println);
    }
}
