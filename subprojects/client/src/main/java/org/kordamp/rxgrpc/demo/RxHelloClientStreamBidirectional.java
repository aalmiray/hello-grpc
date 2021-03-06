/*
 * Copyright 2018-2020 Andres Almiray
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
package org.kordamp.rxgrpc.demo;

import io.reactivex.Flowable;
import org.kordamp.grpc.demo.HelloRequest;
import org.kordamp.grpc.demo.HelloResponse;

public class RxHelloClientStreamBidirectional extends AbstractRxHelloClient {
    public static void main(String[] args) throws Exception {
        RxHelloClientStreamBidirectional client = new RxHelloClientStreamBidirectional();
        client.sayHello("World");
        Thread.sleep(1000);
    }

    private void sayHello(String input) {
        Flowable<HelloRequest> inputs = Flowable.range(1, 5)
            .map(i -> asRequest(input + " (" + i + ")"));

        inputs.compose(stub::helloBidirectional)
            .doOnError(Throwable::printStackTrace)
            .doOnComplete(() -> System.out.println("DONE"))
            .map(HelloResponse::getReply)
            .subscribe(System.out::println);
    }
}
