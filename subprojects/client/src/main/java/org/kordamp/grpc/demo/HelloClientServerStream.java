/*
 * Copyright 2018 Andres Almiray
 *
 * This file is part of Java Trove Examples
 *
 * Java Trove Examples is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Java Trove Examples is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Java Trove Examples. If not, see <http://www.gnu.org/licenses/>.
 */
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
