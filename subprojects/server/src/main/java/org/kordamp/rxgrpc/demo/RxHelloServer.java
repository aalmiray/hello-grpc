package org.kordamp.rxgrpc.demo;

import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;

public class RxHelloServer {
    private final Server server;

    public static void main(String[] args) throws Exception {
        new RxHelloServer().run();
    }

    private RxHelloServer() {
        server = NettyServerBuilder.forPort(4567)
            .addService(new RxHelloServiceImpl())
            .build();
    }

    private void run() throws Exception {
        server.start();
        server.awaitTermination();
    }
}
