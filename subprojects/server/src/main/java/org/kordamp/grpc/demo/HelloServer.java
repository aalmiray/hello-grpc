package org.kordamp.grpc.demo;

import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;

public class HelloServer {
    private final Server server;

    public static void main(String[] args) throws Exception {
        new HelloServer().run();
    }

    private HelloServer() {
        server = NettyServerBuilder.forPort(4567)
            .addService(new HelloServiceImpl())
            .build();
    }

    private void run() throws Exception {
        server.start();
        server.awaitTermination();
    }
}
